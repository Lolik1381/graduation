package ru.stankin.graduation.config.properties

import java.net.URI
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import software.amazon.awssdk.regions.Region

@ConstructorBinding
@ConfigurationProperties(prefix = "aws.s3")
data class S3ClientConfigProperties(
    val region: Region = Region.EU_WEST_1,
    val endpoint: URI,
    val accessKeyId: String,
    val secretAccessKey: String
)
