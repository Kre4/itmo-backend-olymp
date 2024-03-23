package com.itmo.olymp.api;

import com.itmo.olymp.dto.ErrorResponse;
import com.itmo.olymp.dto.movie.MovieListWrapper;
import com.itmo.olymp.dto.movie.MovieWrapper;
import com.itmo.olymp.service.MovieService;
import com.itmo.olymp.utils.EntityMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/movies")
@AllArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<MovieListWrapper> getAllMovies() {
        return ResponseEntity.ok(EntityMapper.mapMovieEntityList(movieService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieWrapper> getMovieById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(EntityMapper.mapMovieEntityToWrapper(movieService.findById(id)));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveMovie(@RequestBody MovieWrapper body) {
        try {
            body.getMovie().setId(null);
            return ResponseEntity.ok(EntityMapper.mapMovieEntityToWrapper(movieService.save(body)));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Integer id, @RequestBody MovieWrapper body) {
        try {
            body.getMovie().setId(id);
            return ResponseEntity.ok(EntityMapper.mapMovieEntityToWrapper(movieService.save(body)));
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
        try {
            movieService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, exception.getMessage()));
        }
    }

}
