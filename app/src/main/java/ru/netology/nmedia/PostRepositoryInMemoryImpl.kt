package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }
    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(1000) { index ->
            Post(
                id = index + 1,
                author = "Автор",
                published = "23.06.2022",
                content = "Контент поста № ${index + 1}",
                likedByMe = false

            )
        }
        data = MutableLiveData(initialPosts)
    }

    override fun like(postId: Int) {
        posts = posts.map { post ->
            var newCount = post.likes
            post.likes = if (!post.likedByMe) newCount++ else newCount--
            if (post.id != postId) post else post.copy(
                likedByMe = !post.likedByMe,
                likes = newCount
            )
        }

    }

    override fun share(postId: Int) {
        posts = posts.map { post ->
            if (post.id != postId) post else post.copy(countShare = post.countShare + 1)
        }

    }

}