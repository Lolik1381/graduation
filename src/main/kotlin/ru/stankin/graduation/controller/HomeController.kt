package ru.stankin.graduation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
@Controller
class HomeController {

    @GetMapping("/")
    fun home(): String {
        return "redirect:swagger-ui/"
    }
}