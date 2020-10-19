package no.kristiania.androidprogrammering.noforeignlandapp

import android.content.Context
import android.net.ConnectivityManager

class Utils {

    //Implementation of checking network connection as demonstrated in class-material
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
