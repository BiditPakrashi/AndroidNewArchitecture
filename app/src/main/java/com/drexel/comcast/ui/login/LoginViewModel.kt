package com.drexel.comcast.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexel.model.FilmsRepository
import com.drexel.model.response.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: FilmsRepository = FilmsRepository()) : ViewModel() {

    val isSuccessLoading = mutableStateOf(value = false)
    val imageErrorAuth = mutableStateOf(value = false)
    val progressBar = mutableStateOf(value = false)
    val authtoken = mutableStateOf(value = "")
    var isSuccessful = true
    private val loginRequestLiveData = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressBar.value = true
                val token = loginRepo(email+password)
                if (token != null) {
                    delay(1500L)
                    isSuccessLoading.value = true
                    authtoken.value = token.token
                    Log.d("Logging", "Response TokenDto: $token")
                    }
                 else {
                        isSuccessful = false
                        imageErrorAuth.value = true
                        delay(1500L)
                        imageErrorAuth.value = false


                }

                loginRequestLiveData.postValue(isSuccessful)
                progressBar.value = false
            } catch (e: Exception) {
                Log.d("Logging", "Error Authentication", e)
                progressBar.value = false
            }
        }
    }

    private suspend fun loginRepo(userpassword:String): Token {
        return  repository.login(userpassword)
    }
}
