package org.uadaf.app.ith

import org.uadaf.app.ith.data.ITHStory

interface ITHPresenter : ITHRowViewPresenter {

    fun preapre()

    fun loadCurrent()

    fun loadNext()

    fun loadPrevious()

    fun requestUserImage()

    fun getCurrentStory(): ITHStory?

    fun getCurrentStoryID(): Int

    fun like()

    fun unlike()

    fun requestStoryLikedState()

}