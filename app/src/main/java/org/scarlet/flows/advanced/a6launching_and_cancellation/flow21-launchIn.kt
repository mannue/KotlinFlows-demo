package org.scarlet.flows.advanced.a6launching_and_cancellation

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.scarlet.util.log

/**
 * ## Launching flow:
 *
 * It is easy to use flows to represent asynchronous events that are coming from some source.
 * In this case, we need an analogue of the `addEventListener` function that registers a piece of
 * code with a reaction for incoming events and continues further work.
 * The `onEach` operator can serve this role. However, `onEach` is an intermediate operator.
 * We also need a terminal operator to collect the flow. Otherwise, just calling `onEach` has no effect.
 *
 * If we use the `collect` terminal operator after `onEach`, then the code after it will wait until
 * the flow is collected:
 */

object Flow_Collection_Using_Lonely_Collect {
    // Imitate a flow of events
    private fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        events()
            .onEach { event -> log("Event: $event") }
            .collect() // <--- Collecting the flow waits

        log("Done")
    }
}

/**
 * ## Launching flow:
 *
 * The `launchIn` terminal operator comes in handy here. By replacing `collect` with `launchIn`
 * we can launch a collection of the flow in a separate coroutine, so that execution of further
 * code immediately continues.
 *
 * The required parameter to `launchIn` must specify a `CoroutineScope` in which the coroutine to
 * collect the flow is launched.
 *
 * This way the pair of `onEach { ... }.launchIn(scope)` works like the `addEventListener`. However,
 * there is no need for the corresponding `removeEventListener` function, as cancellation and
 * structured concurrency serve this purpose.
 *
 * Note that `launchIn` also returns a `Job`, which can be used to cancel the corresponding flow
 * collection coroutine only without cancelling the whole scope or to join it.
 */
object Flow_Collection_Using_LaunchIn {
    // Imitate a flow of events
    private fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

    @JvmStatic
    fun main(args: Array<String>) = runBlocking{

        events()
            .onEach { event -> log("Event: $event") }
            .launchIn(this) // <--- Launching the flow in a separate coroutine

        log("I will be printed first ...")
    }
}







