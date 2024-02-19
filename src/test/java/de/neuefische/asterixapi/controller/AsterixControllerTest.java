package de.neuefische.asterixapi.controller;

import de.neuefische.asterixapi.model.AsterixCharacter;
import de.neuefische.asterixapi.repository.AsterixRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AsterixControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AsterixRepo repo;
    @Test
    void getAllCharacter_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get("/api/asterix/characters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void saveNewAsterixCharacter_shouldReturnNewCharacter_whenGivenValidDto() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.post("/api/asterix")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Test",
                            "age": 22,
                            "profession": "Test"
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                           "name": "Test",
                           "age": 22,
                           "profession": "Test" 
                        }
                        """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void updateAsterixCharacter_shouldReturnUpdatedChar_whenGivenUpdatedDto() throws Exception {
        //GIVEN
        AsterixCharacter newChar = new AsterixCharacter("1", "Test", 22, "Test");
        repo.save(newChar);
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.put("/api/asterix/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "Test1",
                    "age" : 25,
                    "profession": "Tester"
                }
"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                    {
                        "id": "1",
                        "name": "Test1",
                        "age" : 25,
                        "profession": "Tester"
                    }
"""
                ));
    }

    @Test
    void deleteCharacter_shouldDeleteChar_whenGivenValidId() throws Exception {
        //GIVEN
        AsterixCharacter newChar = new AsterixCharacter("1", "Test", 22, "Test");
        repo.save(newChar);
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.delete("/api/asterix/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/api/asterix/characters"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }
}