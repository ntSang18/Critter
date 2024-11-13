package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    private final CustomerRepository customerRepository;

    @Override
    public Pet getPet(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet not found with id: " + id));

    }

    @Override
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetByOwner(Long ownerId) {
        return customerRepository
                .findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + ownerId))
                .getPets();
    }

    @Override
    public Pet save(Pet pet, long ownerId) {
        Customer owner = customerRepository.findById(ownerId).orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + ownerId));
        pet.setOwner(owner);
        owner.getPets().add(pet);
        return petRepository.save(pet);
    }
}
