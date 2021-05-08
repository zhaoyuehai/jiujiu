package com.caicai.jiujiu.bean

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*


class DemoDataTest {

    @Test
    fun test1() {
        val d = DemoData(
            "",
            "21-03-11 星期六",
            "",
            "08:54",
            "",
            "12:23",
            "",
            "",
            "",
        )
        val data = d.calculate()

        println(data?.desc)
        println(data?.jiaBan)
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

    private fun getFilePath(): String {
        var path = File(ResourceUtils.getURL("classpath:").path)
        if (!path.exists())
            path = File("")
        return path.absolutePath
    }

    @Test
    fun test3() {
        val dir = File(getFilePath())
        if (!dir.exists() || dir.isFile) return
        var wb: Workbook? = null
        dir.listFiles()?.forEach { file ->
            when (file.name) {
                "1.xlsx" -> wb = XSSFWorkbook(file)
                "1.xls" -> wb = HSSFWorkbook(FileInputStream(file))
            }
        }
        wb?.let { it ->//开始解析
            val sheet = it.getSheetAt(0)     //读取sheet 0
            val firstRowIndex = sheet.firstRowNum + 4   //第一行是列名，所以不读
            val lastRowIndex = sheet.lastRowNum
            val userList = mutableListOf<DemoData>()
            for (xIndex in firstRowIndex..lastRowIndex) {//行
                val row = sheet.getRow(xIndex)//列  5~10
                if (row != null) {
                    val user = DemoData()
                    user.name = row.getCell(0).toString()
                    user.date = row.getCell(6).toString()
                    user.classNum = row.getCell(7).toString()
                    user.startTime1 = getRealTime(row.getCell(8))
                    user.startResult1 = row.getCell(9).toString()
                    user.endTime1 = getRealTime(row.getCell(10))
                    user.endResult1 = row.getCell(11).toString()
                    user.startTime2 = getRealTime(row.getCell(12))
                    user.calculate().let { userList.add(it) }
                    println(user)
                }
            }
        }
    }

    private fun getRealTime(cell: Cell?): String {
        if (cell == null) return ""
        if (cell.cellType == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            val date = cell.dateCellValue.toString()//Sun Dec 31 08:53:00 CST 1899
            return SimpleDateFormat("HH:mm").format(
                SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(
                    date
                )
            )
        }
        return cell.toString()
    }


    @Test
    fun testTime() {
//        val date = "Sun Dec 31 19:00:00 CST 1899"
        val date = "Sun Dec 31 08:53:00 CST 1899"
        println(
            SimpleDateFormat("HH:mm").format(
                SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(
                    date
                )
            )
        )
    }
}