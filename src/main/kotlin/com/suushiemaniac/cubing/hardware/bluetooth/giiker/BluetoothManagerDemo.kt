package com.suushiemaniac.cubing.hardware.bluetooth.giiker

import org.sputnikdev.bluetooth.URL
import org.sputnikdev.bluetooth.manager.impl.BluetoothManagerBuilder

import com.suushiemaniac.cubing.hardware.bluetooth.giiker.GiikerStateConverter.moveHistory
import com.suushiemaniac.cubing.hardware.bluetooth.giiker.GiikerStateConverter.toDecodedState
import com.suushiemaniac.cubing.hardware.bluetooth.giiker.GiikerStateConverter.toCubeState
import com.suushiemaniac.cubing.hardware.bluetooth.giiker.GiikerStateConverter.giikerToKSolve
import org.sputnikdev.bluetooth.manager.BluetoothManager

@ExperimentalUnsignedTypes
object BluetoothManagerDemo {
    // suushie GiiKER-i3s
    // Gi191846 (C7:79:A8:5E:F6:B1)

    const val GIIKER_191846 = "C7:79:A8:5E:F6:B1"

    @JvmStatic
    fun main(args: Array<String>) {
        attachGiikerListeners(GIIKER_191846)
    }

    fun attachGiikerListeners(mac: String) {
        val blueMan = BluetoothManagerBuilder()
            .withDiscovering(true)
            .withTinyBTransport(true)
            .build()

        val giiker = blueMan.getCharacteristicGovernor(URL(
            "XX:XX:XX:XX:XX:XX",
            mac,
            "0000aadb-0000-1000-8000-00805f9b34fb",
            "0000aadc-0000-1000-8000-00805f9b34fb"
        ), true)

        giiker.addGovernorListener { bool -> println("Authenticated: $bool, device: ${giiker.url.deviceName} (MAC ${giiker.url.deviceAddress}) (adapter ${giiker.url.adapterAddress})") }

        giiker.addValueListener { data ->
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

    fun BluetoothManager.searchNearbyCubes() {
        addDeviceDiscoveryListener {
            if (it.displayName.startsWith("Gi")) {
                println("Found GiiKER cube ${it.displayName} (MAC ${it.url.deviceAddress}) (adapter ${it.url.adapterAddress})")
            }
        }

        start(true)
    }
}
