package org.uadaf.app.internal.eventbus.impl

import android.content.Context
import org.uadaf.app.internal.eventbus.EventExecutionContext

class EventExecutionContextImpl (
    private val context: Context
): EventExecutionContext {
    override fun applicationContext(): Context =
        context

}