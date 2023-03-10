package org.scarlet.flows.advanced.a4exceptions

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.scarlet.util.log

/**
 * ## Flow exceptions:
 *
 * Flow collection can complete with an exception when an emitter or code inside the flow
 * operators throw an exception. There are several ways to handle these exceptions.
 *
 * <br></br>
 *
 * ### Everything is caught:
 *
 * The following examples actually catch any exception happening in the emitter
 * or in any intermediate or terminal operators
 */

object Exceptions_thrown_in_Terminal_Operator {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        try {
            dataFlow().collect { value ->
                check(value <= 1) { "Crashed on $value" }
                log(value)
            }
        } catch (ex: Throwable) {
            log("Caught $ex")
        }
    }
}

object Exceptions_thrown_in_Intermediate_Operator {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        try {
            dataFlow()
                .map { value ->
                    check(value <= 1) { "Crashed on $value" }
                    "string $value"
                }.collect { value -> log(value) }
        } catch (ex: Throwable) {
            log("Caught $ex")
        }
    }
}

object Exceptions_thrown_in_Emitter {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        try {
            dataFlowThrow().collect { value -> log(value) }
        } catch (ex: Throwable) {
            log("Caught $ex")
        }
    }
}
