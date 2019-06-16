package org.uadaf.app.ith.impl

//
//class ITHMockRepository: ITHRepository {
//
//    private var currentID: Int = 0
//    private var currentStory: ITHStory? = null
//
//    private val liked = mutableSetOf<Int>()
//    private val stories = hashMapOf<Int, ITHStory>()
//
//    override fun fetchLastStoryID() {
//        currentID = Random.nextInt(0, 100)
//    }
//
//    override fun getCurrentStory(): ITHStory? =
//        currentStory
//
//    override fun getCurrentID(): Int =
//        currentID
//
//    override fun increaseCurrent() {
//        currentID++
//    }
//
//    override fun decreaseCurrent() {
//        currentID--
//    }
//
//    override fun setCurrentStory(ithStory: ITHStory) {
//        currentStory = ithStory
//    }
//
//    override fun fetchStory(id: Int): ITHStory {
//        val story = get(id)
//        return if (story == null) {
//            Thread.sleep(5000)
//            val newStory = ITHStory(id, "Story #$id", listOf("#$id"), "11.11.200$id", "Story text $id")
//            stories[id] = newStory
//            newStory
//        } else {
//            story
//        }
//    }
//
//
//    override fun get(id: Int): ITHStory? {
//        return stories[id]
//    }
//
//    override fun isStoryLiked(id: Int): Boolean =
//        liked.contains(id)
//
//    override fun like(id: Int) {
//        liked.add(id)
//    }
//
//    override fun unlike(id: Int) {
//        liked.remove(id)
//    }
//
//}