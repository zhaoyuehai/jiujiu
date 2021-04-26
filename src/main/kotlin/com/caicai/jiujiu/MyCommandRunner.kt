package com.caicai.jiujiu

import com.caicai.jiujiu.util.SystemUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class MyCommandRunner : CommandLineRunner {

    @Value("\${server.port}")
    var port: String = ""

    override fun run(vararg args: String?) {
        SystemUtil.getIP()?.hostAddress?.let {
            val url = "http://$it${if (port != "80") ":$port" else ""}"
            //打印服务地址
            val sb = StringBuffer("[")
            for (i in 0 until url.length + 10) {
                sb.append("-")
            }
            val sbStr = sb.append("]").toString()
            println(sbStr)
            println(sbStr)
            println("[---- $url ----]")
            println(sbStr)
            println(sbStr)
        }
//        try {
//            Runtime.getRuntime().exec("cmd /c start http://localhost:8080")
//        } catch (e: Exception) {
//        }
    }
}