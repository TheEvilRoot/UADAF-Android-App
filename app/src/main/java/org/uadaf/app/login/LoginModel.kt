package org.uadaf.app.login

interface LoginModel {

    /** Client side **/

    fun saveData(userData: UserData)

    fun loadSavedData(): UserData

    /** Server side **/

    fun login()

    fun logout()

}