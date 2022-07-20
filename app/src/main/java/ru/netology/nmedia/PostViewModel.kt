package ru.netology.nmedia

import android.view.View
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.PostRepository.Companion.NEW_POST_ID
import ru.netology.nmedia.adapter.PostInteractionListener

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data get() = repository.data

    val currentPost = MutableLiveData<Post?>(null)


    fun onSaveClicked(content: String) {

        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(id = NEW_POST_ID, author = "Kriss", content = content, published = "Today")
        repository.save(post)
        currentPost.value = null

    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) = repository.share(post.id)

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)
    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    override fun onCancelEditClicked() {
        currentPost.value = null



    }
}