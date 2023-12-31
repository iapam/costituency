package com.apam.constituencies.Repositories;

import com.apam.constituencies.Model.ParliamentaryCandidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaliamentaryCandidateRepository extends JpaRepository<ParliamentaryCandidates,Long> {

}
