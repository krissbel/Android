package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding

class PostsAdapter(
    private val interactionListener: PostInteractionListener
) :
    ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        listener: PostInteractionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId){
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }

                }
            }
        }

        init {
            binding.like.setOnClickListener {
               listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }

            binding.video.setOnClickListener{
                listener.onPlayVideoClicked(post)
            }

        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                authorName.text = post.author
                date.text = post.published
                textPost.text = post.content
                like.text = countInText(post.likes)
                like.isChecked = post.likedByMe
                share.text = countInText(post.countShare)
                options.setOnClickListener{popupMenu.show()}
                videoGroup.isVisible = post.video != null

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
