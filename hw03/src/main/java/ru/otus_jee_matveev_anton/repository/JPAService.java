package ru.otus_jee_matveev_anton.repository;

import ru.otus_jee_matveev_anton.repository.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import java.util.List;

public class JPAService implements IDBService {
    private final EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    @Override
    public void saveEmployee(Employee employee) {
        em.persist(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e order by id", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee getEmployeeById(int id) {
        return em.find(Employee.class, id);
    }

    @Override
    public void removeEmployeeById(int id) {
        Employee employee = getEmployeeById(id);
        if (employee != null) {
            em.remove(employee);
        }
    }

    @Override
    public List<Employee> getAllEmployeesWithMaxSalary() {
        StoredProcedureQuery empProc = em.createNamedStoredProcedureQuery(Employee.GET_EMPLOYEES_WITH_MAX_SALARY);
        empProc.execute();
        return empProc.getResultList();
    }
}
