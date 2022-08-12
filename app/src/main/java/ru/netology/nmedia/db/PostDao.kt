package ru.netology.nmedia.db

import ru.netology.nmedia.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeByMe(id: Int)
    fun removeById(id: Int)
}