package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.sharePostContent.observe(this) { postContent ->
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

        setFragmentResultListener(
            requestKey = PostContentFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent = bundle.getString(PostContentFragment.REQUEST_KEY)
                ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent)

        }

        viewModel.playVideo.observe(this) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))

            startActivity(intent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
            val direction = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(direction)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFeedBinding.inflate(layoutInflater,container,false).also { binding ->


        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root

    }



