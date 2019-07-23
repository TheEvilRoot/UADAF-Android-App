package org.uadaf.app.internal.eventbus.impl

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.uadaf.app.internal.eventbus.EventAction
import org.uadaf.app.internal.eventbus.EventExecutionContext

class BaseEventAction (
    private val scheduler: Scheduler = Schedulers.io(),
    private val timeout: Long = -1,
    private val block: EventExecutionContext.() -> Unit
): EventAction {

    override fun scheduler(): Scheduler = scheduler

    override fun timeout(): Long = timeout

    override fun execute(executionContext: EventExecutionContext) = block(executionContext)

}