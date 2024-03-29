package com.villaindustrias.sdpv3.utilidades

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

private const val REQUEST_PERMISSION_COARSE_LOCATION = 101

internal fun Context.isLocationPermissionGranted(): Boolean = when {
    Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> true // It is not needed at all as there were no runtime permissions yet
    else -> ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
}


internal fun Activity.requestLocationPermission() =
        ActivityCompat.requestPermissions(
                this,
                arrayOf(permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSION_COARSE_LOCATION
        )


internal fun Fragment.requestLocationPermission() =
    ActivityCompat.requestPermissions(
        activity!!,
        arrayOf(permission.ACCESS_COARSE_LOCATION),
        REQUEST_PERMISSION_COARSE_LOCATION
    )

internal fun isLocationPermissionGranted(requestCode: Int, grantResults: IntArray) =
        requestCode == REQUEST_PERMISSION_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED
