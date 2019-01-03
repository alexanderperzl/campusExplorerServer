package com.example.campusexplorer.storage

import com.example.campusexplorer.model.Building
import com.example.campusexplorer.model.Floor
import com.example.campusexplorer.model.Room
import java.util.logging.Logger

object Storage {

    private val log = Logger.getLogger(Storage::class.java.name)
    private var roomData: MutableMap<String, Pair<Building, MutableMap<String, Pair<Floor, MutableMap<String, Room>>>>> = HashMap()
    private var floorById: MutableMap<String, Floor> = HashMap()
    private var roomById: MutableMap<String, Room> = HashMap()

    fun init(initBuildings: List<Building>, initFloors: List<Floor>, initRooms: List<Room>) {
        initBuildings(initBuildings)
        initFloors(initFloors)
        initRooms(initRooms)
        log.info("Stored ${roomData.count()} buildings, ${floorById.count()} floors and ${roomById.count()} rooms")
    }

    private fun initBuildings(initBuildings: List<Building>) {
        initBuildings.forEach { building ->
            roomData[building._id] = Pair(building, HashMap())
        }
    }

    private fun initFloors(initFloors: List<Floor>) {
        initFloors.forEach { floor ->
            roomData[floor.building]!!.second[floor._id] = Pair(floor, HashMap())
            floorById[floor._id] = floor
        }
    }

    private fun initRooms(initRooms: List<Room>) {
        initRooms.forEach { room ->
            val building = findBuilding(room.floor)
            if (building != null) {
                roomData[building._id]!!.second[room.floor]!!.second[room._id] = room
                roomById[room._id] = room
            }
        }
    }

    fun findBuilding(floorId: String): Building? {
        val buildingId = floorById[floorId]?.building ?: return null
        return roomData[buildingId]?.first
    }

    fun getAllBuildings(): MutableMap<String, Pair<Building, MutableMap<String, Pair<Floor, MutableMap<String, Room>>>>> {
        return roomData
    }

    fun findRoom(buildingId: String, floorId: String, roomName: String): Room? {
        val floors = roomData[buildingId]?.second ?: return null
        val rooms = floors[floorId]?.second ?: return null
        val room = rooms.values.filter { it.name == roomName }
        return if (room.count() > 0) {
            room[0]
        } else {
            null
        }
    }

    fun findRoom(roomId: String): Room? {
        return roomById[roomId]
    }

}