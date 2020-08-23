package uz.xsoft.restapi.data.models

data class ResponseData<T>(
    val status: String,
    val message: String = "Successful",
    val data: T? = null
)