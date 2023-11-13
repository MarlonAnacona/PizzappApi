package com.API.Pizzapp.Repository;

import com.API.Pizzapp.Models.RestauranEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResturantRepository extends JpaRepository<RestauranEntity,Long> {
}
