import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import react.State

external interface BlueCubeState : State {
    var scope: CoroutineScope
    var connection: Job?
    var cubeStateData: ByteArray?
}