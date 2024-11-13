package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private PetType type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer owner;

    private LocalDate birthDate;

    @Column(length = 5000)
    private String notes;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> schedules = new ArrayList<>();
}
