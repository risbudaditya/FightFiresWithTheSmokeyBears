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
