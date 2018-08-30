package com.sikza.socksapi.models

class Message(val from: String, val replyTo: String, val body: String, val to: String) {

    override fun toString(): String {
        return "Message(from='$from', replyTo='$replyTo', body='$body', to='$to')"
    }
}