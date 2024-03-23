package com.itmo.olymp.repository;

import com.itmo.olymp.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository  extends JpaRepository<Director, Integer> {
}
