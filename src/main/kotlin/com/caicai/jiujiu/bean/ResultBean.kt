package com.caicai.jiujiu.bean

data class ResultBean(
        var code: String,
        val message: String,
        val serviceVersion: String,
        val data: Any? = null
)