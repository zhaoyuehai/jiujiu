package com.caicai.jiujiu.bean

import org.junit.jupiter.api.Test


class DemoDataTest {

    @Test
    fun test1() {
        val d = DemoData(
                "",
                "",
                "",
                "08:54",
                "",
                "19:23",
                "",
                "",
                "",
        )
        val data = d.calculate()

        println(data?.desc)
    }
    @Test
    fun test2() {
        print(myFormatTime(171))
    }
    private fun myFormatTime(min: Int) = when {
        min / 60 == 0 -> {
            "${min}分钟"
        }
        min % 60 == 0 -> {
            "${min}分钟 = ${Math.floorDiv(min, 60)}小时"
        }
        else -> {
            "${min}分钟 = ${Math.floorDiv(min, 60)}小时${min % 60}分钟"
        }
    }
}