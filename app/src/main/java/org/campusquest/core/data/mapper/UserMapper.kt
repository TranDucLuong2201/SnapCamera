package org.campusquest.core.data.mapper

import com.google.firebase.auth.FirebaseUser
import org.campusquest.core.domain.model.User

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        displayName = displayName ?: "Unknown",
        photoUrl = photoUrl?.toString(),
        email = email ?: "Unknown",
        joinDate = metadata?.creationTimestamp?.let {
            java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(java.util.Date(it))
        } ?: "Unknown"
    )
}
