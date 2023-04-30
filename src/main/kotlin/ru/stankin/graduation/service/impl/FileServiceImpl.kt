package ru.stankin.graduation.service.impl

import org.apache.commons.io.FilenameUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import ru.stankin.graduation.dto.FileDto
import ru.stankin.graduation.entity.FileEntity
import ru.stankin.graduation.exception.ApplicationException
import ru.stankin.graduation.exception.BusinessErrorInfo
import ru.stankin.graduation.mapper.FileMapper
import ru.stankin.graduation.repository.FileRepository
import ru.stankin.graduation.service.FileService
import ru.stankin.graduation.service.FileServiceComponent

@Service
class FileServiceImpl(
    private val fileServiceComponent: FileServiceComponent,
    private val fileRepository: FileRepository,
    private val fileMapper: FileMapper
) : FileService {

    companion object {
        private const val BUCKET = "image"
    }

    @Transactional
    override fun saveFile(file: MultipartFile): FileDto {
        val storageId = fileServiceComponent.upload(BUCKET, file.bytes)

        val fileEntity = FileEntity(
            id = storageId,
            name = FilenameUtils.getName(file.name),
            extension = FilenameUtils.getExtension(file.name)
        )

        return fileMapper.toDto(fileRepository.save(fileEntity))
    }

    @Transactional
    override fun saveFiles(files: List<MultipartFile>): List<FileDto> {
        return files.map {
            val storageId = fileServiceComponent.upload(BUCKET, it.bytes)

            FileEntity(
                id = storageId,
                name = FilenameUtils.getName(it.name),
                extension = FilenameUtils.getExtension(it.name)
            )
        }.run { fileRepository.saveAll(this) }.map { fileMapper.toDto(it) }

    }

    override fun downloadFile(storageId: String): ByteArray {
        fileRepository.findByIdOrNull(storageId) ?: throw ApplicationException(BusinessErrorInfo.FILE_NOT_FOUND)

        return fileServiceComponent.download(BUCKET, storageId)
    }
}