package ru.netology.nmedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1001

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }
    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(GENERATED_POSTS_AMOUNT) { index ->
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

    override fun delete(postId: Int) {
        posts = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if(post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }



    private fun update(post: Post) {
        data.value = posts.map {
            if(it.id == post.id) post else it
        }

    }

    private fun insert(post: Post) {

        data.value = listOf(post.copy(id = nextId++)) + posts

    }
    private companion object{
        const val GENERATED_POSTS_AMOUNT = 1000
    }

}