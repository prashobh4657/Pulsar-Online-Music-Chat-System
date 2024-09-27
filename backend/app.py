from flask import Flask, request, jsonify
from flask_cors import CORS
# from flask_mysqldb import MySQL
# from flask_sqlalchemy import SQLAlchemy
import datetime
import time
import json
import jwt

app = Flask(__name__)
# app.config['MYSQL_HOST'] = 'localhost'
# app.config['MYSQL_USER'] = 'root'
# app.config['MYSQL_PASSWORD'] = 'Sunnipee20#mysql'
# app.config['MYSQL_DB'] = 'dbms_mini_project'

# db = SQLAlchemy()
# app.config["SQLALCHEMY_DATABASE_URI"] = "mysql://root:Sunnipee20#mysql@localhost/dbms_mini_project"
# db.init_app(app)

CORS(app)
# mysql = MySQL(app)

@app.route("/")
def home():
    return "Home"

secret_key = "dbms"

users = [] #array of objects;
@app.route("/users", methods=["GET"])
def getAllUsers():
    return jsonify(users), 200

# New User - Signup
@app.route("/signup", methods=["POST"])
def userRegister():
    data = request.json
    fullname = data.get('fullname')
    username = data.get('username')
    email = data.get('email')
    password = data.get('password')

     # Check if username already exists
    if any(user['email'] == email for user in users):
        return jsonify({
            "success": False, 
            "error": { 
                "fields": {
                    "email": "Email already exists"
                }
            }
        }), 400
    if any(user['username'] == username for user in users):
        return jsonify({'message': 'Username already exists'}), 400
    
    user_id = len(users) + 1

    # Save user data
    user = {
        'id': user_id,
        'name': fullname,  # Store only 'id' and 'name' fields
        'username': username,
        'email': email,
        'password': password
    }
    users.append(user)
    return jsonify({"success": True, "message": "Signup successful"}), 201



# User - Login
@app.route("/login", methods=["POST"])
def userLogin():
    data = request.json
    username = data.get('username')
    password = data.get('password')
    
    isUserNameFound = False
    isPasswordMatch = False
    user_data = {}

    for user in users:
        if username == user['username']:
            IsUserNameFound = True
            if password == user['password']:
                isPasswordMatch = True
                user_data = {
                    "id": user["id"],
                    "username": user["username"],
                    "email": user["email"],
                    "fullname": user["name"]
                }
                break

    if isPasswordMatch:
        response = {
            "success": True,
            "data": user_data,
            "message": "Login successful"
        }
        return jsonify(response)
    else:
        error = {}
        fields = {}
        if not isUserNameFound:
            fields['username'] = "Username not found"
        elif not isPasswordMatch:
            fields['password'] = "Wrong password"
        
        error['fields'] = fields
        response = {
            "success": False,
            "error": error,
            "message": "Login failed"
        }
        return jsonify(response), 400
    





# Creation of database 
user_info = []
songs = []
friends = []
artist_fan_group = []
group_members = []
favourite_song_list = []
playlist_info = []
favourite_playlist = []
playlist_songs = []
private_message = []
group_message = []

def get_next_id(table):
    return len(table) + 1  # Use the length of the list as the next ID

def insert_user_info(values):
    user_id = get_next_id(user_info)
    user_info.append({
        'id': user_id,  # Add id field
        'fullname': values['fullname'],
        'username': values['username'],
        'email': values['email'],
        'password': values['password']
    })

def insert_songs(values):
    sid = get_next_id(songs)  # Use get_next_id for song ID
    songs.append({
        'id': sid,  # Add id field
        'title': values['title'],
        'artist': values['artist'],
        'album': values['album'],
        'year': int(values['year']),
        'durationsec': int(values['durationsec'])
    })

def insert_friends(values):
    friends.append({
        'userid': int(values['userid']),
        'friendid': int(values['friendid'])
    })

def insert_artist_fan_group(values):
    group_id = get_next_id(artist_fan_group)  # Use get_next_id for group ID
    artist_fan_group.append({
        'id': group_id,  # Add id field
        'artist': values['artist'],
        'groupname': values['groupname'],
        'description': values['description']
    })

def insert_group_members(values):
    group_members.append({
        'groupid': int(values['groupid']),
        'userid': int(values['userid'])
    })

def insert_favourite_song_list(values):
    favourite_song_list.append({
        'userid': int(values['userid']),
        'sid': int(values['sid'])
    })

