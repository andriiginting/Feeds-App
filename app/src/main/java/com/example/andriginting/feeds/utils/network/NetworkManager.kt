package com.example.andriginting.feeds.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import io.reactivex.Observable

class NetworkManager : BroadcastReceiver() {

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null

        //another approach
        fun isInternetConnected(context: Context): Observable<Boolean>{
            val conn: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = conn.activeNetworkInfo
            return Observable.just(activeNetwork != null && activeNetwork.isConnected)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val isConnected = context?.let { checkConnection(it) }

        if (connectivityReceiverListener != null) {
            isConnected?.let { connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected = it) }
        }

    }

    private fun checkConnection(context: Context): Boolean {
        val conn: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = conn.activeNetworkInfo

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting)

    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

}