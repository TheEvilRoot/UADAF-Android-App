package org.uadaf.app.ith.impl

import org.uadaf.app.R
import org.uadaf.app.internal.ITHStoryDoesNotExists
import org.uadaf.app.internal.UADAFServiceException
import org.uadaf.app.ith.ITHRepository
import org.uadaf.app.ith.data.ITHStory
import org.uadaf.app.ith.data.ITHUser
import org.uadaf.app.ith.service.ITHService
import org.uadaf.app.ith.service.ITHWebFetcherService
import org.uadaf.app.preferences.PreferencesProvider
import java.lang.RuntimeException

class ITHRepositoryImpl (
    private val service: ITHService,
    private val fetcher: ITHWebFetcherService,
    private val preferencesProvider: PreferencesProvider
): ITHRepository {

    private var user: ITHUser? = null
    private var currentStory: ITHStory? = null

    private val stories = hashMapOf<Int, ITHStory>()
    private val liked = mutableSetOf<Int>()

    override fun user(): ITHUser? =
        user

    override fun getLocalUsername(): String? {
        val username = preferencesProvider.string(preferencesProvider.preferenceName(R.string.preference_ith_username), "")
        if (username.isEmpty()) {
            return null
        }
        return username
    }
    override fun initUser(username: String): ITHUser {

        val call = service.login(username)

        val response = call.execute()

        if (!response.isSuccessful) {
            throw UADAFServiceException("ITH", "initUser")
        }

        val fetched = response.body() ?:
                throw UADAFServiceException("ITH", "initUser:emptyBody")

        user = fetched

        return fetched
    }

    override fun getCurrentStory(): ITHStory? =
        currentStory

    override fun getCurrentID(): Int {
        if (user == null) {
            throw RuntimeException("Unable to get current id without user initialization.  Are you sure you called initUser()?")
        } else {
            return user?.storyId ?: 0
        }
    }

    override fun increaseCurrent() {
        if (user == null) {
            throw RuntimeException("Unable to get current id without user initialization.  Are you sure you called initUser()?")
        } else {
            user!!.storyId++
            updateServiceData()
        }
    }

    override fun decreaseCurrent() {
        if (user == null) {
            throw RuntimeException("Unable to get current id without user initialization.  Are you sure you called initUser()?")
        } else {
            user!!.storyId--
            updateServiceData()
        }
    }

    private fun updateServiceData() {
        val call = service.set(user!!.username, user!!.storyId.toString())

        val response = call.execute()

        if (!response.isSuccessful) {
            throw UADAFServiceException("ITH", "updateServiceData : ${response.code()}")
        }
    }

    override fun setCurrentStory(ithStory: ITHStory) {
        currentStory = ithStory
    }

    override fun fetchStory(id: Int): ITHStory {
        val story = get(id)
        return if (story == null) {
            val call = fetcher.story(id)

            val response = call.execute()

            when (response.code()) {
                200 -> {
                    val newStory = response.body() ?:
                        throw UADAFServiceException("ITH", "fetchStory:emptyBody")

                    stories[id] = newStory
                    newStory
                }

                404 -> throw ITHStoryDoesNotExists(id)
                else -> throw UADAFServiceException("ITH", "fetchStory: ${response.code()}")
            }
        } else {
            story
        }
    }


    override fun get(id: Int): ITHStory? {
        return stories[id]
    }

    override fun isStoryLiked(id: Int): Boolean {
        Thread.sleep(500)
        return liked.contains(id)
    }

    override fun like(id: Int) {
        liked.add(id)
    }

    override fun unlike(id: Int) {
        liked.remove(id)
    }

    override fun getLiked(): List<Int> {
        Thread.sleep(1000)
        return liked.toList()
    }

    override fun getStories(): List<Int> {
        Thread.sleep(100)
        return stories.keys.toList()
    }
}