package org.uadaf.app.ith

import android.graphics.Bitmap
import org.uadaf.app.internal.view.IErrorView

interface ITHView: IErrorView {

    fun displayStory()

    fun startLoading()

    fun stopLoading()

    fun displayStoryDoesNotExists(storyID: Int)

    fun setLiked(isLiked: Boolean)

    fun setPreviousState(enabled: Boolean)

    fun setNextState(enabled: Boolean)

    fun displayUsernameDialog()

    fun displayUsername(username: String)

    fun displayUserImage(image: Bitmap)

    fun displayNoUserImage()

    fun disableLike()

}