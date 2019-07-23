package org.uadaf.app.internal.eventbus.impl

import org.uadaf.app.internal.eventbus.EventAction

class EventDescriptor (
    val action: EventAction,
    val registerTime: Long
) {

}