def insert_playlist_info(values):
    pid = get_next_id(playlist_info)  # Use get_next_id for playlist ID
    playlist_info.append({
        'id': pid,  # Add id field
        'pname': values['pname']
    })

def insert_favourite_playlist(values):
    favourite_playlist.append({
        'userid': int(values['userid']),
        'pid': int(values['pid'])
    })

def insert_playlist_songs(values):
    playlist_songs.append({
        'pid': int(values['pid']),
        'sid': int(values['sid'])
    })

def insert_private_message(values):
    msgid = get_next_id(private_message)  # Use get_next_id for message ID
    private_message.append({
        'id': msgid,  # Add id field
        'message_date': values['message_date'],
        'from1': int(values['from1']),
        'to1': int(values['to1']),
        'subject': values['subject'],
        'body': values['body']
    })

def insert_group_message(values):
    msgid = get_next_id(group_message)  # Use get_next_id for message ID
    group_message.append({
        'id': msgid,  # Add id field
        'message_date': values['message_date'],
        'from1': int(values['from1']),
        'groupid': int(values['groupid']),
        'subject': values['subject'],
        'body': values['body']
    })

table_insert_map = {
    'user_info': insert_user_info,
    'songs': insert_songs,
    'friends': insert_friends,
    'artist_fan_group': insert_artist_fan_group,
    'group_members': insert_group_members,
    'favourite_song_list': insert_favourite_song_list,
    'playlist_info': insert_playlist_info,
    'favourite_playlist': insert_favourite_playlist,
    'playlist_songs': insert_playlist_songs,
    'private_message': insert_private_message,
    'group_message': insert_group_message
}

@app.route('/insert', methods=['POST'])
def insert_data():
    data = request.json
    
    # Expecting the JSON structure to have 'table' and 'values'
    table_name = data['table']
    values = data['values']
    
    # Check if the table name is valid and call the corresponding insert function
    if table_name in table_insert_map:
        # Ensure values are a list of dictionaries
        if isinstance(values, list) and all(isinstance(value, dict) for value in values):
            for value in values:
                table_insert_map[table_name](value)  # Insert each object
            return jsonify({"status": "success", "message": f"Data inserted into {table_name}"}), 201
        else:
            return jsonify({"status": "error", "message": "Invalid values format; must be a list of dictionaries"}), 400
    else:
        return jsonify({"status": "error", "message": "Invalid table name"}), 400

# Endpoint to get current in-memory data
@app.route('/data/<table_name>', methods=['GET'])
def get_data(table_name):
    if table_name == 'user_info':
        return jsonify(user_info)
    elif table_name == 'songs':
        return jsonify(songs)
    elif table_name == 'friends':
        return jsonify(friends)
    elif table_name == 'artist_fan_group':
        return jsonify(artist_fan_group)
    elif table_name == 'group_members':
        return jsonify(group_members)
    elif table_name == 'favourite_song_list':
        return jsonify(favourite_song_list)
    elif table_name == 'playlist_info':
        return jsonify(playlist_info)
    elif table_name == 'favourite_playlist':
        return jsonify(favourite_playlist)
    elif table_name == 'playlist_songs':
        return jsonify(playlist_songs)
    elif table_name == 'private_message':
        return jsonify(private_message)
    elif table_name == 'group_message':
        return jsonify(group_message)
    else:
        return jsonify({"status": "error", "message": "Invalid table name"}), 400

if __name__ == '__main__':
    app.run(debug=True)
# Get list of all Users
# @app.route("/users", methods=["GET"])
# def getAllUsers(id):

    # query = 'select * from user_info;'

    # data_raw = db.engine.execute(query)

    # data = []
    # for item in data_raw:
    #     temp = {}    
    #     temp['id'] = item['id']
    #     temp['fullname'] = item['fullname']
    #     temp['username'] = item['username']
    #     temp['email'] = item['email']
    #     data.append(temp)

    # return json.dumps({ "data": data }, default=str)


# #Get dashboard data of loggedIn User
# @app.route("/home", methods=["GET"])
# def getDashboard():

#     #top friends
#     query = 'select fullname, id from user_info U,(select friendid from friends where userid=1) F where U.id=F.friendid limit 5'

#     data_raw = db.engine.execute(query)

#     data = []
#     obj={}
#     obj['title']='Friends'
#     obj['content']=[]
#     for item in data_raw:
#         temp={}
#         temp['fullname'] = item['fullname']
#         temp['id']=item['id']
#         obj['content'].append(temp)
#     data.append(obj)


