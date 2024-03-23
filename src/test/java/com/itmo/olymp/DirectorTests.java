package com.itmo.olymp;

import com.itmo.olymp.dto.director.DirectorDto;
import com.itmo.olymp.dto.director.DirectorWrapper;
import com.itmo.olymp.entity.Director;
import com.itmo.olymp.exception.RequiredFieldException;
import com.itmo.olymp.exception.ValidationException;
import com.itmo.olymp.service.DirectorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/data.sql")
class DirectorTests {

    static Integer directorId1 = 1;
    static Integer directorId2 = 2;
    static Integer notExistingDirector = 3;
    
    @Autowired
    DirectorService directorService;

    @Test
    void testFindByIdDirector() {
        Assertions.assertDoesNotThrow(() -> directorService.findById(directorId1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> directorService.findById(notExistingDirector));
    }

    @Test
    void testFindAllDirectors() {
        List<Director> directorList = directorService.findAll();
        Assertions.assertFalse(directorList.isEmpty());
        Assertions.assertEquals(2, directorList.size());
    }

    @Test
    void testSaveNewDirector() {
        DirectorWrapper directorWrapper = new DirectorWrapper(new DirectorDto(directorId1, null));
        Assertions.assertThrows(RequiredFieldException.class, () -> directorService.save(directorWrapper));

        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 200; ++i) {
            longName.append("a");
        }
        DirectorWrapper directorWrapperIncorrectFio = new DirectorWrapper(
                new DirectorDto(directorId1, longName.toString()));
        Assertions.assertThrows(ValidationException.class,() -> directorService.save(directorWrapperIncorrectFio));

        DirectorWrapper directorWrapperCorrect = new DirectorWrapper(new DirectorDto(directorId2, "New fio"));
        Assertions.assertDoesNotThrow(() -> directorService.save(directorWrapperCorrect));
        Assertions.assertEquals(directorWrapperCorrect.getDirector().getFio(),
                directorService.findById(directorId2).getName());
    }

    @Test
    void deleteDirector() {
        Assertions.assertDoesNotThrow(() -> directorService.delete(directorId2));
        Assertions.assertThrows(EntityNotFoundException.class, () -> directorService.delete(directorId2));
        List<Director> directorList = directorService.findAll();
        Assertions.assertFalse(directorList.isEmpty());
        Assertions.assertEquals(1, directorList.size());
    }
}
