package ru.netology.nmedia.db

object PostTable {

    const val NAME = "POSTS"

    val DDL = """
        CREATE TABLE $NAME(
            ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.AUTHOR.columnName} TEXT NOT NULL,
            ${Column.CONTENT.columnName} TEXT NOT NULL,
            ${Column.PUBLISHED.columnName} TEXT NOT NULL,
            ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.LIKE_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT false,
            ${Column.COUNT_SHARE.columnName} INTEGER NOT NULL DEFAULT 0,
            ${Column.VIDEO.columnName} TEXT DEFAULT NULL
        );
        """.trimIndent()

    val ALL_COLUMNS_NAMES = Column.values().map{it.columnName}.toTypedArray()


    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKES("likes"),
        LIKE_BY_ME("likeByMe"),
        COUNT_SHARE("countShare"),
        VIDEO("video")
    }

}