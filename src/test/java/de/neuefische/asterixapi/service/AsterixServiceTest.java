package de.neuefische.asterixapi.service;

import de.neuefische.asterixapi.model.AsterixCharacter;
import de.neuefische.asterixapi.model.AsterixCharacterDto;
import de.neuefische.asterixapi.repository.AsterixRepo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsterixServiceTest {

    IdService idService = mock(IdService.class);
    AsterixRepo repo = mock(AsterixRepo.class);
    AsterixService service = new AsterixService(repo, idService);
    @Test
    void findAllCharacters_shouldReturnEmptyList_whenCalledInitially() {
        //GIVEN
        List<AsterixCharacter> expected = List.of();
        when(repo.findAll()).thenReturn(List.of());
        //WHEN
        List<AsterixCharacter> actual = service.findAllCharacters();
        //THEN
        assertEquals(expected, actual);
        verify(repo).findAll();
    }

    @Test
    void saveNewAsterixCharacter_shouldReturnCharacter_whenGivenAnDto() {
        //GIVEN
        AsterixCharacterDto dto = new AsterixCharacterDto("Test", 22, "Test");
        AsterixCharacter expected = new AsterixCharacter("1","Test", 22, "Test");
        when(repo.findById("1")).thenReturn(Optional.of(new AsterixCharacter("1", "Test", 22, "Test")));
        when(idService.generateRandomId()).thenReturn("1");
        //WHEN
        AsterixCharacter actual = service.saveNewAsterixCharacter(dto);
        //THEN
        assertEquals(expected, actual);
        verify(repo).findById("1");
        verify(idService).generateRandomId();
    }

    @Test
    void updateCharacterById_shouldReturnUpdatedCharacter_whenGivenUpdatedDto() {
        //GIVEN
        AsterixCharacterDto dto = new AsterixCharacterDto("Test", 25, "Test");
        AsterixCharacter expected = new AsterixCharacter("1","Test", 25, "Test");
        when(repo.findById("1")).thenReturn(Optional.of(new AsterixCharacter("1", "Test", 25, "Test")));
        when(repo.existsById("1")).thenReturn(true);
        //WHEN
        AsterixCharacter actual = service.updateCharacterById("1", dto);
        //THEN
        assertEquals(expected, actual);
        verify(repo).findById("1");
        verify(repo).existsById("1");
    }

    @Test
    void deleteCharById_shouldReturnTrue_whenCalledWithValidId() {
        //GIVEN
        when(repo.existsById("1")).thenReturn(true);

        //WHEN
        boolean actual = service.deleteCharById("1");
        //THEN
        assertTrue(actual);
        verify(repo).deleteById("1");
        verify(repo).existsById("1");
    }
}