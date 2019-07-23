package org.uadaf.app.internal.eventbus

import android.content.Context

interface EventExecutionContext {

    fun applicationContext(): Context

}