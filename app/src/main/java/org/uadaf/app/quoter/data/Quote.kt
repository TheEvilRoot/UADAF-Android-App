package org.uadaf.app.quoter.data

import com.google.gson.annotations.SerializedName

class Quote (
    @SerializedName("id") val id: Int,
    @SerializedName("adder") val adder: String,
    @SerializedName("author") val author: String,
    @SerializedName("quote") val text: String,
    @SerializedName("edited_by") val editedBy: String?,
    @SerializedName("edited_at") val editedAt: Long
)