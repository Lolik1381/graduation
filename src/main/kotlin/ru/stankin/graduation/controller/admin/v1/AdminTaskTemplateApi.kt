package ru.stankin.graduation.controller.admin.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.TaskTemplateDto

@RequestMapping("/admin/v1/task/template")
interface AdminTaskTemplateApi {

    @PostMapping("/create")
    fun createTaskTemplate(@RequestBody taskTemplateDto: TaskTemplateDto): CommonResponse<TaskTemplateDto>

    @PutMapping("/update/{id}")
    fun updateTaskTemplate(@PathVariable id: String, @RequestBody taskTemplateDto: TaskTemplateDto): CommonResponse<TaskTemplateDto>

    @PutMapping("/active/{id}")
    fun activeTaskTemplate(@PathVariable id: String): CommonResponse<TaskTemplateDto>

    @PutMapping("/delete/{id}")
    fun deleteTaskTemplate(@PathVariable id: String): CommonResponse<TaskTemplateDto>

    @GetMapping("/findAll")
    fun findAll(@RequestParam(required = false) searchText: String?, pageable: Pageable): CommonResponse<Page<TaskTemplateDto>>

    @GetMapping("/find/{id}")
    fun findById(@PathVariable id: String): CommonResponse<TaskTemplateDto>
}