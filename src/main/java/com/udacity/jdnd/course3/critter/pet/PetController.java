package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return petToPetDTO(petService.save(petDTOToPet(petDTO), petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getPets()
                .stream()
                .map(this::petToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetByOwner(ownerId)
                .stream()
                .map(this::petToPetDTO).collect(Collectors.toList());
    }

    private Pet petDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    private PetDTO petToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }
}
