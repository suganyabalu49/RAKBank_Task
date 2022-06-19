package com.rakbank.srvc.impl;

import static com.rakbank.util.Constants.DATE_ERROR_MSG;
import static com.rakbank.util.Constants.EMP_EXIST;
import static com.rakbank.util.Constants.EMP_NOT_FOUND;
import static com.rakbank.util.Constants.INVALID_DEPARTMENT;
import static com.rakbank.util.Constants.SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rakbank.entity.DepartmentEntity;
import com.rakbank.entity.EmployeeEntity;
import com.rakbank.exception.BusinessException;
import com.rakbank.model.DepartmentVO;
import com.rakbank.model.EmployeeReq;
import com.rakbank.model.EmployeeRespVO;
import com.rakbank.model.Response;
import com.rakbank.model.SearchEmpReq;
import com.rakbank.repo.DepartmentRepo;
import com.rakbank.repo.EmployeeRepo;
import com.rakbank.srvc.EmployeeAppSrvc;

@Service
public class EmployeeAppSrvcImpl implements EmployeeAppSrvc {

	private DepartmentRepo departmentRepo;
	private EmployeeRepo employeeRepo;

	public EmployeeAppSrvcImpl(DepartmentRepo departmentRepo, EmployeeRepo employeeRepo) {
		this.departmentRepo = departmentRepo;
		this.employeeRepo = employeeRepo;
	}

	@Override
	public Response saveEmployee(EmployeeReq employeeReq) {
		validateDate(employeeReq.getDoj());
		EmployeeEntity entity = employeeRepo.findByEmployeeNo(employeeReq.getEmployeeNo());
		if (null != entity) {
			throw new BusinessException(EMP_EXIST);
		}
		entity = new EmployeeEntity();
		BeanUtils.copyProperties(employeeReq, entity);
		entity.setDepartmentEntity(getDepartMentEntity(employeeReq.getDepartmentCode()));
		employeeRepo.save(entity);
		return new Response(Boolean.TRUE, SUCCESS);
	}

	@Override
	public Response updateEmployee(EmployeeReq employeeReq) {
		validateDate(employeeReq.getDoj());
		EmployeeEntity entity = employeeRepo.findByEmployeeNo(employeeReq.getEmployeeNo());
		if (null == entity) {
			throw new BusinessException(EMP_NOT_FOUND);
		}
		BeanUtils.copyProperties(employeeReq, entity);
		entity.setDepartmentEntity(departmentRepo.findByCode(employeeReq.getDepartmentCode()));
		employeeRepo.save(entity);
		return new Response(Boolean.TRUE, SUCCESS);
	}

	@Override
	public Response deleteEmployee(Long employeeNo) {
		employeeRepo.deleteByEmployeeNo(employeeNo);
		return new Response(Boolean.TRUE, SUCCESS);
	}

	@Override
	public List<EmployeeRespVO> getEmployee(SearchEmpReq searchEmpReq) {
		List<EmployeeEntity> employeeEntities = employeeRepo.findByEmployeeNoAndEmployeeNameContainingIgnoreCase(
				searchEmpReq.getEmployeeNo(), searchEmpReq.getEmployeeName());
		return convertEmpEntityToResp(employeeEntities);
	}

	@Override
	public List<DepartmentVO> getAllDepartments() {
		List<DepartmentVO> departmentVOs = new ArrayList<>();
		List<DepartmentEntity> departmentEntities = departmentRepo.findAll();
		if (null != departmentEntities && !departmentEntities.isEmpty()) {
			departmentEntities.forEach(dep -> {
				departmentVOs.add(new DepartmentVO(dep.getCode(), dep.getDescription()));
			});
		}
		return departmentVOs;
	}

	@Override
	public List<EmployeeRespVO> getAllEmployee() 
	{
		List<EmployeeEntity> employeeEntities = employeeRepo.findAll();
		return convertEmpEntityToResp(employeeEntities);
	}

	/**
	 * @param employeeEntities
	 * @return
	 */
	private List<EmployeeRespVO> convertEmpEntityToResp(List<EmployeeEntity> employeeEntities) {
		List<EmployeeRespVO> empRespList = new ArrayList<>();
		if (null != employeeEntities && !employeeEntities.isEmpty()) 
		{
			employeeEntities.forEach(emp -> {
				EmployeeRespVO empResp = new EmployeeRespVO();
				BeanUtils.copyProperties(emp, empResp);
				if (null != emp.getDepartmentEntity())
					empResp.setDepartmentVO(new DepartmentVO(emp.getDepartmentEntity().getCode(),
							emp.getDepartmentEntity().getDescription()));

				empRespList.add(empResp);
			});
		}
		else
		{
			throw new BusinessException(EMP_NOT_FOUND);
		}
		return empRespList;
	}
	
	/**
	 * @param doj
	 */
	private void validateDate(String doj) 
	{
		if(!doj.matches("^([0]?[1-9]|[1|2][0-9]|[3][0|1])[/]([0]?[1-9]|[1][0-2])[/]([0-9]{4}|[0-9]{2})$"))
			throw new BusinessException(DATE_ERROR_MSG);
	}

	/**
	 * @param code
	 * @return
	 */
	private DepartmentEntity getDepartMentEntity(String code) {
		DepartmentEntity entity = departmentRepo.findByCode(code);
		if (null == entity) {
			throw new BusinessException(INVALID_DEPARTMENT);
		}
		return entity;
	}

}
