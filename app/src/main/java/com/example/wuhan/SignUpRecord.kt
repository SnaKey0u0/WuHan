package com.example.wuhan

class SignUpRecord {
    val name: String
    val room: String
    val phone: String
    val location: String
    val time: String

    constructor(name: String, room: String, phone: String, location: String, time: String) {
        this.name = name
        this.room = room
        this.phone = phone
        this.location = location
        this.time = time
    }
}
