package org.android.snap.snapcamera.data.dto

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class SubscriptionDto(
	val userId: String = "",
	val planType: String = "",
	val status: String = "",
	val startDate: Long = 0L,
	val endDate: Long? = null,
	val trialEndDate: Long? = null,
	val stripeSubscriptionId: String? = null,
	val stripeCustomerId: String? = null,
	val features: List<String> = emptyList(),
	val createdAt: Long = 0L,
	val updatedAt: Long = 0L,
)
