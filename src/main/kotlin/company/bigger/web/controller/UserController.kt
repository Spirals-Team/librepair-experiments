package company.bigger.web.controller

import company.bigger.dto.UserLoginModel
import company.bigger.dto.UserLoginModelResponse
import company.bigger.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @Autowired
    private lateinit var loginService: LoginService

    @GetMapping()
    @RequestMapping(value = ["/user/{username}/login/{password}"])
    fun login(@PathVariable username: String, @PathVariable password: String): UserLoginModelResponse {
        return loginService.login(UserLoginModel(username, password))
    }
}