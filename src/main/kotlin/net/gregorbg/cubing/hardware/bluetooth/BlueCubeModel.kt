package net.gregorbg.cubing.hardware.bluetooth

enum class BlueCubeModel(val namePrefix: String, val bleService: LowEnergyUUID, val stateCharacteristic: LowEnergyUUID, val gyroCharacteristic: LowEnergyUUID? = null) {
    GIIKER("Gi", LowEnergyUUID.from16Bit(0xAADB), LowEnergyUUID.from16Bit(0xAADC)),
    MOYU_AI("MHC-", LowEnergyUUID.from16Bit(0x1000), LowEnergyUUID.from16Bit(0x1003))
}