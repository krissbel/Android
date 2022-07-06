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

        val post = Post(
            id = 0,
            author = "Kriss",
            published = "23.06.2022",
            content = "events",
        )

        binding.render(post)
        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            val imageResId =
                if (post.likedByMe) R.drawable.ic_red_like_24dp else R.drawable.ic_like_24dp
            binding.like.setImageResource(imageResId)
            var newCount = post.likes + 1
            if (post.likedByMe) newCount else newCount -= 1
            binding.countLike.text = countInTextFormat(newCount)
        }

        binding.share.setOnClickListener {
            var countShare = post.countShare++
            binding.countShare.text = countInTextFormat(countShare)

        }
    }

}

private fun ActivityMainBinding.render(post: Post) {
    authorName.text = post.author
    date.text = post.published
    textPost.text = post.content
    countShare.text = countInTextFormat(post.countShare)
    countLike.text = countInTextFormat(post.likes)

}

@DrawableRes
private fun getLikeIconResId(liked: Boolean) =
    if (liked) R.drawable.ic_red_like_24dp else R.drawable.ic_like_24dp

fun convertCount(countShare: Int): Int {
    return countShare / 1000
}

fun countInTextFormat(count: Int): String {

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
