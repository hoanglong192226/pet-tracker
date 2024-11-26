package com.pet.management.tracker.repository;

import com.pet.management.tracker.model.entity.Pet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PetRepository extends BaseRepository<Pet, Long> {

  @Modifying
  @Query("DELETE FROM Pet p where p.id = :id")
  void deleteIfExist(@Param("id") Long id);
}
