package org.uadaf.app.ith.story

import android.content.Context
import android.os.Bundle
import org.uadaf.app.ith.ITHRowViewPresenter

interface ITHStoryPresenter: ITHRowViewPresenter {

    fun load(arguments: Bundle?)

    fun like()

    fun unlike()

    fun requestStoryLikedState()

    fun openInWeb(context: Context)

    fun jump()

}