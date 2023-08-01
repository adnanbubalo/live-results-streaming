import random
import time
import uuid
import requests

club_names = [
    "Real Madrid",
    "Barcelona",
    "Manchester United",
    "Manchester City",
    "Zrinjski",
    "Velez",
    "Zeljeznicar",
    "Liverpool",
    "Arsenal",
    "Paris Saint-Germain",
]

def generate_match_result_event():
    match_id = str(uuid.uuid4())
    team1, team2 = random.sample(club_names, 2)
    match_name = team1 + " - " + team2
    end_result = f"{random.randint(0, 7)} : {random.randint(0, 7)}"
    return {"matchId": match_id, "matchName": match_name, "endResult": end_result}

def match_end_result_generator():
    while True:
        yield generate_match_result_event()
        time.sleep(1)

def send_match_result_event(match_event):
    url = "http://localhost:8080/api/save-end-result"
    headers = {"Content-Type": "application/json"}
    response = requests.post(url, json=match_event, headers=headers)
    if response.status_code == 201:
        print(f"Match result sent successfully: {match_event['matchName']} {match_event['endResult']}")
    else:
        print(f"Failed to send match result: {match_event['matchName']} {match_event['endResult']}")

if __name__ == "__main__":
    generator = match_end_result_generator()
    try:
        while True:
            match_event = next(generator)
            send_match_result_event(match_event)
    except KeyboardInterrupt:
        print("Generator stopped.")
