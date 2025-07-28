package org.android.snap.snapcamera.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.domain.model.UserSubscription
import org.android.snap.core.utils.PremiumFeature
import org.android.snap.snapcamera.data.repository.SubscriptionRepositoryImpl
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SubscriptionUseCase @Inject constructor(
	private val subscriptionRepository: SubscriptionRepositoryImpl,
) {

	suspend fun getUserSubscription(userId: String): Result<UserSubscription?> =
		subscriptionRepository.getUserSubscription(userId)

	suspend fun createSubscription(subscription: UserSubscription): Result<Unit> =
		subscriptionRepository.createSubscription(subscription)

	suspend fun updateSubscription(subscription: UserSubscription): Result<Unit> =
		subscriptionRepository.updateSubscription(subscription)

	suspend fun cancelSubscription(userId: String): Result<Unit> =
		subscriptionRepository.cancelSubscription(userId)

	suspend fun hasFeature(
		userId: String,
		feature: PremiumFeature,
	): Result<Boolean> =
		subscriptionRepository.hasFeature(userId, feature)

	suspend fun isProUser(userId: String): Result<Boolean> =
		subscriptionRepository.isProUser(userId)

	suspend fun syncSubscription(userId: String): Result<Unit> =
		subscriptionRepository.syncSubscription(userId)
}
