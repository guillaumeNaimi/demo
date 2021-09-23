package com.example.demo.infra.adapter.secondary.persistence.document

import com.example.demo.domain.model.Show

data class ShowDocument(
    val title: String,
    val releaseYear: Int
) {
    fun toShow() =
        Show(
            title,
            releaseYear
        )
}
