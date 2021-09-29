package com.example.achieve_goals.service

import com.example.achieve_goals.repository.MinioRepository
import io.minio.GetPresignedObjectUrlArgs
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import io.minio.http.Method
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.concurrent.TimeUnit


@Service
class MinioService(
    minioRepo: MinioRepository,
    @Value("\${app.minioServerUserAvatarBucket}") val bucketName: String
) {
    private val minioClient = minioRepo.configure()

    fun uploadPhoto(uid: Long, file: InputStream, fileExtension: String) {
        val updFile = "$uid.$fileExtension"
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(bucketName).stream(file, -1, PutObjectArgs.MIN_MULTIPART_SIZE.toLong())
                .`object`(updFile)
                .build()
        )
    }

    fun getPhoto(uid: Long, fileExtension: String): String {
        val file = "$uid.$fileExtension"
        return getPhoto(file)
    }

    fun getPhoto(filename: String): String {
        try {
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .`object`(filename)
                    .method(Method.GET)
                    .expiry(5, TimeUnit.SECONDS)
                    .build()
            )
        } catch (e: Exception) {
            throw e
        }
    }

    fun deletePhoto(uid: Long, fileExtension: String) {
        minioClient.removeObject(
            RemoveObjectArgs.builder().bucket(bucketName).`object`("$uid.$fileExtension").build()
        )
    }
}