import com.juul.kable.Options
import com.juul.kable.requestPeripheral
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import net.gregorbg.cubing.hardware.bluetooth.BlueCubeDevice
import react.*
import react.dom.attrs
import react.dom.br
import react.dom.button
import react.dom.div

@JsExport
class App : RComponent<BlueCubeProps, BlueCubeState>() {
    private fun disconnect() {
        state.connection?.cancel()

        setState {
            connection = null
        }
    }

    override fun BlueCubeState.init() {
        scope = MainScope()
        connection = null
    }

    override fun RBuilder.render() {
        button {
            attrs {
                onClickFunction = {
                    disconnect()

                    val periphOptions = Options(
                        optionalServices = arrayOf(
                            props.cube.model.bleService.uuid,
                            //props.cube.model.stateCharacteristic.uuid,
                        ),
                        filters = arrayOf(
                            Options.Filter.NamePrefix(props.cube.model.namePrefix)
                        )
                    )

                    val reqConnection = state.scope.launch {
                        val peripheral = requestPeripheral(periphOptions).await()
                        val peripheralCubeDevice = BlueCubeDevice(props.cube.model, peripheral)

                        peripheralCubeDevice.connect()

                        try {
                            peripheralCubeDevice.cubeState.collect {
                                setState {
                                    cubeStateData = it
                                }
                            }
                        } finally {
                            peripheralCubeDevice.disconnect()
                        }
                    }

                    setState {
                        connection = reqConnection
                    }
                }
            }
            +"Connect"
        }
        button {
            +"Disconnect"
            attrs {
                onClickFunction = {
                    disconnect()
                }
            }
        }
        br { }
        br { }
        +"Status"
        div {
            +(state.cubeStateData?.joinToString(", ", prefix = "[", postfix = "]") { it.toString() } ?: "Unknown")
        }
    }
}