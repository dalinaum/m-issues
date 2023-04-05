package io.github.dalinaum.m_issues.data

import java.util.*

data class Item(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val htmlUrl: String,
    val description: String?,
    val createdAt: Date,
    val updatedAt: Date,
    val homepage: String?,
)
