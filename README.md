# Live match results streaming
The match-end-result-generator generates a stream of match results data that the service saves in a database.

To start:
1. Open Docker Desktop
2. Run ```docker compose up``` in the service terminal
3. Start the service
4. Start the generator with: ```python match-end-result-generator.py```

The generator now continously generates data every 1s that is being saved to the database in the docker container.\
CTRL+C to end the generator.

The service has the following API methods:
- ```localhost:8080/api/results``` (GET) returns all match results
- ```localhost:8080/api/save-end-result``` (POST) saves a match result in the database
- ```localhost:8080/api/best-scoring-home-team``` (GET) return a home team that scored the most number of goals along with the number of goals

Adminer is on ```localhost:9000``` and can be used to manage the database. Login with user: root and password: root
