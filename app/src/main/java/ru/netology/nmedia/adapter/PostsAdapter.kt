package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding

class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit
) :
    ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikeClicked, onShareClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        private val onLikeClicked: (Post) -> Unit,
        private val onShareClicked: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.like.setOnClickListener {
                onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                onShareClicked(post)
            }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                date.text = post.published
                textPost.text = post.content
                countLike.text = countInText(post.likes)
                countShare.text = countInText(post.countShare)
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_red_like_24dp else R.drawable.ic_like_24dp
                )

            }
        }

        private fun convertCount(count: Int): Int {
            return count / 1000
        }

        private fun countInText(count: Int): String {
            if (count < 999) return count.toString()

            if ((count < 10000) && (count > 999)) when {
                (count / 100) % 10 == 0 -> return convertCount(count).toString() + "K"
                (count / 100) % 10 in 1..9 -> return convertCount(count).toString() + "." + (count / 100) % 10 + "K"
            }
            if ((count > 9999) && (count < 99999)) when {
                (count / 100) % 10 in 0..9 -> return convertCount(count).toString() + "K"
            }
            if (count > 99999) when {
                (count / 100) % 100 in 0..9 -> return convertCount(count).toString() + "M"
            }
            return count.toString()
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}
