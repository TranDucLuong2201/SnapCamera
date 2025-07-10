package org.campusquest.core.domain.model

data class User(
    val id: String,
    val displayName: String,
    val photoUrl: String?,
    val email: String,
    val joinDate: String
)
