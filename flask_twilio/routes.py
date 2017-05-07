import psycopg2
from googlemaps import client
import sys

key= "A GOOGLE API KEY AUTHORIZED ON SEVERAL DIRECTIONS APIS"
myClient = client.Client(key)

def getRoutes(cursor) :
    valsToText = []
    cursor.execute("""select * from person""")
    person=cursor.fetchone()
    while(person):
        [minCluster,minDirections] = getClosestCluster(cursor,person)
        urlForRoute = getURLForRoute(cursor, minDirections,person)
        valsToText.append([person[1], urlForRoute])
        person = cursor.fetchone()
    return valsToText

def getClosestCluster(cursor,person) :
    cursor.execute("""select * from cluster""")
    cluster = cursor.fetchone()
    minDistance = sys.maxsize
    minCluster = None
    minDirections = None
    while(cluster):
        directions = myClient.directions(getCoordsForGoogle(person[2],person[3]),getCoordsForGoogle(cluster[1],cluster[2]),alternatives=True)
        distance = directions[0].get('legs')[0].get('duration').get('value')
        if(distance<minDistance):
            minDistance=distance
            minCluster=cluster
            minDirections = directions
        cluster = cursor.fetchone()
    return [minCluster, minDirections]

def getCoordsForGoogle(lat,lon):
    if abs(lat) > 1000:
        lat = lat/1000
    if abs(lon) > 1000:
        lon = lon/1000
    return str(lat)+","+str(lon)

def getURLForRoute(cursor, minDirections, person):
    aFireRoute = FireRoute()
    aFireRoute.addLatLon(person[2],person[3])
    for route in minDirections:
        if not routeHasFires(cursor,route):
            for step in route['legs'][0]['steps']:
                aFireRoute.addLatLon(step['end_location']['lat'],step['end_location']['lng'])
        return aFireRoute.getURL()
    return ""


def routeHasFires(cursor, route):
    cursor.execute("""select * from fire_point""")
    fire_point = cursor.fetchone()
    while(fire_point):
        for step in route.get('legs')[0]['steps']:
            endLoc = step['end_location']
            if(distFrom(endLoc['lat'], endLoc['lng'],fire_point[1], fire_point[2])<1000):
                return True
        fire_point = cursor.fetchone()
    return False

def distFrom(lat1, lon1, lat2, lon2):
    from math import sin, cos, sqrt, atan2, radians
    earthRadius = 6371000
    dlat = radians(lat2-lat1)
    dlon = radians(lon2-lon1)
    a = sin(dlat/2)**2 + cos(radians(lat1)) * cos(radians(lat2)) * sin(dlon/2)**2
    c = 2 * atan2(sqrt(a), sqrt(1-a))
    return earthRadius * c

class FireRoute:
    #aList = []
    PREFIX = "https://www.google.com/maps/dir"
    DELIMITER = "/"
    def __init__(self):
        self.aList = []

    def addLatLon(self,lat, lon):
        self.aList.append(getCoordsForGoogle(lat,lon))

    def getURL(self):
        aURL = FireRoute.PREFIX
        for loc in self.aList:
            aURL+= FireRoute.DELIMITER + loc
        return aURL
