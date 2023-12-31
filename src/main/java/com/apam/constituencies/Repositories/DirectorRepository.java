package com.apam.constituencies.Repositories;

import com.apam.constituencies.Model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director,String> {
    Director findByDirectorid(String email);
    Director findByConstituencyid(String constituencyid);
    Optional<Director>findDirectorByDirectorid(String email);
    @Modifying
    @Transactional
    @Query("update Director set password=:password where directorid=:pollingnumber")
    void updatePollingAgent(String pollingnumber, String password);
}
