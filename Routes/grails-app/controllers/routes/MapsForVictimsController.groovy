package routes
import Controllers.MapsForUsers

class MapsForVictimsController {

    def mapsForVictims() {
        render Controllers.MapsForUsers.mapsForUsers()
    }
}
