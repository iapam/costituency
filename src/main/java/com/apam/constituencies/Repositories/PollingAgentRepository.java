package com.apam.constituencies.Repositories;

import com.apam.constituencies.Model.Director;
import com.apam.constituencies.Model.PollingAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollingAgentRepository extends JpaRepository<PollingAgent,String> {
    PollingAgent findByPollingNumber(String pollingnumber);
  List<PollingAgent> findAllByEmail(String email);
    @Query("select director from PollingAgent where  pollingNumber=:pollingNumber")
    Director findDirector(String pollingNumber);
}
