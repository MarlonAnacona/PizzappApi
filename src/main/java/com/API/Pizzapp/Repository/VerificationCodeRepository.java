package com.API.Pizzapp.Repository;

import com.API.Pizzapp.Models.CodeVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<CodeVerification, Long> {
    @Query("SELECT cv FROM CodeVerification cv JOIN cv.user u WHERE cv.code = :code AND u.email = :email")
    Optional<CodeVerification> findByCodeAndUserEmail(@Param("code") String code, @Param("email") String email);

}
