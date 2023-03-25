package com.example.employee.service;
import com.example.employee.model.Employee;
import com.example.employee.model.EmployeeRowMapper;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
@Service
public class EmployeeH2Service implements EmployeeRepository{
     @Autowired
     private JdbcTemplate db;
     public ArrayList<Employee> getAllEmployees(){
        return (ArrayList<Employee>) db.query("select * from employeelist",new EmployeeRowMapper());
     }
     public Employee getEmployeeById(int employeeId){
        try{
            return db.queryForObject("select * from employeelist where employeeId=?",new EmployeeRowMapper(),employeeId);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
     }
     public Employee addEmployee(Employee employee){
        db.update("insert into employeelist(employeeName,email,department) values(?,?,?)",employee.getEmployeeName(),employee.getEmail(),employee.getDepartment());
        return db.queryForObject("select * from employeelist where employeeName=? and email=?",new EmployeeRowMapper(),employee.getEmployeeName(),employee.getEmail());
     }
     public void deleteEmployee(int employeeId){
        db.update("delete from employeelist where employeeId=?",employeeId);
     }
     public Employee updateEmployee(int employeeId,Employee employee){
        if(employee.getEmployeeName()!=null){
            db.update("update employeelist set employeeName=? where employeeId=?",employee.getEmployeeName(),employeeId);
        }
        if(employee.getEmail()!=null){
            db.update("update employeelist set email=? where employeeId=?",employee.getEmail(),employeeId);
        }
        if(employee.getDepartment()!=null){
            db.update("update employeelist set department=? where employeeId=?",employee.getDepartment(),employeeId);
        }
        return getEmployeeById(employeeId);
     }
}