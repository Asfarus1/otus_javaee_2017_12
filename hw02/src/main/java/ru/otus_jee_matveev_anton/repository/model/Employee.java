package ru.otus_jee_matveev_anton.repository.model;

import javax.persistence.*;

@Entity
@Table(name = "employees", schema = "public", catalog = "otus_jee")
@NamedStoredProcedureQuery(name = "getEmployeesWithMaxSalary", procedureName = Employee.GET_EMPLOYEES_WITH_MAX_SALARY, resultClasses = Employee.class)
public class Employee {
    public static final String GET_EMPLOYEES_WITH_MAX_SALARY = "getEmployeesWithMaxSalary";
    private int id;
    private String surname;
    private String name;
    private String fathername;
    private Subdivision subdivision;
    private City city;
    private Position position;
    private String phone;
    private String email;
    private User user;
    private int salary;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "fathername")
    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subdivision")
    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position")
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "appuser")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "salary")
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee that = (Employee) o;

        if (id != that.id) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (fathername != null ? !fathername.equals(that.fathername) : that.fathername != null) return false;
        if (subdivision != null ? !subdivision.equals(that.subdivision) : that.subdivision != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fathername != null ? fathername.hashCode() : 0);
        result = 31 * result + (subdivision != null ? subdivision.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", fathername='" + fathername + '\'' +
                ", subdivision=" + subdivision +
                ", city=" + city +
                ", position=" + position +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", user=" + user +
                ", salary=" + salary +
                '}';
    }
}
