package org.android.snap.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import org.android.snap.core.data.entities.SubscriptionEntity
import java.time.LocalDateTime

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

@Dao
interface SubscriptionDao {
	@Query("SELECT * FROM subscription_info WHERE userId = :userId")
	suspend fun getSubscription(userId: String): SubscriptionEntity?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertSubscription(subscription: SubscriptionEntity)

	@Update
	suspend fun updateSubscription(subscription: SubscriptionEntity)

	@Query("UPDATE subscription_info SET status = :status, endDate = :endDate WHERE userId = :userId")
	suspend fun updateSubscriptionStatus(userId: String, status: String, endDate: LocalDateTime?)

	@Delete
	suspend fun deleteSubscription(subscription: SubscriptionEntity)
}
