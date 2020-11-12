package files

import excel.WriteToExcel
import java.io.File
import java.io.InputStream

class ConvertAnsysToExcelAndSave(private val openFile: List<File>,
                                 private val saveFile: File) {
    private val writeToExcel = WriteToExcel(saveFile.absoluteFile)

    private fun getTable(file: File){
        val data = parseFile(file)
        writeToExcel.createTable(data = data,
                                 tableName = file.name)
    }

    private fun parseFile(file: File): List<List<String>>{
        // Get values from file
        val lineList: MutableList<String> = getLinesFromFile(file = file).toMutableList()

        // Delete header
        with(lineList){
            removeAt(0)
            removeAt(0)
            removeAt(0)
            removeAt(0)
            removeAt(0)
        }

        // Defining the number of columns
        val data = mutableListOf<List<String>>()

        for(line in lineList){
            if(line.trim() != ""){
                val dataInLine = line.split(" ") as MutableList<String>
                dataInLine.removeAll{ it == " " || it == ""}

                when {
                    line.contains("PATH") -> {
                        data.add(listOf())
                    }
                    else -> {
                        data.add(dataInLine)
                    }
                }

            }
        }

        return data
    }

    /**
     * Get lists from files
     */
    private fun getLinesFromFile(file: File): List<String>{
        val inputStream: InputStream = File(file.absolutePath).inputStream()
        val lineList = mutableListOf<String>()

        inputStream.bufferedReader().useLines {
                lines -> lines.forEach {
                lineList.add(it)
            }
        }
        return lineList
    }

    fun convert(): Boolean{
        openFile.forEach {
            getTable(it)
        }
        return writeToExcel.saveExcel()
    }
}