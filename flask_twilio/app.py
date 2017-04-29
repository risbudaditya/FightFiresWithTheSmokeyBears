#http://64a26bcb.ngrok.io


# debug mode for auto restart on file update

import psycopg2

from flask import Flask
from flask import request, jsonify

try:
    conn = psycopg2.connect("dbname='smokey' user='smokeyuser' host='localhost' password='onlyyoucanescapeforestfires'")
    cursor = conn.cursor()
except Exception as e:
    print("I am unable to connect to the database")
    raise e

cursor.execute(
    """CREATE TABLE IF NOT EXISTS destination (pk SERIAL PRIMARY KEY, latitude integer, longitude integer, UNIQUE(latitude, longitude));"""
)
cursor.execute(
    """CREATE TABLE IF NOT EXISTS person (pk SERIAL PRIMARY KEY, phone_number integer, latitude integer, longitude integer, UNIQUE(phone_number));"""
)
cursor.execute(
    """CREATE TABLE IF NOT EXISTS fire_point (pk SERIAL PRIMARY KEY, latitude integer, longitude integer, intensity integer, day text, time_string text, UNIQUE(latitude, longitude, day, time_string));"""
)
cursor.execute(
    """CREATE TABLE IF NOT EXISTS cluster (pk SERIAL PRIMARY KEY, latitude integer, longitude integer, number_of_people integer, UNIQUE(latitude, longitude));"""
)

#create database seeds
cursor.execute("""SELECT COUNT(*) FROM destination;""")
table_count = cursor.fetchall()
if table_count[0][0] == 0:
    cursor.execute(
        """INSERT INTO destination (latitude, longitude) VALUES (35260, -119673);"""
    )
    cursor.execute(
        """INSERT INTO destination (latitude, longitude) VALUES (36705, -119673);"""
    )
    cursor.execute(
        """INSERT INTO destination (latitude, longitude) VALUES (36297, -119156);"""
    )

cursor.execute("SELECT COUNT(*) FROM fire_point;")
table_count = cursor.fetchall()
if table_count[0][0] == 0:
    cursor.execute(
        "INSERT INTO fire_point (latitude, longitude, intensity, day, time_string) VALUES (36297, -119156, 3, '2017-04-28', '08:30PM');"
    )


cursor.execute("SELECT COUNT(*) FROM cluster;")
table_count = cursor.fetchall()
if table_count[0][0] == 0:
    cursor.execute(
        "INSERT INTO cluster (latitude, longitude, number_of_people) VALUES (36297, -119156, 15)"
    )

app = Flask(__name__)

@app.route("/message", methods=["POST"] )

def message():
    body = request.values.get("Body")
    number = request.values.get("From")
    media_url = request.values.get("MediaUrl0", "")
    return ("<Response><Message>{} from {} url {}</Message></Response>".format(body,number,media_url))

@app.route("/dummy", methods=["GET"])

def dummy():
    return 'Hello World'

@app.route("/route_info", methods=["GET"])

def route_info():
    result = {}
    cursor.execute("SELECT * FROM destination;")
    result['destinations'] = [
        (latitude / 1000, longitude / 1000) for pk, latitude, longitude in cursor.fetchall()
    ]
    cursor.execute("SELECT * FROM fire_point;")
    result['fires'] = [
        (
            latitude / 1000, longitude / 1000, intensity, day, time
        ) for pk, latitude, longitude, intensity, day, time in cursor.fetchall()
    ]
    cursor.execute("SELECT * FROM cluster;")
    result['clusters'] = [
        (latitude / 1000, longitude / 1000, num_people) for pk, latitude, longitude, num_people in cursor.fetchall()
    ]

    return jsonify(result)
