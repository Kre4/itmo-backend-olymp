package com.itmo.olymp.service;

import com.itmo.olymp.dto.movie.MovieWrapper;
import com.itmo.olymp.entity.Movie;
import com.itmo.olymp.exception.ValidationException;
import com.itmo.olymp.repository.MovieRepository;
import com.itmo.olymp.utils.EntityMapper;
import com.itmo.olymp.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No movie with id " + id));
    }

    public Movie save(MovieWrapper movieWrapper) throws ParseException, ValidationException {
        if (movieWrapper.getMovie().getId() != null)
            findById(movieWrapper.getMovie().getId());

        Movie movie = EntityMapper.mapMovieWrapperToEntity(movieWrapper);

        ValidationUtils.validateNotNull(movie.getDirector().getId(), "Director identifier ");
        ValidationUtils.validateNotNull(movie.getLength(), "Movie length");
        ValidationUtils.validateIntegerOrThrow(movie.getYear(), 1900, 2100, "Year");
        ValidationUtils.validateIntegerOrThrow(movie.getRating(), 0, 10, "Rating");
        ValidationUtils.validateStringOrThrow(movie.getTitle(), 100, "Titile");
        return movieRepository.save(movie);
    }

    public void delete(Integer id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }

}
