package org.uadaf.app.about

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.uadaf.app.about.data.Author
import org.uadaf.app.about.data.Link

interface AboutProvider {

    @DrawableRes
    fun getAppIcon(): Int

    @StringRes
    fun getAppName(): Int

    @StringRes
    fun getDescription(): Int

    fun getAuthorCount(): Int

    fun getAuthor(position: Int): Author

    fun getLinksCount(): Int

    fun getLink(position: Int): Link

}