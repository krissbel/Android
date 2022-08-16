import android.database.Cursor
import ru.netology.nmedia.Post
import ru.netology.nmedia.db.PostEntity


internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    published = published,
    content = content,
    likes = likes,
    likedByMe = likedByMe,
    video = video,
    countShare = countShare,
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    published = published,
    content = content,
    likes = likes,
    likedByMe = likedByMe,
    video = video,
    countShare = countShare,
)