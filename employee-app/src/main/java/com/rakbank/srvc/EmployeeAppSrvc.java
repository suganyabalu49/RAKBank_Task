package com.rakbank.srvc;

import java.util.List;

import com.rakbank.model.DepartmentVO;
import com.rakbank.model.EmployeeReq;
import com.rakbank.model.EmployeeRespVO;
import com.rakbank.model.Response;
import com.rakbank.model.SearchEmpReq;

public interface EmployeeAppSrvc {

	Response saveEmployee(EmployeeReq employeeReq);

	Response updateEmployee(EmployeeReq employeeReq);

	Response deleteEmployee(Long employeeNo);

	List<EmployeeRespVO> getEmployee(SearchEmpReq searchEmpReq);

	List<DepartmentVO> getAllDepartments();

	List<EmployeeRespVO> getAllEmployee();
}
