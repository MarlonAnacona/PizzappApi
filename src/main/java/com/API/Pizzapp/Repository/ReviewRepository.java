package com.API.Pizzapp.Repository;

import com.API.Pizzapp.Models.ReseñaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReseñaEntity,Long> {
}
