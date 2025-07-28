package org.android.snap.snapcamera.domain.usecase

import org.android.snap.snapcamera.domain.usecase.preference.DeletePreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.GetBooleanPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.GetIntPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.GetPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.SetBooleanPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.SetIntPreferenceUseCase
import org.android.snap.snapcamera.domain.usecase.preference.SetPreferenceUseCase

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

data class UserPreferenceUseCase(
	val setPreferenceUseCase: SetPreferenceUseCase,
	val getPreferenceUseCase: GetPreferenceUseCase,
	val deletePreferenceUseCase: DeletePreferenceUseCase,
	val setBooleanPreferenceUseCase: SetBooleanPreferenceUseCase,
	val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
	val setIntPreferenceUseCase: SetIntPreferenceUseCase,
	val getIntPreferenceUseCase: GetIntPreferenceUseCase,
)
