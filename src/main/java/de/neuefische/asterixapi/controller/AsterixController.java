package de.neuefische.asterixapi.controller;

import de.neuefische.asterixapi.model.AsterixCharacter;
import de.neuefische.asterixapi.model.AsterixCharacterDto;
import de.neuefische.asterixapi.repository.AsterixRepo;
import de.neuefische.asterixapi.service.AsterixService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asterix")
@RequiredArgsConstructor
public class AsterixController {

    private final AsterixService service;
    @GetMapping("/characters")
    public List<AsterixCharacter> getAllCharacter(){
        return service.findAllCharacters();
    }

    @PostMapping
    public AsterixCharacter saveNewAsterixCharacter(@RequestBody AsterixCharacterDto asterixCharacter){
        return service.saveNewAsterixCharacter(asterixCharacter);
    }

    @PutMapping("/update/{id}")
    public AsterixCharacter updateAsterixCharacter (@PathVariable String id,
                                                    @RequestBody AsterixCharacterDto asterixCharacter){
        return service.updateCharacterById(id, asterixCharacter);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteCharacter(@PathVariable String id){
        return service.deleteCharById(id);
    }
}
