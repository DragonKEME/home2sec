package fr.insacvl.home2sec.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import fr.insacvl.home2sec.data.DeviceRepository
import fr.insacvl.home2sec.models.errors.InternalError
import fr.insacvl.home2sec.ui.Page
import kotlinx.coroutines.launch

class LoginViewModel(
    private val deviceRepository: DeviceRepository,
    private val navHostController: NavHostController
): ViewModel() {

    private val maxSize = 60

    var uiState: LoginUiState by mutableStateOf(LoginUiState.FormState(false))
        private set

    var usernameFormText by mutableStateOf("")
        private set

    var passwordFormText by mutableStateOf("")
        private set


    fun set_username(username: String){
        this.usernameFormText = if (username.length > maxSize){
            username.substring(0..maxSize)
        }else{
            username
        }
    }

    fun set_password(password: String){
        this.passwordFormText = if (password.length > maxSize){
            password.substring(0..maxSize)
        }else{
            password
        }
    }

    fun reset_login_form() {
        this.usernameFormText = ""
        this.passwordFormText = ""
    }

    fun onSubmit(){
        viewModelScope.launch {
            try {
                deviceRepository.login(usernameFormText, passwordFormText)
                navHostController.navigate(Page.List.route)
            } catch (_: InternalError) {
                reset_login_form()
                uiState = LoginUiState.FormState(true)
            }
        }
    }
}