package com.caching_in_memory.service;

import com.caching_in_memory.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeById(long id);

    EmployeeDto updateEmployeeById(long id, EmployeeDto employeeDto);

    void deleteEmployeeById(long id);
}
