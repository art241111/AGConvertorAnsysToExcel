package excel

import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class WriteToExcel(private val outputFile: File){
    private val workbook = XSSFWorkbook()

    fun createTable(
            data: List<List<String>>,
            tableName: String): Boolean {

        val sheet = workbook.createSheet(tableName)

        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.RED.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)

        data.forEachIndexed { dataIndex, list ->
                val row = sheet.createRow(dataIndex)
                list.forEachIndexed { index, s ->
                    val cell = row.createCell(index)

                    val value = s.replace(",",".").toDoubleOrNull()

                    if (value == null) {
                        cell.setCellValue(s)
                        cell.cellStyle = headerCellStyle
                    } else {
                        cell.setCellValue(value)
                    }
                }
        }
        return true
    }

    fun saveExcel(): Boolean{
        try {
            val fileOut = FileOutputStream(outputFile.absoluteFile)
            workbook.write(fileOut)
            fileOut.close()
            workbook.close()
        } catch (e: Exception) {
            return false
        }
        return true
    }
}
