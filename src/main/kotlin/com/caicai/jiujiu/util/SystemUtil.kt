package com.caicai.jiujiu.util

import java.net.InetAddress
import java.net.NetworkInterface

object SystemUtil {
    fun getIP(): InetAddress? {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val element = networkInterfaces.nextElement()
            if (element.name.startsWith("wlan")) {
                val addresses = element.inetAddresses
                while (addresses.hasMoreElements()) {
                    val inetAddress = addresses.nextElement()
                    if (inetAddress.address.size == 4)
                        return inetAddress
                }
            }
        }
        return null
    }
}