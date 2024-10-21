package com.akshat.springboootweb.springbootweb.services;

import com.akshat.springboootweb.springbootweb.dto.EmployeeDTO;
import com.akshat.springboootweb.springbootweb.entities.EmployeeEntity;
import com.akshat.springboootweb.springbootweb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper){
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public EmployeeDTO getEmployeeById(Long id){
        EmployeeEntity emp = employeeRepository.findById(id).orElse(null);
        return mapper.map(emp, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<EmployeeEntity> l =  employeeRepository.findAll();
        return l
                .stream()
                .map(employeeEntity -> mapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO emp){
        EmployeeEntity ent = mapper.map(emp, EmployeeEntity.class);
        ent = employeeRepository.save(ent);
        return mapper.map(ent, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long id, EmployeeDTO employeeDTO){
        EmployeeEntity employeeEntity = mapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return mapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id){
        if(employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    public EmployeeDTO updateEmployeeById(Map<String, Object> updates, Long id) {
        if (employeeRepository.existsById(id)) {
            EmployeeEntity employeeEntity = employeeRepository.findById(id).get();

            updates.forEach((key, value) -> {
                Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class, key); // Use 'key' instead of 'field'

                if (fieldToBeUpdated != null) {
                    fieldToBeUpdated.setAccessible(true);
                    ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
                } else {
                    throw new IllegalArgumentException("Field not found: " + key);
                }
            });

            employeeRepository.save(employeeEntity);
            return mapper.map(employeeEntity, EmployeeDTO.class);
        } else {
            System.out.println("Employee Not Found");
            return null; // Consider throwing an exception if employee not found
        }
    }

}
