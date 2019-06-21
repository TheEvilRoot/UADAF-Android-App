package org.uadaf.app.login

import android.graphics.Bitmap

interface UserSession {

    fun id(): Int

    fun username(): String

    fun pictureUrL(): String

    fun cachedPicture(): Bitmap?

    fun role(): String

}