package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
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

        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            val imageResId =
                if (post.likedByMe) R.drawable.ic_red_like_24dp else R.drawable.ic_like_24dp
            binding.like.setImageResource(imageResId)

            val countLikes =
                if (imageResId == R.drawable.ic_red_like_24dp) post.likes + 1 else post.likes
            binding.countLike.text = countLikes.toString()
        }

        binding.share.setOnClickListener {
            var countShare = post.countShare++
            countShare = 100000
            binding.countShare.text = agoToText(countShare).toString()

        }
    }

}

private fun ActivityMainBinding.render(post: Post) {
    authorName.text = post.author
    date.text = post.published
    textPost.text = post.content

}

@DrawableRes
private fun getLikeIconResId(liked: Boolean) =
    if (liked) R.drawable.ic_red_like_24dp else R.drawable.ic_like_24dp

fun convertCount(countShare: Int): Int {
    return countShare / 1000
}

fun agoToText(countShare: Int): String {

    if (countShare < 999) return countShare.toString()

    if ((countShare < 10000) && (countShare > 999))  when {
        (countShare / 100) % 10 == 0 ->  return convertCount(countShare).toString() + "K"
        (countShare / 100) % 10 in 1..9 -> return convertCount(countShare).toString() + "." + (countShare / 100) % 10 + "K"
    }
    if ((countShare > 9999) && (countShare < 99999)) when {
        (countShare / 100) % 10 in 0..9 -> return convertCount(countShare).toString() + "K"
    }
    if (countShare > 99999) when {
        (countShare / 100) % 100 in 0..9 ->  return convertCount(countShare).toString() + "M"
    }
return countShare.toString()
}


