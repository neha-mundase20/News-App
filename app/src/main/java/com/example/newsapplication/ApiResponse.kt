package com.example.newsapplication

data class ApiResponse(
    val articles: List<Article>
)

data class Article(
    val source: Details,
    val title: String,
    val description: String,
    val urlToImage: String
)

data class Details(
    val id: String,
    val name: String
)