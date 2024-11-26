package com.pet.management.tracker.repository;

import com.pet.management.tracker.model.entity.Owner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends BaseRepository<Owner, Long> {

  @Modifying
  @Query("DELETE FROM Owner o where o.id = :id")
  void deleteIfExist(@Param("id") Long id);
}
