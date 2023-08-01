SELECT LEFT(match_name, INSTR(match_name, ' - ') - 1) 'Home team', LEFT(end_result, 1) 'Scored'
FROM match_result
WHERE LEFT(end_result, 1) = (
    SELECT MAX(LEFT(end_result, 1))
    FROM match_result
)
LIMIT 1