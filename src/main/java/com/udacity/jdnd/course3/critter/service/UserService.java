package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface UserService {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllCustomer();

    Customer getOwnerByPet(long petId);

    Employee saveEmployee(Employee employee);

    Employee getEmployee(long id);

    void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date);
}
