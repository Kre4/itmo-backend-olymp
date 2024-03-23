package com.itmo.olymp.service;

import com.itmo.olymp.dto.director.DirectorWrapper;
import com.itmo.olymp.entity.Director;
import com.itmo.olymp.exception.ValidationException;
import com.itmo.olymp.repository.DirectorRepository;
import com.itmo.olymp.utils.EntityMapper;
import com.itmo.olymp.utils.ValidationUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    public List<Director> findAll() {
        return directorRepository.findAll();
    }

    public Director findById(Integer id) {
        return directorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No director with id " + id));
    }

    public Director save(DirectorWrapper directorWrapper) throws ValidationException {
        if (directorWrapper.getDirector().getId() != null)
            findById(directorWrapper.getDirector().getId());
        Director director = EntityMapper.mapDirectorWrapperToEntity(directorWrapper);
        ValidationUtils.validateNotNull(director.getName(), "Fio");
        ValidationUtils.validateStringOrThrow(director.getName(), 100, "Fio");
        return directorRepository.save(director);
    }

    public void delete(Integer id) {
        Director director = findById(id);
        directorRepository.delete(director);
    }
}
