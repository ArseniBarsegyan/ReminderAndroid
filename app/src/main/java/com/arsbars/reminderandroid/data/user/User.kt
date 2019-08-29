package com.arsbars.reminderandroid.data.user

class User {
    var id: Long = 0
    var username: String? = null
    var password: ByteArray? = null
    var imageContent: ByteArray? = null

    internal constructor(id: Long, username: String, password: ByteArray, imageContent: ByteArray) {
        this.id = id
        this.username = username
        this.password = password
        this.imageContent = imageContent
    }

    internal constructor(user: User) {
        this.id = user.id
        this.username = user.username
        this.password = user.password
        this.imageContent = user.imageContent
    }
}
