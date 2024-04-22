package com.example.imageloaderpracticalnative.helper

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Window
import android.widget.Toast
import com.example.imageloaderpracticalnative.R

object Const {
    private const val TAG = "Const--->"

    private var loaderDialog: Dialog? = null

    fun isOnline(context: Context): Boolean {
        var isConnected = false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            isConnected = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                activeNetworkInfo?.run {
                    isConnected = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return isConnected
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showDialog(context: Context) {
        loaderDialog = Dialog(context)
        loaderDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loaderDialog!!.setContentView(R.layout.loader)
        loaderDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loaderDialog!!.setCancelable(false)
        loaderDialog!!.show()
    }

    fun dismissDialog() {
        loaderDialog!!.dismiss()
    }
}
