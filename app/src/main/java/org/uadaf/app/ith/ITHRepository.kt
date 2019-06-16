package org.uadaf.app.ith

import android.graphics.Bitmap
import org.uadaf.app.ith.data.ITHStory
import org.uadaf.app.ith.data.ITHUser

interface ITHRepository {

    fun initUser(username: String): ITHUser

    fun user(): ITHUser?

    fun getLocalUsername(): String?

    fun fetchStory(id: Int): ITHStory

    fun get(id: Int): ITHStory?

    fun getCurrentStory(): ITHStory?

    fun getCurrentID(): Int

    fun increaseCurrent()

    fun decreaseCurrent()

    fun setCurrentStory(ithStory: ITHStory)

    fun isStoryLiked(id: Int): Boolean

    fun like(id: Int)

    fun unlike(id: Int)

    fun getLiked(): List<Int>

    fun getStories(): List<Int>

}