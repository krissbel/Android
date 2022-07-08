package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 0,
        author = "Kriss",
        published = "23.06.2022",
        content = "events",
        likedByMe = false
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        var newCount = post.likes
        post.likes = if (!post.likedByMe) newCount++ else newCount--
        post = post.copy(likedByMe = !post.likedByMe, likes = post.likes)
        data.value = post

    }

    override fun share() {
        var countShare = post.countShare
        post = post.copy(countShare = countShare + 1)
        data.value = post

    }

}