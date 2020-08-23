package uz.xsoft.restapi.data.models

data class ResetPasswordData(
    val phoneNumber: String,
    val password: String,
    val code: String
)