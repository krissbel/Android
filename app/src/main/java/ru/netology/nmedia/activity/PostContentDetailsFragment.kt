package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentPostContentDetailsBinding

class PostContentDetailsFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPostContentDetailsBinding.inflate(layoutInflater, container, false)
        .also { binding ->

            val post = viewModel.currentPost.value

            val popupMenu by lazy {
                PopupMenu(layoutInflater.context, binding.options).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                if (post != null) {
                                    viewModel.onRemoveClicked(post)
                                }
                                true
                            }
                            R.id.edit -> {
                                if (post != null) {
                                    viewModel.onEditClicked(post)
                                }
                                true
                            }
                            else -> false
                        }

                    }
                }
            }
            binding.options.setOnClickListener { popupMenu.show() }

            viewModel.data.observe(viewLifecycleOwner) { posts ->
                val changedPosts = posts.filter { it.id == post?.id }
                if (changedPosts.isNotEmpty()) {

                    with(binding) {
                        authorName.text = post?.author
                        date.text = post?.published
                        textPost.text = post?.content
                        like.text = countInText(post?.likes)
                        like.isChecked = post?.likedByMe == true
                        share.text = countInText(post?.countShare)
                        options.setOnClickListener { popupMenu.show() }
                        videoGroup.isVisible = post?.video != null
                    }
                } else {
                    findNavController().navigateUp()
                }
            }

            setFragmentResultListener(
                requestKey = PostContentFragment.REQUEST_KEY
            ) { requestKey, bundle ->
                if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
                val newPostContent = bundle.getString(PostContentFragment.REQUEST_KEY)
                    ?: return@setFragmentResultListener
                viewModel.onSaveClicked(newPostContent)
            }

            viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.ACTION_SEND, postContent)
                    type = "text/plain"

                }
                val shareIntent = Intent.createChooser(
                    intent, getString(R.string.chooser_share_post)
                )
                startActivity(shareIntent)

            }

            viewModel.playVideo.observe(viewLifecycleOwner) { videoUrl ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))

                startActivity(intent)
            }

            post?.let {
                binding.like.setOnClickListener {
                    viewModel.onLikeClicked(post)
                }

                binding.share.setOnClickListener {
                    viewModel.onShareClicked(post)
                }
                binding.video.setOnClickListener {
                    viewModel.onPlayVideoClicked(post)
                }
            }



            viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
                val direction =
                    PostContentDetailsFragmentDirections.actionPostContentDetailsFragmentToPostContentFragment(
                        initialContent
                    )
                findNavController().navigate(direction)
            }
        }.root

    private fun convertCount(count: Int?): Int? {
        if (count != null) {
            return count / 1000
        }
        return count
    }



    private fun countInText(count: Int?): String {
        if (count != null) {
            if (count < 999) return count.toString()
        }

        if (count != null) {
            if ((count < 10000) && (count > 999)) when {
                (count / 100) % 10 == 0 -> return convertCount(count).toString() + "K"
                (count / 100) % 10 in 1..9 -> return convertCount(count).toString() + "." + (count / 100) % 10 + "K"
            }
        }
        if (count != null) {
            if ((count > 9999) && (count < 99999)) when {
                (count / 100) % 10 in 0..9 -> return convertCount(count).toString() + "K"
            }
        }
        if (count != null) {
            if (count > 99999) when {
                (count / 100) % 100 in 0..9 -> return convertCount(count).toString() + "M"
            }
        }
        return count.toString()
    }
}

















