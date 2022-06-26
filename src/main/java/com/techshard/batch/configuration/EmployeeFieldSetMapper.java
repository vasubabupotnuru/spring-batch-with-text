package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Employee;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFieldSetMapper implements FieldSetMapper<Employee> {

    @Override
    public Employee mapFieldSet(FieldSet fieldSet) {
        final Employee employee = new Employee();

        employee.setAssociateId(fieldSet.readLong(0));
        employee.setCgInitials(trimActualValue(fieldSet.readString(1)));
        employee.setFirstName(trimActualValue(fieldSet.readString(2)));
        employee.setLastName(trimActualValue(fieldSet.readString(3)));
        employee.setSiteCd(trimActualValue(fieldSet.readString(4)));
        return employee;

    }
    
    private String trimActualValue(String input) {
    	if(input!=null) {
    		input = input.replaceAll("^\"|\"$", "");	
        	return input.length()>0?input:null;
    	}
    	return null;
    }
}
