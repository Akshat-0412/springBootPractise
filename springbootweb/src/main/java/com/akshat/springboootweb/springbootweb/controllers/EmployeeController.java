package com.akshat.springboootweb.springbootweb.controllers;

import com.akshat.springboootweb.springbootweb.dto.EmployeeDTO;
import com.akshat.springboootweb.springbootweb.entities.EmployeeEntity;
import com.akshat.springboootweb.springbootweb.repositories.EmployeeRepository;
import com.akshat.springboootweb.springbootweb.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RequestMapping(path = "/employees")

@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @GetMapping("/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeID){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeID);
        if(employeeDTO == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(employeeDTO);
        }
    }

    @GetMapping()
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age){
        return employeeService.getAllEmployees();
    }

    @PostMapping()
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);
    }

    @PutMapping(path="/{employeeId}")
    public EmployeeDTO updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId){
        return employeeService.updateEmployeeById(employeeId, employeeDTO);
    }

    @DeleteMapping(path = "/{employeeId}")
    public Boolean deleteEmployeeById(@PathVariable Long employeeId){
        return employeeService.deleteEmployeeById(employeeId);
    }

    @PatchMapping(path = "/{employeeId}")
    public EmployeeDTO updateEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
        return employeeService.updateEmployeeById(updates, employeeId);
    }

}
