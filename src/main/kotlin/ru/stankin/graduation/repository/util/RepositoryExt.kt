package ru.stankin.graduation.repository.util

import org.springframework.data.jpa.domain.Specification

fun String?.getLikeText(): String {
    return "%${this?.lowercase().orEmpty()}%"
}

fun <T> combineSpecification(vararg specifications: Specification<T>?): Specification<T> {
    return specifications.filterNotNull().reduce { acc, specification -> acc.and(specification) }
}