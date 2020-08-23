package uz.xsoft.restapi.utils

import android.text.Editable
import android.text.TextWatcher

class TextWatchers private constructor() {
    companion object {
        var name = ""
        var surName = ""
        var phone = ""
        var forgotPasswordPhone = ""
        var codeResetPassword = ""
        var password = ""
        var resetPassword = ""
        var repeatPassword = ""
        var codeVerification = ""
        var login = ""
        var loginPassword = ""
        var INSTANCE: TextWatchers? = null
        fun get(): TextWatchers {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            INSTANCE =
                TextWatchers()
            return INSTANCE!!
        }
    }


    val nameTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            name = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val surNameTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            surName = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val phoneTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            phone = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val passwordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            password = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val repeatPasswordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            repeatPassword = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val codeVerificationTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            codeVerification = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val loginTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            login = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val loginPasswordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            loginPassword = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val phoneForgotTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            forgotPasswordPhone = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val codeForgotTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            codeResetPassword = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val resetPasswordForgotTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            resetPassword = editable.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
}
