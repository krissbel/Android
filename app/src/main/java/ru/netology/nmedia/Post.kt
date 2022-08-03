package ru.netology.nmedia
import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Int,
    val author: String,
    val content: String,
    val published:String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var countShare: Int = 0,
    val video: String? = "https://www.youtube.com/watch?v=FOiRXAujHBE"
        )
