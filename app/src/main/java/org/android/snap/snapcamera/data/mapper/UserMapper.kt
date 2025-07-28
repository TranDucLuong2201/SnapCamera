package org.android.snap.snapcamera.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseUser
import org.android.snap.core.domain.model.User
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
fun FirebaseUser.toUser(): User {
	val createdAt = metadata?.creationTimestamp?.let {
		LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
	} ?: LocalDateTime.now()
	return User(
		id = uid,
		displayName = displayName ?: "Unknown",
		photoUrl = photoUrl?.toString(),
		email = email ?: "Unknown",
		createdAt = createdAt,
		updatedAt = metadata?.lastSignInTimestamp?.let {
			LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
		} ?: createdAt,
	)
}
