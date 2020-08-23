package uz.xsoft.restapi.data.models

data class RegisterData(
    val phoneNumber: String?,
    val lastName: String?,
    val firstName: String?,
    val password: String
)