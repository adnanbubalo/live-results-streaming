package com.adnan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, Integer> {
    @Query(value = "SELECT LEFT(match_name, INSTR(match_name, ' - ') - 1) 'Home team', LEFT(end_result, 1) 'Scored'" +
            " FROM match_result" +
            " WHERE LEFT(end_result, 1) = " +
            " (SELECT MAX(LEFT(end_result, 1))" +
            " FROM match_result)" +
            " LIMIT 1",
            nativeQuery = true)
    String findBestScoringHomeTeam();
}
