package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final UserService userService;

    private final PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleToScheduleDTO(scheduleService.createSchedule(scheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(this::scheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getScheduleForPet(petId)
                .stream()
                .map(this::scheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleForEmployee(employeeId)
                .stream()
                .map(this::scheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getScheduleForCustomer(customerId)
                .stream()
                .map(this::scheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    private Schedule scheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        if (scheduleDTO.getEmployeeIds() != null) {
            List<Employee> employees = scheduleDTO.getEmployeeIds()
                    .stream()
                    .map(userService::getEmployee)
                    .collect(Collectors.toList());
            schedule.setEmployees(employees);
        }
        if (scheduleDTO.getPetIds() != null) {
            List<Pet> pets = scheduleDTO.getPetIds()
                    .stream()
                    .map(petService::getPet)
                    .collect(Collectors.toList());
            schedule.setPets(pets);
        }
        return schedule;
    }

    private ScheduleDTO scheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getEmployees() != null) {
            List<Long> employeeIds =  schedule.getEmployees()
                    .stream()
                    .map(Employee::getId)
                    .collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        if (schedule.getPets() != null) {
            List<Long> petIds =  schedule.getPets()
                    .stream().map(Pet::getId)
                    .collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }
        return scheduleDTO;
    }
}
