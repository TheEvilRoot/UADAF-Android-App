package org.uadaf.app.ith.data

import com.google.gson.annotations.SerializedName

data class ITHUser (
  @SerializedName("username") val username: String,
  @SerializedName("storyId") var storyId: Int
)