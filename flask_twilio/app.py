#http://64a26bcb.ngrok.io


# debug mode for auto restart on file update

import psycopg2
import requests

from flask import Flask
from flask import request, jsonify

from twilio.rest import Client

from clarifai.rest import ClarifaiApp
import json
clar_app = ClarifaiApp("qhKfUc8b7dxgUFzvHrgFfTfIfdvN2jl3KGnqTOTH", "AwhJYXHKpwVcBGiRw_gXXyhnJl9ZMa1aCjJYV1bP")
# get the general model
model = clar_app.models.get("general-v1.3")


account_sid = "AC7ce393555d3134bc6987b90a015ee7c6"
auth_token = "2bc5b52462a84300a1fc6e5cb059e234"

client = Client(account_sid,auth_token)
twilio_number = 15097923063

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
    """CREATE TABLE IF NOT EXISTS person (pk SERIAL PRIMARY KEY, phone_number text, latitude integer, longitude integer, UNIQUE(phone_number));"""
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

    # This section is for victim flow

    #----------------------------------------------
    # body = request.values.get("Body")
    # print(body)
    # if body == 'victim_flag':
    #     # json = request.json()
    #     url = 'http://www.google.com'
    #     phone_number = '2018382463'
    #     client.messages.create(to=phone_number,from_=twilio_number,body=url)
    # else:
    #     number = request.values.get("From")
    #     media_url = request.values.get("MediaUrl0", "")
    # import re
    #     print(body)
    # r = requests.get(body.split()[-1])
    # a=re.search(r"""cacheResponse.*?,(.*?),(.*?)]""",str(r.content))
    # latitude = float(a.group(1))
    # longitude = float(a.group(2))
        # latitude = int(latitude * 1000)
        # longitude = int(longitude * 1000)
        # new_person = (number, latitude, longitude)
        # print(new_person)
        # print("coords: {},{}".format(latitude,longitude))
        # exists_query = """SELECT COUNT(*) FROM person WHERE phone_number=%s"""
        # cursor.execute(exists_query, (new_person[0],))
        # exists = cursor.fetchall()[0][0]
        # if exists:
        #     print('already exists')
        #     update_query = """UPDATE person SET latitude=%s, longitude=%s WHERE phone_number=%s"""
        #     cursor.execute(update_query, (new_person[1], new_person[2], new_person[0]))
        # else:
        #     print('new person saved')
        #     cursor.execute("""INSERT INTO person (phone_number, latitude, longitude) VALUES (%s,%s,%s)""", new_person)
        # print('start flow')
        # resp = requests.get('http://10.171.20.126:8080/MapsForUsers.jsp')
        # print(resp.status_code)

    # url = 'https://www.google.com/maps/dir/36.3258464,-118.8779856/36.297,-119.156/@36.2985292,-119.0846929,12z/data=!4m2!4m1!3e0'
    # phone_number = '2018382463'
    # client.messages.create(to=phone_number,from_=twilio_number,body=url)
    # msg = "Got It. Your location has been registered ({}, {}). Help is on the way. Please follow the linked route to temporary safety.  If you see anything dangerous along the way, please send us a photo.".format(
    #     latitude, longitude
    # )

    # return ("<Response><Message>{}</Message></Response>".format(msg))

#-------------------------------------------



        #This section is for Clarifai flow

        # clarify code.... how to run things

    # media_url = request.values.get("MediaUrl0", "")
    # b = model.predict_by_url(url=media_url)
    #
    # fire_found = False
    # for i in b['outputs'][0]['data']['concepts']:
    #     if 'fire' in i['name'] or 'smoke' in i['name'] or 'flame' in i['name']:
    #         fire_found = True
    # msg = 'Fire Identified' if fire_found else 'This does not appear to be a fire, please send a clearer image'
    # return ("<Response><Message>{}</Message></Response>".format(msg))



@app.route("/victims", methods=["GET"])
def victims():
    print('hit victims')
    result = {}
    cursor.execute("SELECT * FROM person;")
    result['victims'] = [
        (
            phone_number, latitude / 1000, longitude / 1000
        ) for pk, phone_number, latitude, longitude in cursor.fetchall()
    ]
    return jsonify(result)

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
