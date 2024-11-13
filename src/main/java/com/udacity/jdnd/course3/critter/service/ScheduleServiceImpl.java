package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final PetRepository petRepository;

    private final EmployeeRepository employeeRepository;

    private final CustomerRepository customerRepository;

    @Override
    public Schedule createSchedule(Schedule schedule) {
        schedule.getEmployees().forEach(employee -> employee.getSchedules().add(schedule));
        schedule.getPets().forEach(pet -> pet.getSchedules().add(schedule));
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleForPet(long petId) {
        return petRepository
                .findById(petId).orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + petId))
                .getSchedules();
    }

    @Override
    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id:" + employeeId))
                .getSchedules();
    }

    @Override
    public List<Schedule> getScheduleForCustomer(long customerId) {
        return customerRepository
                .findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found with id:" + customerId))
                .getPets()
                .stream()
                .map(Pet::getSchedules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