#     #top songs
#     query = 'select title, S.sid from songs S,(select sid from favourite_song_list where userid=1) F where S.sid=F.sid limit 5'

#     data_raw = db.engine.execute(query)

#     obj={}
#     obj['title']='Songs'
#     obj['content']=[]
#     for item in data_raw:
#         temp={}
#         temp['title'] = item['title']
#         temp['sid']=item['sid']

#         obj['content'].append(temp)
#     data.append(obj)

#     #top groups
#     query = 'select groupname, A.groupid from artist_fan_group A,(select groupid from group_members where userid=1) F where A.groupid=F.groupid limit 10'
#     data_raw = db.engine.execute(query)

#     obj={}
#     obj['title']='Groups'
#     obj['content']=[]
#     for item in data_raw:
#         temp={}
#         temp['groupname'] = item['groupname']
#         temp['groupid']=item['groupid']

#         obj['content'].append(temp)
#     data.append(obj)

#     #top playlists
#     query = 'select pname, P.pid from playlist_info P,(select pid from  favourite_playlist where userid=1) F where P.pid=F.pid limit 5'
#     data_raw = db.engine.execute(query)
    
#     obj={}
#     obj['title']='Playlists'
#     obj['content']=[]
#     for item in data_raw:
#         temp={}
#         temp['pname'] = item['pname']
#         temp['pid']=item['pid']
#         obj['content'].append(temp)
#     data.append(obj)

#     return json.dumps({ "data": data }, default=str)

# #Get friends of loggedIn User
# @app.route("/friends", methods=["GET"])
# def getFriends():

#     #all friends
#     query = 'select fullname, id, username from user_info U,(select friendid from friends where userid=2) F where U.id=F.friendid'

#     data_raw = db.engine.execute(query)

#     data = []
#     obj={}
#     obj['title']='Friends'
#     obj['content']=[]
#     for item in data_raw:
#         temp={}
#         temp['fullname'] = item['fullname']
#         temp['id']=item['id']
#         obj['content'].append(temp)
#     data.append(obj)

#     return json.dumps({ "data": data }, default=str)

# #Get playlists of loggedIn User
# @app.route("/playlists", methods=["POST"])
# def getPlaylists():

#     #all playlists
#     userid = request.json["userId"]
#     query = 'select pname, P.pid from playlist_info P,(select pid from  favourite_playlist where userid="%d") F where P.pid=F.pid;' % (int(userid))

#     data_raw = db.engine.execute(query)

#     obj={}
#     obj['title']='Playlists'
#     obj['content']=[]
#     for item in data_raw:
#         temp={}
#         temp['name'] = item['pname']
#         temp['id'] = item['pid']
#         temp['type'] = 'playlist'
#         temp['following'] = True
#         obj['content'].append(temp)

#     return json.dumps({ "data": obj, "success": True}, default=str)

# #Get playlist songs of selected playlist
# @app.route("/playlist", methods=["POST"])
# def getPlaylistSongs():

#     #all playlists
#     userid = request.json["userId"]
#     playlistid = request.json["playlistId"]

#     query = 'select pname from playlist_info where pid = %d;' % (int(playlistid))
#     playlist_name = db.engine.execute(query)
#     print(playlist_name)
    
#     query = 'select * from songs S,(select sid from playlist_songs where pid="%d") F where S.sid=F.sid;' % (int(playlistid))

#     data_raw = db.engine.execute(query)

#     obj={}
#     obj['id'] = playlistid
#     obj['name'] = playlist_name
#     songs=[]
#     for song in data_raw:
#         temp={}
#         temp['id'] = song['sid']
#         temp['name'] = song['title']
#         temp['artist'] = song['artist']
#         temp['durationInSec'] = song['durationsec']
#         temp['album'] = song['album']
#         temp['art']=''
#         temp['release'] = song['year']
#         temp['playlistid'] = playlistid

#         query = 'select count(*) from (SELECT * FROM favourite_song_list where userid = %d and sid = %d) a;' % (int(userid), (int(song['sid'])))
#         favourite = db.engine.execute(query)

#         if(favourite==1):
#             temp['liked'] = True
#         else:
#             temp['liked'] = False

#         songs.append(temp)

#     obj['songs'] = songs

#     return json.dumps({ "data": obj, "success": True}, default=str)



