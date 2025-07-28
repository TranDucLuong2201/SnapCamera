package org.android.snap.snapcamera.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import org.android.snap.core.data.dao.SubscriptionDao
import org.android.snap.core.data.mapper.toDomain
import org.android.snap.core.data.mapper.toEntity
import org.android.snap.core.domain.model.UserSubscription
import org.android.snap.core.domain.repository.SubscriptionRepository
import org.android.snap.core.utils.PremiumFeature
import org.android.snap.core.utils.SubscriptionStatus
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@RequiresApi(Build.VERSION_CODES.O)
class SubscriptionRepositoryImpl @Inject constructor(
	private val subscriptionDao: SubscriptionDao,
) : SubscriptionRepository {

	override suspend fun getUserSubscription(userId: String): Result<UserSubscription?> =
		try {
			val subscription = subscriptionDao.getSubscription(userId)
			Result.success(subscription?.toDomain())
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun createSubscription(subscription: UserSubscription): Result<Unit> =
		try {
			val subscriptionEntity = subscription.toEntity()
			subscriptionDao.insertSubscription(subscriptionEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun updateSubscription(subscription: UserSubscription): Result<Unit> =
		try {
			val subscriptionEntity = subscription.toEntity()
			subscriptionDao.updateSubscription(subscriptionEntity)
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun cancelSubscription(userId: String): Result<Unit> =
		try {
			subscriptionDao.updateSubscriptionStatus(userId, SubscriptionStatus.CANCELLED.name, LocalDateTime.now())
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun hasFeature(
		userId: String,
		feature: PremiumFeature,
	): Result<Boolean> =
		try {
			val subscription = subscriptionDao.getSubscription(userId)
			val hasFeature = subscription?.features?.contains(feature.name) ?: false
			Result.success(hasFeature)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun isProUser(userId: String): Result<Boolean> =
		try {
			val subscription = subscriptionDao.getSubscription(userId)
			val isPro = subscription?.status == SubscriptionStatus.ACTIVE
			Result.success(isPro)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}

	override suspend fun syncSubscription(userId: String): Result<Unit> =
		try {
			// TODO: Implement sync logic with Firebase/Stripe
			Result.success(Unit)
		} catch (e: Exception) {
			e.printStackTrace()
			Result.failure(e)
		}
}
