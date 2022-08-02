package ru.netology.nmedia

import androidx.lifecycle.LiveData

interface PostRepository {
    val data: LiveData<List<Post>>
    fun like(postId: Int)
    fun share(postId: Int)
    fun delete(postId: Int)
    fun save(post: Post)
    fun cancelEdit(post:Post)

    companion object {
        const val NEW_POST_ID = 0
    }
}