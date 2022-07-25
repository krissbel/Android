package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.core.view.isVisible
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(viewModel)


        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.save.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveClicked(content)
                clearFocus()
                binding.cancelButton.visibility = View.INVISIBLE
                hideKeyboard()


            }
        }
        binding.cancelButton.setOnClickListener {
            viewModel.onCancelEditClicked()
            binding.cancelButton.visibility = View.INVISIBLE

        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {

                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    binding.cancelButton.visibility = View.VISIBLE
                }
            }
        }

    }
}










