package com.caicai.jiujiu.bean

enum class ResultState(val code: String, val message: String) {
    ERROR("00000", "未知错误"),
    SUCCESS("10000", "success"),
}