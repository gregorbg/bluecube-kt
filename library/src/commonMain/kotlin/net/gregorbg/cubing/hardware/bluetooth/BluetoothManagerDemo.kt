package net.gregorbg.cubing.hardware.bluetooth

object BluetoothManagerDemo {/*
    val GIIKER_191846 = BlueCube(BlueCubeModel.GIIKER, "C7:79:A8:5E:F6:B1")
    val GIIKER_111821 = BlueCube(BlueCubeModel.GIIKER, "F1:C9:BC:52:01:4C")

    val WEILONG_AI_0100100000000E5F = BlueCube(BlueCubeModel.MOYU_AI, "00:15:83:83:45:B1")

    val blueMan by lazy {
        BluetoothManagerBuilder()
            .withDiscovering(true)
            .withTinyBTransport(true)
            .build()
    }

    fun main(args: Array<String>) {
        attachBlueCubeListeners(WEILONG_AI_0100100000000E5F)
    }

    fun attachBlueCubeListeners(cube: BlueCube) {
        val stateCharacteristic = blueMan.getCharacteristicGovernor(URL(
            "XX:XX:XX:XX:XX:XX",
            cube.mac,
            cube.model.bleService.uuid,
            cube.model.stateCharacteristic.uuid
        ), true)

        stateCharacteristic.addGovernorListener { bool -> println("Authenticated: $bool, device: ${stateCharacteristic.url.deviceName} (MAC ${stateCharacteristic.url.deviceAddress})") }

        stateCharacteristic.addValueListener { data ->
            val giikerData = data.toDecodedState()

            val giikerState = giikerData.toCubeState(
                PieceType.CORNER,
                PieceType.EDGE
            )
            val kState = giikerState.giikerToKSolve()

            println()
            println("Internal byte array:   $giikerData")
            println("Decoded cube state:    $giikerState")
            println()
            println("Most recent moves: " + giikerData.moveHistory().reversed())
            println()

            for ((type, state) in kState) {
                println(type.name)
                println(state.first.joinToString(" "))
                println(state.second.joinToString(" "))
            }

            println()
            println("-----------------------------------------")
        }
    }

    fun BluetoothManager.searchNearbyCubes(cubeModel: BlueCubeModel) {
        addDeviceDiscoveryListener {
            if (it.displayName.startsWith(cubeModel.namePrefix)) {
                println("Found ${cubeModel.name} cube ${it.displayName} (MAC ${it.url.deviceAddress})")
            }
        }

        start(true)
    }
*/}
