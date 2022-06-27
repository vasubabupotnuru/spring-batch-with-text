package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Employee;
import com.techshard.batch.dao.entity.Voltage;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Value("${fileName}")
    String fileName;
    
    @Value("${table}")
    String table;
    
    @Value("${delimiter}")
    String delimeter;
    

    @Bean
    public FlatFileItemReader<Voltage> readerVoltage() {
        return new FlatFileItemReaderBuilder<Voltage>()
                .name("voltItemReader")
                .resource(new ClassPathResource(fileName))
                .delimited()
                .names(new String[]{"segmentid", "aeid", "segmenttype", "classification", "description"})
                .recordSeparatorPolicy(new BlankLineRecordSeparatorPolicy())
                .lineMapper(lineMapperVoltage())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Voltage>() {{
                    setTargetType(Voltage.class);
                }})
                .build();
    }
    
    @Bean
    public FlatFileItemReader<Employee> readerEmployee() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .resource(new ClassPathResource(fileName))
                .delimited()
                .names(new String[]{})
                .lineMapper(lineMapperEmployee())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
                    setTargetType(Employee.class);
                }})
                .build();
    }

    @Bean
    public LineMapper<Voltage> lineMapperVoltage() {

        final DefaultLineMapper<Voltage> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(delimeter);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] {"segmentid", "aeid", "segmenttype", "classification", "description"});

        final VoltageFieldSetMapper fieldSetMapper = new VoltageFieldSetMapper();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
    
    @Bean
    public LineMapper<Employee> lineMapperEmployee() {

        final DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(delimeter);

        final EmployeeFieldSetMapper fieldSetMapper = new EmployeeFieldSetMapper();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    @Bean
    public VoltageProcessor voltageProcessor() {
        return new VoltageProcessor();
    }
    
    @Bean
    public EmployeeProcessor employeeProcessor() {
        return new EmployeeProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Voltage> writerVoltage(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Voltage>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO voltage (segmentid, aeid, segmenttype, classification, description) VALUES (:segmentid, :aeid, :segmenttype, :classification, :description)")
                .dataSource(dataSource)
                .build();
    }
    
	@Bean
    public JdbcBatchItemWriter<Employee> writerEmployee(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO employee (assoiciate_id, cg_initials, First_name, last_Name, site_Cd) VALUES (:associateId, :cgInitials, :firstName, :lastName, :siteCd)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importVoltageJob(NotificationListener listener, Step step1, Step step2) {
    	if(table.equalsIgnoreCase("VOLTAGE")){
	        return jobBuilderFactory.get("importVoltageJob")
	                .incrementer(new RunIdIncrementer())
	                .flow(step1)
	                .end()
	                .build();
    	}else {
    		return jobBuilderFactory.get("importVoltageJob")
	                .incrementer(new RunIdIncrementer())
	                .flow(step2)
	                .end()
	                .listener(listener)
	                .build();
    	}
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Voltage> writer) {
        return stepBuilderFactory.get("step1")
                .<Voltage, Voltage> chunk(10)
                .reader(readerVoltage())
                .processor(voltageProcessor())
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(new StepSkipListener())
                .build();
    }
    
    @Bean
    public Step step2(JdbcBatchItemWriter<Employee> writer) {
        return stepBuilderFactory.get("step1")
                .<Employee, Employee> chunk(10)
                .reader(readerEmployee())
                .processor(employeeProcessor())
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(new StepSkipListener())
                .build();
    }
}
