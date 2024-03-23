package com.itmo.olymp;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.itmo.olymp.dto.movie.MovieDto;
import com.itmo.olymp.dto.movie.MovieWrapper;
import com.itmo.olymp.entity.Director;
import com.itmo.olymp.entity.Movie;
import com.itmo.olymp.exception.ValidationException;
import com.itmo.olymp.service.DirectorService;
import com.itmo.olymp.service.MovieService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.lang.reflect.Executable;
import java.text.ParseException;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Sql("/data.sql")
class MovieApplicationTests {

    static Integer movieId1 = 1;
    static Integer movieId2 = 2;
    static Integer notExistingMovie = 3;


    @Autowired
    MovieService movieService;

    @Autowired
    DirectorService directorService;

    @Test
    void testFindByIdMovie() {
        Assertions.assertDoesNotThrow(() -> movieService.findById(movieId1));
        Assertions.assertThrows(EntityNotFoundException.class, () -> movieService.findById(notExistingMovie));
    }

    @Test
    void testFindAllMovies() {
        List<Movie> movies = movieService.findAll();
        Assertions.assertFalse(movies.isEmpty());
        Assertions.assertEquals(2, movies.size());
    }

    @Test
    void testSaveNewMovie() throws ParseException {
        MovieWrapper movieWrapper = new MovieWrapper(
                MovieDto.builder()
                        .year(2002)
                        .build()
        );
        Assertions.assertThrows(NullPointerException.class, () -> movieService.save(movieWrapper));

        MovieWrapper movieWrapperIncorrectYear = new MovieWrapper(
                MovieDto.builder()
                        .director(1)
                        .title("Titile wfkwf")
                        .length("20:20:20")
                        .year(1000)
                        .rating(10)
                        .build()
        );
        Assertions.assertThrows(ValidationException.class,() -> movieService.save(movieWrapperIncorrectYear));

        MovieWrapper movieWrapperCorrect = new MovieWrapper(
                MovieDto.builder()
                        .director(1)
                        .title("Titile wfkwf")
                        .length("20:20:20")
                        .year(2000)
                        .rating(10)
                        .id(movieId2)
                        .build()
        );
        Assertions.assertDoesNotThrow(() -> movieService.save(movieWrapperCorrect));
        Assertions.assertEquals(movieWrapperCorrect.getMovie().getTitle(), movieService.findById(movieId2).getTitle());
    }

    @Test
    void deleteMovie() {
        Assertions.assertDoesNotThrow(() -> movieService.delete(movieId2));
        Assertions.assertThrows(EntityNotFoundException.class,() -> movieService.delete(movieId2));
        List<Movie> movies = movieService.findAll();
        Assertions.assertFalse(movies.isEmpty());
        Assertions.assertEquals(1, movies.size());
    }

}
