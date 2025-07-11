package org.example.expert.domain.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogService {
    private final org.example.expert.domain.log.LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(String message){
        logRepository.save(new org.example.expert.domain.log.Log(message));
    }
}
