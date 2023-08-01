package com.adnan.service;

import com.adnan.dto.MatchResultDTO;
import com.adnan.repository.MatchResultRepository;
import com.adnan.model.MatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MatchResultService {
    private final MatchResultRepository matchResultRepository;
    private final Map<String, MatchResultDTO> pendingEvents = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public MatchResultService(MatchResultRepository matchResultRepository) {
        this.matchResultRepository = matchResultRepository;
        startEventInsertionThread();
    }
    private void startEventInsertionThread() {
        executorService.submit(() -> {
            while (true) {
                try {
                    for (Map.Entry<String, MatchResultDTO> entry : pendingEvents.entrySet()) {
                        MatchResultDTO event = entry.getValue();
                        MatchResult matchResult = new MatchResult();
                        matchResult.setMatchId(event.matchId);
                        matchResult.setMatchName(event.matchName);
                        matchResult.setEndResult(event.endResult);
                        matchResultRepository.save(matchResult);

                        pendingEvents.remove(entry.getKey());
                    }

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public List<MatchResult> getAll() {
        return matchResultRepository.findAll();
    }
    public void add(MatchResultDTO event) {
        pendingEvents.put(event.matchId, event);
    }

    public String getBestScoringHomeTeam() {
        return matchResultRepository.findBestScoringHomeTeam();
    }
}
