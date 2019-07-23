package org.uadaf.app.internal.eventbus.impl

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import org.uadaf.app.internal.eventbus.EventAction
import org.uadaf.app.internal.eventbus.EventBus
import org.uadaf.app.internal.eventbus.EventType
import java.util.*
import kotlin.collections.HashMap

class EventBusImpl (
    applicationContext: Context
): EventBus {

    private val handlers = HashMap<EventType, Queue<EventDescriptor>>()
    private val trashBin: CompositeDisposable = CompositeDisposable()
    private val executionContext = EventExecutionContextImpl(applicationContext)

    override fun registerHandler(eventType: EventType, action: EventAction) {
        if (eventType !in handlers) handlers[eventType] = LinkedList()
        handlers[eventType]?.run {
            val descriptor = EventDescriptor(action, System.currentTimeMillis())
            offer(descriptor)
        }
    }

    override fun dispatch(eventType: EventType) {
        handlers[eventType]?.run {
            while (isNotEmpty()) {
                val descriptor = remove()
                if (descriptor.action.timeout() < 0 || descriptor.registerTime + descriptor.action.timeout() <= System.currentTimeMillis()) {
                    trashBin.add(descriptor.action.scheduler().scheduleDirect { descriptor.action.execute(executionContext) })
                }
            }
        }
    }
}