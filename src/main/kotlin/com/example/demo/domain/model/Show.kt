package com.example.demo.domain.model

data class Show(
    val title: String,
    val releaseYear: Int,
    val avgStars: Float? = null,
)