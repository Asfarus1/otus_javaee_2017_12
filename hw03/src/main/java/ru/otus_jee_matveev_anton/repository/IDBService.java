package ru.otus_jee_matveev_anton.repository;

import ru.otus_jee_matveev_anton.repository.model.Employee;

import java.util.List;

public interface IDBService {

    void saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);

    void removeEmployeeById(int id);

    List<Employee> getAllEmployeesWithMaxSalary();
}
