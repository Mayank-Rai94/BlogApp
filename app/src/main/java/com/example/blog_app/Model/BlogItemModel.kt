package com.example.blog_app.Model

data class BlogItemModel(
    val heading : String = "",
    val userName : String = "",
    val date : String = "",
    val post : String = "",
    val likeCount : Int = 0
)