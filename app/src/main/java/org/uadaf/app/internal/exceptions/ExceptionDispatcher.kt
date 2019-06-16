package org.uadaf.app.internal.exceptions

import androidx.annotation.IdRes
import org.uadaf.app.internal.view.IErrorView

interface ExceptionDispatcher {

    fun dispatch(exception: Throwable, withView: IErrorView, @IdRes fragment: Int? = null, message: String = "%s")

    fun dispatchFatal(exception: Throwable)

}