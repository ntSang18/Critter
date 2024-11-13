package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleForPet(long petId);

    List<Schedule> getScheduleForEmployee(long employeeId);

    List<Schedule> getScheduleForCustomer(long customerId);
}
