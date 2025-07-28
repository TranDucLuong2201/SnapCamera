package org.android.snap.core.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.android.snap.core.utils.ExportType
import org.android.snap.core.utils.NoteType
import org.android.snap.core.utils.PlanType
import org.android.snap.core.utils.PremiumFeature
import org.android.snap.core.utils.SubscriptionStatus

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class EnumConverters {

	@TypeConverter
	fun fromNoteType(value: NoteType?): String? = value?.name

	@TypeConverter
	fun toNoteType(value: String?): NoteType? = value?.let { enumValueOf<NoteType>(it) }

	@TypeConverter
	fun fromPlanType(value: PlanType?): String? = value?.name

	@TypeConverter
	fun toPlanType(value: String?): PlanType? = value?.let { enumValueOf<PlanType>(it) }

	@TypeConverter
	fun fromSubscriptionStatus(value: SubscriptionStatus?): String? = value?.name

	@TypeConverter
	fun toSubscriptionStatus(value: String?): SubscriptionStatus? =
		value?.let { enumValueOf<SubscriptionStatus>(it) }

	@TypeConverter
	fun fromExportType(value: ExportType?): String? = value?.name

	@TypeConverter
	fun toExportType(value: String?): ExportType? = value?.let { enumValueOf<ExportType>(it) }

	@TypeConverter
	fun fromPremiumFeatureList(value: List<PremiumFeature>?): String? {
		return Gson().toJson(value)
	}

	@TypeConverter
	fun toPremiumFeatureList(value: String?): List<PremiumFeature>? {
		return value?.let {
			try {
				Gson().fromJson(it, object : TypeToken<List<PremiumFeature>>() {}.type)
			} catch (e: Exception) {
				emptyList()
			}
		}
	}
}
