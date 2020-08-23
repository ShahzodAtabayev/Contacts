package uz.xsoft.restapi.networks

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*
import uz.xsoft.restapi.data.local.room.entities.ContactData
import uz.xsoft.restapi.data.models.*

interface ContactApi {
    /**
     * 1. Get all contacts
     * */
    @GET("contact")
    fun getAllContactAsync(@Header("token") token: String): Deferred<Response<ResponseData<List<ContactData>>>>

    /**
     * 2. add new a contact
     * */
    @POST("contact")
    fun addContactAsync(@Body contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>

    /**
     * 3. remove a contact
     * */
    @HTTP(method = "DELETE", path = "contact", hasBody = true)
    fun removeContactAsync(@Body contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>

    /**
     * 3. update a contact
     * */
    @PUT("contact")
    fun updateAsync(@Body contactData: ContactData): Deferred<Response<ResponseData<ContactData>>>

    @POST("contact/login")
    fun loginAsync(@Body loginData: LoginData): Deferred<Response<ResponseData<String>>>

    @POST("contact/register")
    fun registerAsync(@Body registerData: RegisterData): Deferred<Response<ResponseData<String>>>

    @POST("contact/verify")
    fun verifyAsync(@Body smsCodeData: SmsCodeData): Deferred<Response<ResponseData<String>>>

    @POST("contact/reset")
    fun sendPhoneAsync(@Body resetPhoneData: ResetPhoneData): Deferred<Response<ResponseData<String>>>

    @POST("contact/password")
    fun sendChangePasswordAsync(@Body resetPasswordData: ResetPasswordData): Deferred<Response<ResponseData<String>>>

}