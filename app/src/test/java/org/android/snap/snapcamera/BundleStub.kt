package org.android.snap.snapcamera

import android.os.Bundle

object BundleStub {
	fun googleIdTokenData(idToken: String): Bundle {
		val bundle = Bundle()
		bundle.putString("idToken", idToken)
		return bundle
	}
}
