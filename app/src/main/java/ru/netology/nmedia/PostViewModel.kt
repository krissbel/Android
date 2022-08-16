package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.PostRepository.Companion.NEW_POST_ID
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {
    private val repository: PostRepository = PostRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )
    val data get() = repository.data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val navigateToPostContentDetails = SingleLiveEvent<Int>()

    /** Значение = url содержит видео для воспроизведения */

    val playVideo = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)


    fun onSaveClicked(content: String) {

        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = NEW_POST_ID,
            author = "Kriss",
            content = content,
            published = "Today",
            video = "https://www.youtube.com/watch?v=b11RKHyOFCs"
        )
        repository.save(post)
        currentPost.value = null

    }

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post.content

    }

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content

    }

    override fun onPlayVideoClicked(post: Post) {
        val url = requireNotNull(post.video) {

        }
        playVideo.value = url
    }

    override fun openPost(post: Post) {
        currentPost.value = post
        navigateToPostContentDetails.value = post.id
    }

}