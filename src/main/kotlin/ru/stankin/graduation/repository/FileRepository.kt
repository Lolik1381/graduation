package ru.stankin.graduation.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.stankin.graduation.entity.FileEntity

interface FileRepository : JpaRepository<FileEntity, String> {
}