package com.rakbank.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeReq {

	@NotNull
	@Digits(integer = 10, fraction = 0, message = "employee number size must be less than or equal to 10 digit")
	private Long employeeNo;

	@NotBlank
	@Size(max = 100)
	private String employeeName;

	@NotBlank
	private String doj;

	@NotBlank
	@Size(min = 2, max = 2, message = "Invalid Department Code")
	private String departmentCode;

	@NotNull
	@Digits(integer = 10, fraction = 0, message = "salary size must be less than or equal to 10 digit")
	private Long salary;

	public Long getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(Long employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public Long getSalary() {
		return salary;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

}
