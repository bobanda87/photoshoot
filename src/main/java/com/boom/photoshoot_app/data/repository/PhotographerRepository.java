package com.boom.photoshoot_app.data.repository;

import com.boom.photoshoot_app.data.models.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
