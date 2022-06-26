package com.techshard.batch.dao.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Employee {

    @Id
    @Column (name = "assoiciate_id", nullable = false)
    private Long associateId;

    @Column (name = "cg_initials")
    private String cgInitials;

    @Column (name = "First_name")
    private String firstName;

    @Column (name = "last_Name")
    private String lastName;

    @Column (name = "site_Cd")
    private String siteCd;


    public Employee() {
    }

    public Employee(Long associateId, @NotNull String cgInitials, @NotNull String firstName, String lastName,
			String siteCd) {
		super();
		this.associateId = associateId;
		this.cgInitials = cgInitials;
		this.firstName = firstName;
		this.lastName = lastName;
		this.siteCd = siteCd;
	}

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public String getCgInitials() {
		return cgInitials;
	}

	public void setCgInitials(String cgInitials) {
		this.cgInitials = cgInitials;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSiteCd() {
		return siteCd;
	}

	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}

	
}
