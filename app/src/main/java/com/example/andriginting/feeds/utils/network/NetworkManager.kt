package com.example.andriginting.feeds.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkManager : BroadcastReceiver() {

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
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