package net.gregorbg.cubing.hardware.bluetooth

@JvmInline
value class LowEnergyUUID(val uuid: String) {
    companion object {
        const val BLE_BASE_SUFFIX = "-0000-1000-8000-00805f9b34fb"

        fun from16Bit(bits: Int): LowEnergyUUID {
            val prefix = bits.toString(16).padStart(8, '0')
            return LowEnergyUUID("$prefix$BLE_BASE_SUFFIX")
        }

        fun from32Bit(bits: Long): LowEnergyUUID {
            val prefix = bits.toString(16).padStart(8, '0')
            return LowEnergyUUID("$prefix$BLE_BASE_SUFFIX")
        }
    }
}
