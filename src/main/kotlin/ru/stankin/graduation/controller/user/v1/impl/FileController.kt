package ru.stankin.graduation.controller.user.v1.impl

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.stankin.graduation.controller.user.v1.FileApi
import ru.stankin.graduation.dto.CommonResponse
import ru.stankin.graduation.dto.FileDto
import ru.stankin.graduation.service.FileService

@RestController
class FileController(
    private val fileService: FileService
) : FileApi {

    override fun saveFile(file: MultipartFile): CommonResponse<FileDto> {
        return CommonResponse.ok(fileService.saveFile(file))
    }

    override fun saveFiles(files: List<MultipartFile>): CommonResponse<List<FileDto>> {
        return CommonResponse.ok(fileService.saveFiles(files))
    }

    override fun downloadFile(storageId: String): ByteArray {
        return fileService.downloadFile(storageId)
    }
}