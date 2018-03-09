package ru.otus_jee_matveev_anton.servlets;

import ru.otus_jee_matveev_anton.repository.IDBService;
import ru.otus_jee_matveev_anton.repository.JPAService;
import ru.otus_jee_matveev_anton.repository.model.Employee;
import ru.otus_jee_matveev_anton.repository.model.Position;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "JPAServlet", urlPatterns = "/jpa")
public class JPAServlet extends HttpServlet {
    private IDBService service = new JPAService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        remove3Employees();
        change2Employees();
        try(PrintWriter writer = response.getWriter()) {
            service.getAllEmployees().forEach(writer::println);
            writer.println("employees with max salary:");
            service.getAllEmployeesWithMaxSalary().forEach(writer::println);
        }
    }

    private void remove3Employees(){
        service.removeEmployeeById(3);
        service.removeEmployeeById(5);
        service.removeEmployeeById(2);
    }

    private void change2Employees(){
        Employee e1 = service.getEmployeeById(4);
        e1.setFathername("changedFatherName");
        service.saveEmployee(e1);

        Employee e2 = service.getEmployeeById(9);
        Position position = new Position();
        position.setTitle("new position");
        e1.setPosition(position);
        service.saveEmployee(e2);
    }
}
