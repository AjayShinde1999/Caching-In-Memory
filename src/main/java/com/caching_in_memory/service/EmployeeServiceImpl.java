package com.caching_in_memory.service;

import com.caching_in_memory.dto.EmployeeDto;
import com.caching_in_memory.entity.Employee;
import com.caching_in_memory.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;


    @CachePut(cacheNames = "employees", key = "#result.id")
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.info("Employee to save {}", employeeDto);
        Employee employeeEntity = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(employeeEntity);
        log.info("Saved employee {}", savedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);
    }

    @Cacheable(cacheNames = "employees", key = "#id")
    @Override
    public List<EmployeeDto> getAllEmployee() {
        log.info("Fetching all the employee from database");
        List<Employee> listOfEmployees = employeeRepository.findAll();
        log.info("Fetched employee from data {}", listOfEmployees);
        return listOfEmployees.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "employees", key = "#id")
    @Override
    public EmployeeDto getEmployeeById(long id) {
        log.info("Fetching the employee data for id {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        log.info("Fetched employee data {}", employee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @CachePut(cacheNames = "employees", key = "#id")
    @Override
    public EmployeeDto updateEmployeeById(long id, EmployeeDto employeeDto) {
        log.info("Update employee for id {} with data {}", id, employeeDto);
        employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        Employee updateEmployee = modelMapper.map(employeeDto, Employee.class);
        updateEmployee.setId(id);
        Employee updatedEmployee = employeeRepository.save(updateEmployee);
        log.info("Updated employee data {}", updatedEmployee);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @CacheEvict(cacheNames = "employees", key = "#id")
    @Override
    public void deleteEmployeeById(long id) {
        log.info("Delete employee for id {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        employeeRepository.delete(employee);
        log.info("Employee delete with id {}", id);
    }
}
