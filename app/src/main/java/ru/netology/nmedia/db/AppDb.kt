package ru.netology.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.db.PostTable.DDL

class AppDb private constructor(db:SQLiteDatabase){
    val postDao: PostDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb{
            return instance?: synchronized(this){
                instance?: AppDb(
                    buildDataBase(context, arrayOf(PostTable.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs
        ).writableDatabase
    }

}