package org.android.snap.core.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.android.snap.core.data.entities.SubscriptionEntity
import org.android.snap.core.domain.model.UserSubscription
import org.android.snap.core.utils.PremiumFeature

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

// UserSubscription Mappers
@RequiresApi(Build.VERSION_CODES.O)
fun SubscriptionEntity.toDomain(): UserSubscription {
	val featuresList = try {
		val featuresJson = features
		val stringList: List<String> =
			Gson().fromJson(featuresJson, object : TypeToken<List<String>>() {}.type) ?: emptyList()
		stringList.map { PremiumFeature.valueOf(it.uppercase()) }
	} catch (e: Exception) {
		e.printStackTrace()
		emptyList()
	}

	return UserSubscription(
		userId = userId,
		planType = planType,
		status = status,
		startDate = startDate,
		endDate = endDate,
		trialEndDate = trialEndDate,
		stripeSubscriptionId = stripeSubscriptionId,
		stripeCustomerId = stripeCustomerId,
		features = featuresList,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}

@RequiresApi(Build.VERSION_CODES.O)
fun UserSubscription.toEntity(): SubscriptionEntity {
	val featuresJson = Gson().toJson(features.map { it.name.lowercase() })

	return SubscriptionEntity(
		userId = userId,
		planType = planType,
		status = status,
		startDate = startDate,
		endDate = endDate,
		trialEndDate = trialEndDate,
		stripeSubscriptionId = stripeSubscriptionId,
		stripeCustomerId = stripeCustomerId,
		features = featuresJson,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
}
