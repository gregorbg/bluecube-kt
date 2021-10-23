import react.dom.*
import kotlinx.browser.document
import net.gregorbg.cubing.hardware.bluetooth.BlueCube
import net.gregorbg.cubing.hardware.bluetooth.BlueCubeModel

fun main() {
    render(document.getElementById("root")) {
        h1 {
            +"BlueCube KT"
        }
        child(App::class) {
            attrs.cube = BlueCube(BlueCubeModel.MOYU_AI, "00:15:83:83:45:B1")
            //attrs.cube = BlueCube(BlueCubeModel.GIIKER, "F1:C9:BC:52:01:4C")
        }
    }
}
