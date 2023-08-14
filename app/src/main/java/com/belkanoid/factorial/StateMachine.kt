package com.belkanoid.factorial


sealed class Action {
    object ProcedureFactorial: Action()
    data class ProcedureFailure(val message: String): Action()
    data class ProcedureSuccess(val value: String): Action()
}

class StateMachine(
    private val onStateChanged: (State) -> Unit
) {
    fun dispatch(action: Action) {
        when(action) {
            is Action.ProcedureFactorial -> {
                onStateChanged(State.Loading)
            }
            is Action.ProcedureSuccess -> {
                onStateChanged(State.Factorial(action.value))
            }
            is Action.ProcedureFailure -> {
                onStateChanged(State.Error(action.message))
            }
        }
    }

}