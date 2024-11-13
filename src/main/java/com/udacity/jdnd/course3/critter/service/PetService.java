package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Pet;

import java.util.List;

public interface PetService  {

    Pet getPet(Long id);

    List<Pet> getPets();

    List<Pet> getPetByOwner(Long ownerId);

    Pet save(Pet pet, long ownerId);

}
