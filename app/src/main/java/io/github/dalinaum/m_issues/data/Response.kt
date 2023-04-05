package io.github.dalinaum.m_issues.data

data class Response(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<Item>
)