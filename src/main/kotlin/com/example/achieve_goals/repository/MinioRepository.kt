package com.example.achieve_goals.repository

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.errors.MinioException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository

@Repository
class MinioRepository(
    @Value("\${app.minioServerURL}") final val serverUrl: String,
    @Value("\${app.minioServerUser}") final val serverUser: String,
    @Value("\${app.minioServerPassword}") final val serverPassword: String,
    @Value("\${app.minioServerUserAvatarBucket}") val name: String
) {
    private val logger: Logger = LoggerFactory.getLogger(MinioRepository::class.java)
    private val client = try {
        MinioClient.builder()
            .endpoint(serverUrl)
            .credentials(serverUser, serverPassword)
            .build()
    } catch (e: MinioException) {
        logger.error("Error occurred: $e")
        logger.error("HTTP trace: " + e.httpTrace())
        throw e
    }

    fun configure(): MinioClient {
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(name).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(name).build())
        } else {
            logger.error("Bucket $name already exists.")
        }
        return client
    }
}