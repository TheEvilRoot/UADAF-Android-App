package org.uadaf.app.about

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface AboutRowIconView {

    fun setAppIcon(@DrawableRes res: Int)

}

interface AboutRowNameView {

    fun setAppName(@StringRes res: Int)

}

interface AboutRowAuthorsView {


}

interface AbouRowLinksView {


}