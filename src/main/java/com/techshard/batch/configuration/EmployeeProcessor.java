package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Employee;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee>{

    @Override
    public Employee process(final Employee employee) {
        final Long associateId = employee.getAssociateId();
        final String cgInitials = employee.getCgInitials();
        final String firstName = employee.getFirstName();
        final String lastName = employee.getLastName();
        final String siteCd = employee.getSiteCd();

        final Employee processedemployee = new Employee();
        processedemployee.setAssociateId(associateId);
        processedemployee.setCgInitials(cgInitials);
        processedemployee.setFirstName(firstName);
        processedemployee.setLastName(lastName);
        processedemployee.setSiteCd(siteCd);


        return processedemployee;
    }
}
