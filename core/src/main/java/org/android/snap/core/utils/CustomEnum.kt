package org.android.snap.core.utils

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

enum class NoteType {
	TEXT,
	HANDWRITTEN,
	ANNOTATION,
}

enum class ExportType {
	SESSION,
	SUBJECT,
	DATE_RANGE,
	CUSTOM,
}

enum class PlanType {
	FREE,
	PRO,
}

enum class SubscriptionStatus {
	ACTIVE,
	EXPIRED,
	CANCELLED,
	TRIAL,
}

enum class PremiumFeature {
	DIRECT_IMAGE_ANNOTATION,
	LIVE_NOTE_TAKING,
	ADVANCED_PDF_EXPORT,
	AUTO_DRIVE_SYNC,
	NO_ADS,
	UNLIMITED_STORAGE,
}
