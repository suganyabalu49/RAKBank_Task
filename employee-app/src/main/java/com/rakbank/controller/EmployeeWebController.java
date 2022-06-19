package com.rakbank.controller;

import static com.rakbank.util.Constants.CREATE_EMP;
import static com.rakbank.util.Constants.INDEX;
import static com.rakbank.util.Constants.SUCCESS;
import static com.rakbank.util.Constants.WEB;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rakbank.exception.BusinessException;
import com.rakbank.model.EmployeeReq;
import com.rakbank.srvc.EmployeeAppSrvc;

@Controller
@RequestMapping(WEB)
public class EmployeeWebController {
	
	private EmployeeAppSrvc employeeAppSrvc;
	
	public EmployeeWebController(EmployeeAppSrvc employeeAppSrvc) {
		this.employeeAppSrvc = employeeAppSrvc;
	}
	
	@GetMapping(INDEX)
	public String showIndexPage(Model model) 
	{
		model.addAttribute("employee", new EmployeeReq());
		model.addAttribute("department", employeeAppSrvc.getAllDepartments().stream().map(dep -> dep.getCode()).collect(Collectors.toList()));
		return "add-emp";
	}

	@PostMapping(CREATE_EMP)
	public String saveEmployee(@Valid @ModelAttribute("employee") EmployeeReq employee, BindingResult result, Model model) 
	{
		model.addAttribute("department", employeeAppSrvc.getAllDepartments().stream().map(dep -> dep.getCode()).collect(Collectors.toList()));
		if (result.hasErrors()) {
			model.addAttribute("employee", employee);
			return "add-emp";
        }
		try 
		{
			employeeAppSrvc.saveEmployee(employee);
			model.addAttribute("employee", new EmployeeReq());
			model.addAttribute("msg", SUCCESS);
		} 
		catch (BusinessException ex) 
		{
			model.addAttribute("msg", ex.getMessage());
		}
		return "add-emp";
	}
	
}
