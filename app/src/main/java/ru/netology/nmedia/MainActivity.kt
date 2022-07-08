package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = PostViewModel()
        viewModel.data.observe(this) { post ->
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
        binding.like.setOnClickListener {
            viewModel.like()
        }

        binding.share.setOnClickListener {
            viewModel.share()
        }
    }

    private fun convertCount(count: Int): Int {
        return count / 1000
    }

    fun countInText(count: Int): String {
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

