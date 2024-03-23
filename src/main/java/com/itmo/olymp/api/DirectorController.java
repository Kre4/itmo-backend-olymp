package com.itmo.olymp.api;

import com.itmo.olymp.dto.ErrorResponse;
import com.itmo.olymp.dto.director.DirectorListWrapper;
import com.itmo.olymp.dto.director.DirectorWrapper;
import com.itmo.olymp.service.DirectorService;
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
@RequestMapping(value = "/api/directors")
@AllArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public ResponseEntity<DirectorListWrapper> getAllDirectors() {
        return ResponseEntity.ok(EntityMapper.mapDirectorEntityList(directorService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorWrapper> getDirectorById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(EntityMapper.mapDirectorEntityToWrapper(directorService.findById(id)));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveDirector(@RequestBody DirectorWrapper body) {
        try {
            body.getDirector().setId(null);
            return ResponseEntity.ok(EntityMapper.mapDirectorEntityToWrapper(directorService.save(body)));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDirector(@PathVariable Integer id, @RequestBody DirectorWrapper body) {
        try {
            body.getDirector().setId(id);
            return ResponseEntity.ok(EntityMapper.mapDirectorEntityToWrapper(directorService.save(body)));
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, exception.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable Integer id) {
        try {
            directorService.delete(id);
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
