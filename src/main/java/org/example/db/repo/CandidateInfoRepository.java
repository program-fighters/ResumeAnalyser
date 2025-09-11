package org.example.db.repo;

import org.example.db.model.CandidatesInfo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateInfoRepository extends MongodbRepository<CandidatesInfo, String> {
    Optional<CandidatesInfo> findByEmail(String s);
}
