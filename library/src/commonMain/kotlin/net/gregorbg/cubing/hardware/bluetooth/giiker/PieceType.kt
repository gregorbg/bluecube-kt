package net.gregorbg.cubing.hardware.bluetooth.giiker

enum class PieceType(val numPieces: Int, val orientations: Int) {
    CORNER(8, 3),
    EDGE(12, 2),
    CENTER(6, 1)
}