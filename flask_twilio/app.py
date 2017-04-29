#http://64a26bcb.ngrok.io


# debug mode for auto restart on file update

from flask import Flask
from flask import request, jsonify

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
    result['destinations'] = [
        (36.25977, -119.67269),
        (36.70476, -120.00366),
        (36.29741, -119.15634)
    ]
    result['fires'] = [
        # (lat, lon, intensity)
        (36.52619, -119.57656, 3)
    ]
    result['clusters'] = [
        # (lat, lon, number_of_people),
        (36.74872, -119.28131, 15)
    ]

    return jsonify(result)
