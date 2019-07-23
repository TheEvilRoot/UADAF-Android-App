package org.uadaf.app.internal.eventbus

import io.reactivex.disposables.CompositeDisposable

interface EventBus {

    fun registerHandler(eventType: EventType, action: EventAction)

    fun dispatch(eventType: EventType)

}