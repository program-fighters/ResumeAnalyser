package org.example.service.impls;

import org.example.db.repo.CandidateInfoRepository;
import org.example.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseServiceImpl implements BaseService {
   @Autowired protected CandidateInfoRepository candidateInfoRepository;



}
