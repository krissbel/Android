package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.Post
import toPost

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    override fun getAll() =
        db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS_NAMES,
            null, null, null, null,
            "${PostTable.Column.ID.columnName} DESC"
        ).use { cursor ->
            List(cursor.count) {
                cursor.moveToNext()
                cursor.toPost()
            }

        }


    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostTable.Column.AUTHOR.columnName, post.author)
            put(PostTable.Column.CONTENT.columnName, post.content)
            put(PostTable.Column.PUBLISHED.columnName, post.published)
        }
        val id = if(post.id != 0){
            db.update(
                PostTable.NAME,
                values,
                "${PostTable.Column.ID.columnName} = ?",
            arrayOf(post.id.toString())
            )
            post.id
        }else{
            db.insert(PostTable.NAME, null, values)
        }
        return db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMNS_NAMES,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()),
            null, null, null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()

        }
    }

    override fun likeById(id: Int) {
        db.execSQL(
            """
                UPDATE posts SET
                likes = likes + CASE WHEN likeByMe THEN -1 ELSE 1 END,
                likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END
                WHERE id = ?;
                """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Int) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())

        )
    }

    override fun share(id: Int) {
        db.execSQL(
            """
                UPDATE posts SET
                countShare = countShare + 1 WHERE id =?;
                """.trimIndent(),
            arrayOf(id)
        )

    }
}