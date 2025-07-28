package org.android.snap.core.domain.model

import org.android.snap.core.utils.PlanType
import org.android.snap.core.utils.PremiumFeature
import org.android.snap.core.utils.SubscriptionStatus
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class UserSubscription(
	val userId: String,
	val planType: PlanType,
	val status: SubscriptionStatus,
	val startDate: LocalDate,
	val endDate: LocalDate?,
	val trialEndDate: LocalDate?,
	val stripeSubscriptionId: String?,
	val stripeCustomerId: String?,
	val features: List<PremiumFeature>,
	val createdAt: LocalDateTime,
	val updatedAt: LocalDateTime,
)
