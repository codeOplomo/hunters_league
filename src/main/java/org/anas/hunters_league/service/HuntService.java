package org.anas.hunters_league.service;

import org.anas.hunters_league.domain.Hunt;
import org.anas.hunters_league.repository.HuntRepository;

public class HuntService {

    private final HuntRepository huntRepository;

    public HuntService(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    public Hunt createHunt(Hunt hunt) {
        return huntRepository.save(hunt);
    }
}
