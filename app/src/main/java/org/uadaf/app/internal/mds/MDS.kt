package org.uadaf.app.internal.mds

object MDS: IMetaDataProvider {

    const val KEY_ITH_LOGIN_REQUESTED = "ith_login_requested"

    private val mdStorage: HashMap<String, Int> = HashMap()

    override fun setFlag(key: String, value: Int) {
        mdStorage[key] = value
    }

    override fun getFlag(key: String, default: Int): Int {
        return mdStorage[key] ?: default
    }

    override fun hasFlag(key: String): Boolean {
        return mdStorage.containsKey(key)
    }

    override fun clearFlag(key: String): Int? {
        val value = mdStorage[key]
        mdStorage.remove(key)
        return value
    }

}