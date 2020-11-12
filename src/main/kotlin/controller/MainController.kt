package controller

import Main
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.stage.FileChooser
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import files.ConvertAnsysToExcelAndSave
import java.io.File


/**
 * Controller for JavaFX.
 * @author artem241120@gmail.com (Artem Gerasimov)
 */
class MainController {
    @FXML
    lateinit var statusLabel: Label

    @FXML
    lateinit var saveButton: Button

    private lateinit var openFiles: List<File>
    @FXML
    private fun selectFile(event: ActionEvent) {
        println("You clicked me!")
        val fileChooser = FileChooser()
        fileChooser.title = "Choose ansys files"

        val list = fileChooser.showOpenMultipleDialog(Main.javaFXC)
        if (list != null) {
            openFiles = list

            writeInStatusLabel(text = "Файл выбран. Теперь можно сохранить в Excel.")
        }
    }

    @FXML
    private fun saveExcel(event: ActionEvent) {
        val fileChooser = FileChooser() //Класс работы с диалогом выборки и сохранения
        fileChooser.title = "Save Document" //Заголовок диалога

        val extFilter = FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx") //Расширение

        fileChooser.initialFileName = "1"
        fileChooser.extensionFilters.add(extFilter)
        val file = fileChooser.showSaveDialog(Main.javaFXC)

        if (file != null) {
            saveFile(file)
            println(file.absoluteFile)
        }
    }

    private fun saveFile(saveFiles: File) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        if (this::openFiles.isInitialized){
            scope.launch {
                if(ConvertAnsysToExcelAndSave(openFiles,saveFiles).convert()){
                    writeInStatusLabel(text = "Файл сохранен")
                } else{
                    writeInStatusLabel(text = "Сохранение не удалось. Проверьте, чтобы файл был закрыт.")
                }
            }
        }
    }

    private fun writeInStatusLabel(text: String){
        val scope = CoroutineScope(Dispatchers.JavaFx)

        if(this::statusLabel.isInitialized){
            scope.launch {
                statusLabel.text = text
            }
        }
      }

    fun initialize() {
        println("Controller working")
          statusLabel.text = "Выберете файл(лы)"
    }
}