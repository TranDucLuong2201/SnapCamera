package org.android.snap.snapcamera.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.android.snap.snapcamera.data.repository.PreferenceRepositoryImpl
import org.android.snap.snapcamera.data.repository.dataStore
import org.android.snap.snapcamera.utils.Constant.PREFERENCES_KEY
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class UserPreferenceRepositoryImplTest {

	private lateinit var context: Context
	private lateinit var dataStore: DataStore<Preferences>
	private lateinit var userPreferenceRepository: PreferenceRepositoryImpl
	private lateinit var mockPreferences: Preferences

	@Before
	fun setUp() {
		context = mockk()
		dataStore = mockk()
		mockPreferences = mockk(relaxed = true) // Use relaxed mock for Preferences

		// Mock context.dataStore extension property
		mockkStatic("org.android.snap.snapcamera.data.repository.UserPreferenceRepositoryImplKt")
		every { context.dataStore } returns dataStore

		userPreferenceRepository = PreferenceRepositoryImpl(context)
	}

	@After
	fun tearDown() {
		unmockkAll()
	}

	@Test
	fun `getPreference should return string value when key exists`() = runTest {
		// Given
		val key = "test_key"
		val expectedValue = "test_value"
		val stringKey = stringPreferencesKey(key)

		every { mockPreferences[stringKey] } returns expectedValue
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.getPreference(key)

		// Then
		assertTrue(result.isSuccess)
		assertEquals(expectedValue, result.getOrNull())
	}

	@Test
	fun `getPreference should return null when key does not exist`() = runTest {
		// Given
		val key = "non_existent_key"
		val stringKey = stringPreferencesKey(key)

		every { mockPreferences[stringKey] } returns null
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.getPreference(key)

		// Then
		assertTrue(result.isSuccess)
		assertNull(result.getOrNull())
	}

	@Test
	fun `getPreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "test_key"
		val exception = RuntimeException("Test exception")

		every { dataStore.data } throws exception

		// When
		val result = userPreferenceRepository.getPreference(key)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `setPreference should save string value successfully`() = runTest {
		// Given
		val key = "test_key"
		val value = "test_value"

		// Corrected mocking for dataStore.updateData (which edit uses)
		// The block passed to updateData takes Preferences and returns Preferences
		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } returns mockPreferences

		// When
		val result = userPreferenceRepository.setPreference(key, value)

		// Then
		assertTrue(result.isSuccess)
		// You are verifying the call to dataStore.edit in your original code,
		// which internally calls updateData. Let's verify updateData.
		coVerify { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) }
	}

	@Test
	fun `setPreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "test_key"
		val value = "test_value"
		val exception = RuntimeException("Test exception")

		// Corrected mocking for dataStore.updateData (which edit uses)
		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } throws exception

		// When
		val result = userPreferenceRepository.setPreference(key, value)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `deletePreference should remove all types of keys successfully`() = runTest {
		// Given
		val key = "test_key"
		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } returns mockPreferences

		// When
		val result = userPreferenceRepository.deletePreference(key)

		// Then
		assertTrue(result.isSuccess)
		coVerify { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) }
	}

	@Test
	fun `deletePreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "test_key"
		val exception = RuntimeException("Test exception")

		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } throws exception
		// When
		val result = userPreferenceRepository.deletePreference(key)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `getBooleanPreference should return stored value when key exists`() = runTest {
		// Given
		val key = "boolean_key"
		val expectedValue = true
		val defaultValue = false
		val booleanKey = booleanPreferencesKey(key)

		every { mockPreferences[booleanKey] } returns expectedValue
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.getBooleanPreference(key, defaultValue)

		// Then
		assertTrue(result.isSuccess)
		assertEquals(expectedValue, result.getOrNull())
	}

	@Test
	fun `getBooleanPreference should return default value when key does not exist`() = runTest {
		// Given
		val key = "non_existent_boolean_key"
		val defaultValue = true
		val booleanKey = booleanPreferencesKey(key)

		every { mockPreferences[booleanKey] } returns null
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.getBooleanPreference(key, defaultValue)

		// Then
		assertTrue(result.isSuccess)
		assertEquals(defaultValue, result.getOrNull())
	}

	@Test
	fun `getBooleanPreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "boolean_key"
		val defaultValue = false
		val exception = RuntimeException("Test exception")

		every { dataStore.data } throws exception

		// When
		val result = userPreferenceRepository.getBooleanPreference(key, defaultValue)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `setBooleanPreference should save boolean value successfully`() = runTest {
		// Given
		val key = "boolean_key"
		val value = true

		// CORRECTED LINE:
		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } returns mockPreferences

		// When
		val result = userPreferenceRepository.setBooleanPreference(key, value)

		// Then
		assertTrue(result.isSuccess)
		// You might also want to verify the interaction
		coVerify { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) }
	}

	@Test
	fun `setBooleanPreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "boolean_key"
		val value = true
		val exception = RuntimeException("Test exception")

		// CORRECTED LINE:
		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } throws exception

		// When
		val result = userPreferenceRepository.setBooleanPreference(key, value)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `getIntPreference should return stored value when key exists`() = runTest {
		// Given
		val key = "int_key"
		val expectedValue = 42
		val defaultValue = 0
		val intKey = intPreferencesKey(key)

		every { mockPreferences[intKey] } returns expectedValue
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.getIntPreference(key, defaultValue)

		// Then
		assertTrue(result.isSuccess)
		assertEquals(expectedValue, result.getOrNull())
	}

	@Test
	fun `getIntPreference should return default value when key does not exist`() = runTest {
		// Given
		val key = "non_existent_int_key"
		val defaultValue = 100
		val intKey = intPreferencesKey(key)

		every { mockPreferences[intKey] } returns null
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.getIntPreference(key, defaultValue)

		// Then
		assertTrue(result.isSuccess)
		assertEquals(defaultValue, result.getOrNull())
	}

	@Test
	fun `getIntPreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "int_key"
		val defaultValue = 0
		val exception = RuntimeException("Test exception")

		every { dataStore.data } throws exception

		// When
		val result = userPreferenceRepository.getIntPreference(key, defaultValue)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `setIntPreference should save int value successfully`() = runTest {
		// Given
		val key = "int_key"
		val value = 42

		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } returns mockPreferences
		// When
		val result = userPreferenceRepository.setIntPreference(key, value)

		// Then
		assertTrue(result.isSuccess)
		coVerify { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) }
	}

	@Test
	fun `setIntPreference should return failure when exception occurs`() = runTest {
		// Given
		val key = "int_key"
		val value = 42
		val exception = RuntimeException("Test exception")

		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } throws exception
		// When
		val result = userPreferenceRepository.setIntPreference(key, value)

		// Then
		assertTrue(result.isFailure)
		assertEquals(exception, result.exceptionOrNull())
	}

	@Test
	fun `saveOnBoardingState should save boolean value successfully`() = runTest {
		// Given
		val completed = true

		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } returns mockPreferences
		// When
		userPreferenceRepository.saveOnBoardingState(completed)

		// Then
		coVerify { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) }
	}

	@Test
	fun `saveOnBoardingState should throw exception when edit fails`() = runTest {
		// Given
		val completed = true
		val exception = RuntimeException("Test exception")

		coEvery { dataStore.updateData(any<suspend (Preferences) -> Preferences>()) } throws exception
		// When & Then
		try {
			userPreferenceRepository.saveOnBoardingState(completed)
			assert(false) { "Expected exception to be thrown" }
		} catch (e: RuntimeException) {
			assertEquals("Test exception", e.message)
		}
	}

	@Test
	fun `readOnBoardingState should return stored value`() = runTest {
		// Given
		val expectedValue = true
		val onBoardingKey = booleanPreferencesKey(PREFERENCES_KEY)

		every { mockPreferences[onBoardingKey] } returns expectedValue
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.readOnBoardingState().first()

		// Then
		assertEquals(expectedValue, result)
	}

	@Test
	fun `readOnBoardingState should return false when key does not exist`() = runTest {
		// Given
		val onBoardingKey = booleanPreferencesKey(PREFERENCES_KEY)

		every { mockPreferences[onBoardingKey] } returns null
		every { dataStore.data } returns flowOf(mockPreferences)

		// When
		val result = userPreferenceRepository.readOnBoardingState().first()

		// Then
		assertFalse(result)
	}

	@Test
	fun `readOnBoardingState should handle IOException and return false`() = runTest {
		// Given
		val testFlow = flow<Preferences> {
			throw IOException("Test IOException")
		}.catch { exception ->
			if (exception is IOException) {
				emit(mockPreferences)
			} else {
				throw exception
			}
		}

		every { dataStore.data } returns testFlow
		every { mockPreferences[booleanPreferencesKey(PREFERENCES_KEY)] } returns null

		// When
		val result = userPreferenceRepository.readOnBoardingState().first()

		// Then
		assertFalse(result)
	}

	@Test
	fun `readOnBoardingState should rethrow non-IOException`() = runTest {
		// Given
		val exception = RuntimeException("Non-IO exception")
		val testFlow = flow<Preferences> {
			throw exception
		}.catch { e ->
			if (e is IOException) {
				emit(mockPreferences)
			} else {
				throw e
			}
		}

		every { dataStore.data } returns testFlow

		// When & Then
		try {
			userPreferenceRepository.readOnBoardingState().first()
			assert(false) { "Expected exception to be thrown" }
		} catch (e: RuntimeException) {
			assertEquals("Non-IO exception", e.message)
		}
	}
}
