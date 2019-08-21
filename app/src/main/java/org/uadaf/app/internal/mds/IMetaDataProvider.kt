package org.uadaf.app.internal.mds

interface IMetaDataProvider {

    fun setFlag(key: String, value: Int)

    fun getFlag(key: String, default: Int = 0): Int

    fun hasFlag(key: String): Boolean

    fun clearFlag(key: String): Int?

}