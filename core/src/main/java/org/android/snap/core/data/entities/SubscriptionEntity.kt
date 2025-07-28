package org.android.snap.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.android.snap.core.utils.DbConstants.Subscription.TABLE_NAME
import org.android.snap.core.utils.PlanType
import org.android.snap.core.utils.SubscriptionStatus
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Entity(tableName = TABLE_NAME)
data class SubscriptionEntity(
	@PrimaryKey val userId: String,
	// "free", "pro"
	val planType: PlanType,
	// "active", "expired", "cancelled", "trial"
	val status: SubscriptionStatus,
	val startDate: LocalDate,
	val endDate: LocalDate?,
	val trialEndDate: LocalDate?,
	val stripeSubscriptionId: String?,
	val stripeCustomerId: String?,
	// JSON của features được phép
	val features: String,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
