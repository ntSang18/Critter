package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerToCustomerDTO(userService.saveCustomer(customerDTOToCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.getAllCustomer()
                .stream()
                .map(this::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return customerToCustomerDTO(userService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeToEmployeeDTO(userService.saveEmployee(employeeDTOToEmployee(employeeDTO)));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeToEmployeeDTO(userService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return userService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate())
                .stream()
                .map(this::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    private Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Customer customerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO customerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null) {
            List<Long> petIds = customer.getPets()
                    .stream()
                    .map(Pet::getId)
                    .collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

}
