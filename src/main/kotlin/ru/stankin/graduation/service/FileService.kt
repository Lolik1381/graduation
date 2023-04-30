package ru.stankin.graduation.service

import org.springframework.web.multipart.MultipartFile
import ru.stankin.graduation.dto.FileDto

interface FileService {

    fun saveFile(file: MultipartFile): FileDto
    fun downloadFile(storageId: String): ByteArray
    fun saveFiles(files: List<MultipartFile>): List<FileDto>
}