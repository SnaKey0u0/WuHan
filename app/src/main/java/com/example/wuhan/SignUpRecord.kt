package com.example.wuhan

class SignUpRecord {
    val name: String
    val id: String
    val phone: String
    val location: String
    val time: String

    constructor(name: String, id: String, phone: String, location: String, time: String) {
        this.name = name
        this.id = id
        this.phone = phone
        this.location = location
        this.time = time
    }

    override fun toString(): String {
        return "SignUpRecord(name='$name', id='$id', phone='$phone', location='$location', time='$time')"
    }
}
