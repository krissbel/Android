import android.database.Cursor
import ru.netology.nmedia.Post
import ru.netology.nmedia.db.PostTable

fun Cursor.toPost() = Post(
    id = getInt(getColumnIndexOrThrow(PostTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostTable.Column.AUTHOR.columnName)),
    published = getString(getColumnIndexOrThrow(PostTable.Column.PUBLISHED.columnName)),
    content = getString(getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)),
    likes = getInt(getColumnIndexOrThrow(PostTable.Column.LIKES.columnName)),
    likedByMe = getInt(getColumnIndexOrThrow(PostTable.Column.LIKE_BY_ME.columnName)) != 0,
    video = getString(getColumnIndexOrThrow(PostTable.Column.VIDEO.columnName)),
    countShare = getInt(getColumnIndexOrThrow(PostTable.Column.COUNT_SHARE.columnName)),
)