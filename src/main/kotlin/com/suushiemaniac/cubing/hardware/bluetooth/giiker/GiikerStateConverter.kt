package com.suushiemaniac.cubing.hardware.bluetooth.giiker

import kotlin.math.absoluteValue
import kotlin.math.sign

@ExperimentalUnsignedTypes
object GiikerStateConverter {
    fun PieceState.giikerToKSolve(type: PieceType, targetMapping: List<Int>): PieceState {
        val rawMapping = targetMapping.map { it.absoluteValue }

        val permutationMapping = rawMapping.map { it / type.orientations }
        val orientationMapping = rawMapping.map { it % type.orientations }

        val orientationFlip = targetMapping.map { (it or 1).sign }

        return List(type.numPieces) {
            val kIndex = permutationMapping.indexOf(it)

            val kPermutation = permutationMapping[this.first[kIndex]]

            val orientationBias = orientationMapping[kPermutation]
            val orientationExtra = (type.orientations - orientationMapping[it]) pmod type.orientations

            val orientationSign = orientationFlip[kIndex]

            val kOrientationRaw = this.second[kIndex] * orientationSign + orientationBias + orientationExtra
            val kOrientation = kOrientationRaw pmod type.orientations

            kPermutation to kOrientation
        }.transpose()
    }

    fun CubeState.giikerToKSolve(): CubeState = mapValues {
        val perm = CONVERTER_CONFIG.getValue(it.key)
        it.value.giikerToKSolve(it.key, perm)
    }// + SOLVED_CENTERS

    fun ByteArray.toDebugState() = upperHalfBits() interlace lowerHalfBits()

    private fun ByteArray.upperHalfBits() = map { it.asUnsigned() shr 4 } // 4 upper bits
    private fun ByteArray.lowerHalfBits() = map { it.asUnsigned() and 15 } // 4 lower bits

    private infix fun <T> List<T>.interlace(other: List<T>) = zip(other).flatMap { it.toList() }

    fun ByteArray.toDecodedState(): GiikerData {
        val debugState = toDebugState()

        val explodedEdges = debugState.subList(28, 32)
            .joinToString("") { it.toString(2).padStart(4, '0') }
            .map { it.toString().toInt() }
            .take(12)

        return debugState.subList(0, 28) + explodedEdges + debugState.subList(32, 40)
    }

    fun GiikerData.moveHistory() = chunked(2).takeLast(4).map { (a, b) -> (a to b).toSignNotation() }

    fun Pair<Int, Int>.toSignNotation(): String {
        if (second == 9) {
            return (first to 2).toSignNotation()
        }

        val kFace = giikerFaceToSign[first]
        val kOrder = giikerMagnitudeToSign[second]

        return "$kFace$kOrder"
    }

    fun GiikerData.toCubeState(vararg types: PieceType): CubeState {
        return types.toList().associateWith { type ->
            val typeIndex = types.indexOf(type)

            val traversedTypes = types.toList().subList(0, typeIndex)
            val startIndex = traversedTypes.map(PieceType::numPieces).sum() * 2

            val permutationsRaw = subList(startIndex, startIndex + type.numPieces)
            val orientationsRaw = subList(startIndex + type.numPieces, startIndex + type.numPieces + type.numPieces)

            val permutations = permutationsRaw.map { it - 1 }
            val orientations = orientationsRaw.map { it % type.orientations }

            permutations to orientations
        }
    }

    // FIXME
    private fun Byte.asUnsigned() = toUByte().toString().toInt()

    private infix fun Int.pmod(base: Int) = ((this % base) + base) % base

    fun <X, Y> List<Pair<X, Y>>.transpose(): Pair<List<X>, List<Y>> {
        return map { it.first } to map { it.second }
    }

    val giikerFaceToSign = listOf("?", "B", "D", "L", "U", "R", "F")
    val giikerMagnitudeToSign = listOf("0", "", "2", "'")

    // FD-FR-FU-FL-DR-UR-UL-DL-BD-BR-BU-BL
    // KJILVBDXSTQR
    val EDGE_KSOLVE_DEFAULT = listOf(17, 14, 1, 8, 22, 6, 2, 18, 21, 12, 5, 10)

    // FDR-FRU-FUL-FLD-BRD-BUR-BLU-BDL
    // KJILTQRS
    // FDR,FUL,BUR,BDL twist CCW!
    val CORNER_KSOLVE_DEFAULT = listOf(-13, 2, -4, 17, 23, -10, 8, -19)

    val CONVERTER_CONFIG = mapOf(
        PieceType.CORNER to CORNER_KSOLVE_DEFAULT,
        PieceType.EDGE to EDGE_KSOLVE_DEFAULT
    )

    val SOLVED_CENTERS = mapOf(
        PieceType.CENTER to (listOf(0, 1, 2, 3, 4, 5) to listOf(0, 0, 0, 0, 0, 0))
    )
}
