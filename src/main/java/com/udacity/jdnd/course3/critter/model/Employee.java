package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class Employee extends User {

    @ElementCollection
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @CollectionTable(name = "employee_days_available", joinColumns = @JoinColumn(name = "employee_id"))
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules = new ArrayList<>();
}
