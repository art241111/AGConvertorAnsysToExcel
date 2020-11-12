import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlin.system.exitProcess

class Main : Application() {
    private val layout = "main.fxml"
    private val logo = "logo.ico"

    override fun start(primaryStage: Stage?) {
//        System.setProperty("prism.lcdtext", "false") // for beautiful fonts on linux
        try {
            val root = load<Parent>(javaClass.getResource(layout))
            val icon = Image(Main::class.java.classLoader.getResourceAsStream(logo))

            val scene = Scene(root)
            if(primaryStage != null){
                with(primaryStage){
                    setScene(scene)
                    isResizable = false
                    title = "Convert ansys file to excel"
                    icons.add(icon)
                    show()
                    javaFXC = this
                }
            }
        } catch (e: Exception){
            print(e)
            exitProcess(0)
        }

    }

    companion object {
        var javaFXC: Stage? = null

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}