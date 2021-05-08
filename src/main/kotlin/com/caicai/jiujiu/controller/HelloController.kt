package com.caicai.jiujiu.controller

import ResultUtil
import com.caicai.jiujiu.bean.DemoData
import com.caicai.jiujiu.bean.ResultBean
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.util.ResourceUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


@RestController
@RequestMapping("api/v1")
class HelloController {

    @RequestMapping("hello")
    fun hello() = Date().toString()

    @PostMapping("upload/excel")
    fun uploadExcel(@RequestParam("file") file: MultipartFile): ResultBean {
        if (file.isEmpty) return ResultUtil.fail("文件为空")
        val suffixName = file.originalFilename?.let {
            it.substring(it.lastIndexOf("."))
        }
        if (suffixName.isNullOrEmpty()) return ResultUtil.fail("没有找到文件后缀")
        if (!(suffixName.endsWith("xls") || suffixName.endsWith("xlsx"))) {
            return ResultUtil.fail("请上传excel文件")
        }
        val path = getFilePath()
        val dest = File("$path${File.separator}1$suffixName")
        if (!dest.parentFile.exists()) dest.parentFile.mkdirs()
        try {
            file.transferTo(dest)
            return ResultUtil.success(dest.absolutePath, "上传成功")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ResultUtil.fail("上传失败")
    }

    private fun getFilePath(): String {
        var path = File(ResourceUtils.getURL("classpath:").path)
        if (!path.exists())
            path = File("")
        return path.absolutePath
    }


    @RequestMapping("info")
    fun excelInfo(): ResultBean {
        val dir = File(getFilePath())
        if (!dir.exists() || dir.isFile) return ResultUtil.fail("文件为空")
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
                }
            }
            return ResultUtil.success(userList)
        }
        return ResultUtil.fail("文件不存在或格式错误")
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
}