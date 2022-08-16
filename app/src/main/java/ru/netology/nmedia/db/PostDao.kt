package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.Post

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateById(id: Int, content:String)

    fun save(post: PostEntity) =
        if(post.id == 0) insert(post) else updateById(post.id, post.content)

    @Query(
        """
            UPDATE posts SET
            likes = likes + CASE WHEN liked_By_Me THEN -1 ELSE 1 END,
            liked_By_Me = CASE WHEN liked_By_Me THEN 0 ELSE 1 END
            WHERE id = :id
            """
    )
    fun likeById(id: Int)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Int)

    @Query(
        """
            UPDATE posts SET
            countShare = countShare + 1 WHERE id = :id
            """)
    fun share(id:Int)
}