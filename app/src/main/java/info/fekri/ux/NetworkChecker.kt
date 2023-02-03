package info.fekri.ux

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkChecker(private val context: Context) {

    val isInternetConnected: Boolean
        @SuppressLint("ObsoleteSdkInt")
        get() {

            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val myNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

                result = when {

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true // TRANSPORT_CELLULAR is about mobile data

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true // TRANSPORT_ETHERNET is checking is user connected to internet by usb?

                    else -> false
                }


            } else {

                result = when (connectivityManager.activeNetworkInfo?.type) {

                    ConnectivityManager.TYPE_WIFI -> true

                    ConnectivityManager.TYPE_MOBILE -> true

                    ConnectivityManager.TYPE_ETHERNET -> true

                    else -> false
                }

            }

            return result

        }

    val isWifiConnected: Boolean
        @SuppressLint("ObsoleteSdkInt")
        get() {

            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val myNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

                result = when {

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false

                    else -> false

                }
            } else {

                result = when (connectivityManager.activeNetworkInfo?.type) {

                    ConnectivityManager.TYPE_WIFI -> true

                    ConnectivityManager.TYPE_MOBILE -> false

                    ConnectivityManager.TYPE_ETHERNET -> false

                    else -> false

                }

            }

            return result
        }

    val isMobileDataConnected: Boolean
        @SuppressLint("ObsoleteSdkInt")
        get() {

            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val myNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

                result = when {

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false

                    else -> false

                }
            } else {

                result = when (connectivityManager.activeNetworkInfo?.type) {

                    ConnectivityManager.TYPE_WIFI -> false

                    ConnectivityManager.TYPE_MOBILE -> true

                    ConnectivityManager.TYPE_ETHERNET -> false

                    else -> false

                }

            }

            return result
        }

    val isEthernetConnected: Boolean
        @SuppressLint("ObsoleteSdkInt")
        get() {

            var result = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val myNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

                result = when {

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false

                    myNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

                    else -> false

                }

            } else {

                result = when (connectivityManager.activeNetworkInfo?.type) {

                    ConnectivityManager.TYPE_WIFI -> false

                    ConnectivityManager.TYPE_MOBILE -> false

                    ConnectivityManager.TYPE_ETHERNET -> true

                    else -> false

                }

            }

            return result
        }

}