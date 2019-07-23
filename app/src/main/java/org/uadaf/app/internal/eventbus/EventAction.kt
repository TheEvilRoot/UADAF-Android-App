package org.uadaf.app.internal.eventbus

import io.reactivex.Scheduler

interface EventAction {

    fun scheduler(): Scheduler

    fun timeout(): Long

    fun execute(executionContext: EventExecutionContext)

}