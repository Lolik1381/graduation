package ru.stankin.graduation.service

interface FileServiceComponent {

    fun upload(bucket: String, file: ByteArray): String
    fun download(bucket: String, storageId: String): ByteArray
}