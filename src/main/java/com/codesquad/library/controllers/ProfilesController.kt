package com.codesquad.library.controllers

import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfilesController constructor(private val environment: Environment) {

    @GetMapping("/profile")
    fun getProfile() : String {
        return environment.activeProfiles.getOrElse(0) {"no profiles found!"}
    }
}