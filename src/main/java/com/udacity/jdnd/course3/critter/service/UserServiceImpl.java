package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer.getPets() != null) {
            customer.getPets().forEach(pet -> pet.setOwner(customer));
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getOwnerByPet(long petId) {
        return petRepository
                .findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with petId: " + petId))
                .getOwner();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(long id) {
        return employeeRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = getEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        return employeeRepository
                .findAll()
                .stream()
                .filter(
                        employee ->
                                employee.getDaysAvailable().contains(date.getDayOfWeek())
                                        && employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }
}
