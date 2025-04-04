package com.example.blog_app.Model

data class UserData(
    val name : String,
    val email : String
){
    constructor() : this ("","")
}
