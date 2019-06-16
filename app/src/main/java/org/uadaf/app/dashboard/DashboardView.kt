package org.uadaf.app.dashboard

import androidx.annotation.IdRes

interface DashboardView {

    fun displayFragment(@IdRes fragmentRes: Int)

}