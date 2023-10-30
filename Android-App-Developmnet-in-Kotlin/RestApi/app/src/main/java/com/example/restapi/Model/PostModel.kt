package com.example.restapi.Model

data class PostModel (
    var id : Int,
    var slug : String,
    var url : String,
    var title : String,
    var content : String,
    var image : String,
    var thumbnail : String,
    var status : String,
    var category : String,
    var publishedAt : String,
    var updatedAt : String,
    var userId : Int
)


