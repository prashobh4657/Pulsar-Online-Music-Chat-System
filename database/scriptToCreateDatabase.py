import requests
import json
import os

# Base URL for the Flask API
BASE_URL = "http://127.0.0.1:5000"

try:
    os.chdir("/Users/prashobh/Desktop/PROJECT1/database")  # Set the correct path to your JSON files
    print(f"Current working directory: {os.getcwd()}")  # Verify the current working directory
except FileNotFoundError:
    print("Directory not found, please check the path.")
    exit()

# List of tables and their corresponding JSON file names
tables = {
    "user_info": "user_info.json",
    "songs": "songs.json",
    "friends": "friends.json",
    "artist_fan_group": "artist_fan_group.json",
    "group_members": "group_members.json",
    "favourite_song_list": "favourite_song_list.json",
    "playlist_info": "playlist_info.json",
    "favourite_playlist": "favourite_playlist.json",
    "playlist_songs": "playlist_songs.json",
    "private_message": "private_message.json",
    "group_message": "group_message.json"
}


def read_json_file(filename):
    """Read a JSON file and return its contents."""
    try:
        with open(filename, 'r') as file:
            return json.load(file)
    except FileNotFoundError:
        print(f"File {filename} not found!")
    except json.JSONDecodeError:
        print(f"Error decoding JSON from the file {filename}!")
    return None

def insert_data(data):
    url = f"{BASE_URL}/insert"
    response = requests.post(url, json=data)
    print(f"Response Status Code: {response.status_code}")
    if response.status_code == 201:
        print(f"Success: {response.json()}")
    else:
        print(f"Failed: {response.json()}")


# Insert dummy data for each table
for table, json_file in tables.items():
    if os.path.exists(json_file):
        data = read_json_file(json_file)
        # print(f"Inserting data for {table}") 
        insert_data({"table": table, "values": data})  # Call insert_data with the whole entry
    else:
        print(f"File {json_file} not found!")


# RUN THIS SCRIPT WHEN CURRENT DIRECTORY IS IN DATABASE FOLDER ONLY;
# print(f"Current working directory: {os.getcwd()}")  (IT SHOULD PRINT /database folder in which json files are present);
