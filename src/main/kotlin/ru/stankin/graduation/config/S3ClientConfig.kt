package ru.stankin.graduation.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.stankin.graduation.config.properties.S3ClientConfigProperties
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration

@Configuration
@EnableConfigurationProperties(S3ClientConfigProperties::class)
class S3ClientConfig {

    @Bean
    fun s3client(properties: S3ClientConfigProperties, credentialsProvider: AwsCredentialsProvider): S3Client {
        val serviceConfiguration = S3Configuration.builder()
            .checksumValidationEnabled(false)
            .chunkedEncodingEnabled(true)
            .build()

        return S3Client.builder()
            .region(properties.region)
            .credentialsProvider(credentialsProvider)
            .serviceConfiguration(serviceConfiguration)
            .endpointOverride(properties.endpoint)
            .build()
    }

    @Bean
    fun awsCredentialsProvider(s3props: S3ClientConfigProperties): AwsCredentialsProvider {
        return AwsCredentialsProvider {
            AwsBasicCredentials.create(s3props.accessKeyId, s3props.secretAccessKey)
        }
    }
}
