package com.caicai.jiujiu.bean

data class DemoData(
    var name: String? = null,//姓名 0
    var date: String? = null,//日期 6
    var classNum: String? = null, //班次 7
    var startTime1: String? = null, //上班1打卡时间 8
    var startResult1: String? = null, //"上班1打卡结果 9
    var endTime1: String? = null,//"下班1打卡时间 10
    var endResult1: String? = null, //"下班1打卡结果 11
    var startTime2: String? = null,//"上班2打卡时间 12
    var desc: String? = null,//评价
    var jiaBan: String? = null,//加班h
) {
    //标准是  8:30  - 17：30
    //弹性最大值 10:00 - 20:30  【下班考虑补时2倍】
    fun calculate(): DemoData {
        if (classNum != null && isNoEmpty(startTime1) && isNoEmpty(endTime1)) {
            val startTimes = startTime1!!.split(":")
            val endTimes = endTime1!!.split(":")
            if (classNum == "休息") {  //判断放假
                val h8 = startTimes[0].toInt()
                val minStart = startTimes[1].toInt()
                val startMinutes = h8 * 60 + minStart //8x60 + 30 = 510 分钟，
                val h17 = endTimes[0].toInt()
                val minEnd = endTimes[1].toInt()
                val endMinutes = h17 * 60 + minEnd
                val jiaBanMin = endMinutes - startMinutes //周末加班分钟
                jiaBan = myFormatTime(jiaBanMin, true)
                desc = if (jiaBanMin < 3600) {
                    "加班已足6小时☺"
                } else {
                    "不足6小时🤣"
                }
                return this
            } else {
                val h8 = startTimes[0].toInt()
                val minStart = startTimes[1].toInt()
                var startMinutes = h8 * 60 + minStart //8x60 + 30 = 510 分钟， 比这个早的都算8:30来的
                if (startMinutes < 510) startMinutes = 510

                val h17 = endTimes[0].toInt()
                val minEnd = endTimes[1].toInt()
                val endMinutes = h17 * 60 + minEnd

                val chiDaoMin = startMinutes - 510//迟到分钟

                val duoGanMin = endMinutes - 1050//标准17x60 + 30 = 1050 分钟,比这个晚的才是多干的时间

                val jiaBanMin = duoGanMin - chiDaoMin * 2//多干的时间 - 迟到时间x2 = 可以计算加班的时间

                if (jiaBanMin > 0) {
                    if (jiaBanMin >= 120) {
                        val jiaBanHours = Math.floorDiv(jiaBanMin, 60)//加班小时数 2h 3h 4h 5h ...
                        jiaBan = "${2 * Math.floorDiv(jiaBanHours, 2)}小时"//1 1 2 2 ...
                        desc = "【有效加班$jiaBan】🤩实际干了${myFormatTime(jiaBanMin)}"
                    } else {
                        desc = "白干了${myFormatTime(jiaBanMin)}😂"
                    }
                } else if (jiaBanMin == 0) {
                    desc = "哎哟，时间管理大师😄"
                } else {
                    desc = "你迟到了额🙄应该晚走${myFormatTime(-jiaBanMin)}"
                }
            }
            return this
        } else {
            return this
        }
    }

    private fun myFormatTime(min: Int, justHour: Boolean = false) = when {
        min / 60 == 0 -> if (justHour) "" else "${min}分钟"
        min % 60 == 0 -> if (justHour) "${Math.floorDiv(min, 60)}小时" else "${Math.floorDiv(min, 60)}小时"
        else -> if (justHour) "${Math.floorDiv(min, 60)}小时" else "${Math.floorDiv(min, 60)}小时${min % 60}分钟"
    }

    private fun isNoEmpty(s: String?) = s != null && s.length == 5 && s.contains(":")
}