package kr.nutee.nutee_android.data.main.home

data class Comment(
    /*val ParentId: Int,*/
    val PostId: Int,
    /*val ReComment: List<Any>,*/
    val User: CommentUser,
    val UserId: Int,
    val commentLikers: List<CommentLiker>,
    val content: String,
    val createdAt: String,
    val id: Int,
    val isDeleted: Boolean,
    val updatedAt: String
)