# my twilio_number is 15097923063 for the server

from twilio.rest import Client

account_sid = "AC7ce393555d3134bc6987b90a015ee7c6"
auth_token = "2bc5b52462a84300a1fc6e5cb059e234"

client = Client(account_sid,auth_token)

twilio_number = 15097923063
all_numbers = [9174029869,8482050489,5095390603,7035172175,9142180462,9083043685,2018382463]

for num in all_numbers:
    client.messages.create(to=num,from_=twilio_number,body="Welcome to Smokey Bears!")