package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.PostEntity
import toEntity
import toModel

class PostRepositoryImpl(private val dao: PostDao) : PostRepository {


    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun like(postId: Int) = dao.likeById(postId)

    override fun share(postId: Int) = dao.share(postId)

    override fun delete(postId: Int) = dao.removeById(postId)

    override fun save(post: Post) {
        dao.save(post.toEntity())
    }

}
