package ru.stankin.graduation.controller.user.v1

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.FileDto

@RequestMapping("/user/v1/file")
interface FileApi {

    @PostMapping("/create")
    fun saveFile(@RequestPart file: MultipartFile): CommonResponse<FileDto>

    @PostMapping("/multiple-create")
    fun saveFiles(@RequestPart files: List<MultipartFile>): CommonResponse<List<FileDto>>

    @GetMapping("/{storageId}", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun downloadFile(@PathVariable storageId: String): ByteArray
}