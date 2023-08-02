package com.adnan.service;

import com.adnan.dto.MatchResultDTO;
import com.adnan.repository.MatchResultRepository;
import com.adnan.model.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatchResultService {
    private final MatchResultRepository matchResultRepository;
    private final Map<String, MatchResultDTO> pendingEvents = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MatchResultService(MatchResultRepository matchResultRepository) {
        this.matchResultRepository = matchResultRepository;
    }
    public void startEventInsertionThread() {
        executorService.submit(() -> {
            while (true) {
                try {
                    for (Map.Entry<String, MatchResultDTO> entry : pendingEvents.entrySet()) {
                        MatchResultDTO event = entry.getValue();
                        MatchResult matchResult = new MatchResult();
                        matchResult.setMatchId(event.getMatchId());
                        matchResult.setMatchName(event.getMatchName());
                        matchResult.setEndResult(event.getEndResult());

                        saveAndRemoveFromMap(matchResult, entry.getKey());
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void saveAndRemoveFromMap(MatchResult matchResult, String key) {
        try {
            matchResultRepository.save(matchResult);
            pendingEvents.remove(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<MatchResult> getAll(Integer page, Integer size)
    {
        Page<MatchResult> matchResultPage = matchResultRepository.findAll(PageRequest.of(page, size));
        return matchResultPage.getContent();
    }
    public void add(MatchResultDTO event) {
        pendingEvents.put(event.getMatchId(), event);
    }

    public String getBestScoringHomeTeam() {
        return matchResultRepository.findBestScoringHomeTeam();
    }
}
