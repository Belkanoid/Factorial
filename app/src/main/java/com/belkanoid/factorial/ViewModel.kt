package com.belkanoid.factorial

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class ViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Empty)
    val state = _state.asStateFlow()

    private val stateMachine = StateMachine {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ADV", it.toString())
            _state.emit(it)
        }
    }

    fun calculate(number: String?) {
        if (number.isNullOrBlank()) {
            stateMachine.dispatch(Action.ProcedureFailure("Значение должно быть больше нуля"))
            return
        }
        stateMachine.dispatch(Action.ProcedureFactorial)
        viewModelScope.launch {
            calculateFactorial(number.toLong())
        }
    }

    private suspend fun calculateFactorial(number: Long) = withContext(Dispatchers.Default) {
        var result = BigInteger.ONE
        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }
        stateMachine.dispatch(Action.ProcedureSuccess(value = result.toString()))
    }


}