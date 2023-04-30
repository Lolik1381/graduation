package ru.stankin.graduation.service.impl

import java.util.UUID
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import ru.stankin.graduation.service.FileServiceComponent
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class S3FileServiceComponent(
    private val s3Client: S3Client
) : FileServiceComponent {

    override fun upload(bucket: String, file: ByteArray): String {
        val storageId = UUID.randomUUID().toString()

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(storageId)
                .contentLength(file.size.toLong())
                .contentType(MediaType.APPLICATION_OCTET_STREAM.toString())
                .metadata(HashMap())
                .build(),
            RequestBody.fromBytes(file)
        )

        return storageId
    }

    override fun download(bucket: String, storageId: String): ByteArray {
        return s3Client.getObject(
            GetObjectRequest.builder()
                .bucket(bucket)
                .key(storageId)
                .build(),
            ResponseTransformer.toBytes()
        ).asByteArray()
    }
}