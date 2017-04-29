# my number is 15097923063 for the server

from twilio.rest import Client

account_sid = "AC7ce393555d3134bc6987b90a015ee7c6"
auth_token = "2bc5b52462a84300a1fc6e5cb059e234"

client = Client(account_sid,auth_token)

client.messages.create(to="5095390603",from_="15097923063",body="message text goes here")