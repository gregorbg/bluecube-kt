package net.gregorbg.cubing.hardware.bluetooth

import com.juul.kable.Peripheral
import com.juul.kable.characteristicOf
import kotlinx.coroutines.flow.Flow

class BlueCubeDevice(cubeModel: BlueCubeModel, val peripheral: Peripheral) : Peripheral by peripheral {
    val cubeStateCharacteristic = characteristicOf(cubeModel.bleService.uuid, cubeModel.stateCharacteristic.uuid)
    val cubeState: Flow<ByteArray> get() = peripheral.observe(cubeStateCharacteristic)
}