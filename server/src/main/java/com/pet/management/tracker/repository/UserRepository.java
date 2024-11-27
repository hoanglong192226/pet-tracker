package com.pet.management.tracker.repository;

import com.pet.management.tracker.model.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends BaseRepository<User, Long> {

  @Modifying
  @Query("DELETE FROM User p where p.id = :id")
  void deleteIfExist(@Param("id") Long id);

  List<User> findByUsername(String username);
}
