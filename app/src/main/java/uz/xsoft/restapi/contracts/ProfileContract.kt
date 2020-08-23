package uz.xsoft.restapi.contracts


interface ProfileContract {
    interface Model {
       suspend fun logOut()
    }

    interface ViewModel {
        fun btLogOut()
        fun btEditProfile()
        fun btSettings()
        fun btContacts()
    }
}