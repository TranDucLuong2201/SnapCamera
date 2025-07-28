package org.android.snap.snapcamera.domain.usecase.preference

import org.android.snap.snapcamera.data.repository.impl.UserPreferenceRepository

/**
 * Created by Duclu
 * Email: ducluongtran597@gmail.com
 */

class SetPreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String, value: String) = repository.setPreference(key, value)
}

class GetPreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String) = repository.getPreference(key)
}

class DeletePreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String) = repository.deletePreference(key)
}

class SetBooleanPreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String, defaultValue: Boolean) =
		repository.setBooleanPreference(key, defaultValue)
}

class GetBooleanPreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String, defaultValue: Boolean) =
		repository.getBooleanPreference(key, defaultValue)
}

class SetIntPreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String, value: Int) =
		repository.setIntPreference(key, value)
}

class GetIntPreferenceUseCase(
	private val repository: UserPreferenceRepository,
) {
	suspend operator fun invoke(key: String, value: Int) =
		repository.getIntPreference(key, value)
}
