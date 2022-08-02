package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.view.GravityCompat.apply
import androidx.core.view.isVisible
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
import kotlin.contracts.contract


class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private val postContentActivityLauncher =
        registerForActivityResult(PostContentActivity.ResultContract) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveClicked(postContent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)


        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

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

        viewModel.editPost.observe(this) {
            val editText = viewModel.currentPost.value?.content
            postContentActivityLauncher.launch(editText)
        }



        viewModel.playVideo.observe(this) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))

            startActivity(intent)
        }




        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch(null)

        }

    }


}











