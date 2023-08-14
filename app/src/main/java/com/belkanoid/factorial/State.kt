package com.belkanoid.factorial

sealed class State {
    object Empty: State()
    object Loading: State()
    data class Factorial(val value: String): State()
    data class Error(val message: String): State()
}
