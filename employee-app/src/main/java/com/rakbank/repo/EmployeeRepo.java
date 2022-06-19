package com.rakbank.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rakbank.entity.EmployeeEntity;

@Transactional
@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long>{

	List<EmployeeEntity> findByEmployeeNoAndEmployeeNameContainingIgnoreCase(Long employeeNo, String employeeName);
	
	EmployeeEntity findByEmployeeNo(Long empNo);
	
	Long deleteByEmployeeNo(Long employeeNo);
}
