package com.example.ihsanstask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.Calendar
import java.util.Locale

fun View.snack(
    @StringRes messageRes: Int,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
) {
    snack(resources.getString(messageRes), length, f)
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun String.debugPrint() {
    Timber.d(this)
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        //for other device how are able to connect with Ethernet
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        //for check internet over Bluetooth
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

fun FragmentManager.showDatePicker(
    startMonth: Int = Calendar.getInstance(Locale("en"))[Calendar.MONTH],
    startYear: Int = Calendar.getInstance(Locale("en"))[Calendar.YEAR],
    endYear: Int = Calendar.getInstance(Locale("en"))[Calendar.YEAR],
    pickerType: PickerType = PickerType.DATE,
    result: (Int, Int, Int) -> Unit
) {

    DateTimeDialog(startMonth, startYear, endYear, pickerType)
    { year, month, day -> result(year, month, day) }.show(this, "MonthYearPickerDialog")
}

fun RecyclerView.addDecoration(
    spacing: Int = resources.getDimension(com.intuit.sdp.R.dimen._8sdp).toInt(),
    isGrid: Boolean = false,
    isSingleVertically: Boolean = false
) {
    addItemDecoration(DividerItemDecoration(spacing, isGrid, isSingleVertically))
}
