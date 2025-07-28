package org.android.snap.core.domain.repository

import org.android.snap.core.domain.model.UserSubscription
import org.android.snap.core.utils.PremiumFeature

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

interface SubscriptionRepository {
	suspend fun getUserSubscription(userId: String): Result<UserSubscription?>
	suspend fun createSubscription(subscription: UserSubscription): Result<Unit>
	suspend fun updateSubscription(subscription: UserSubscription): Result<Unit>
	suspend fun cancelSubscription(userId: String): Result<Unit>
	suspend fun hasFeature(userId: String, feature: PremiumFeature): Result<Boolean>
	suspend fun isProUser(userId: String): Result<Boolean>
	suspend fun syncSubscription(userId: String): Result<Unit> // Trừu tượng
}
