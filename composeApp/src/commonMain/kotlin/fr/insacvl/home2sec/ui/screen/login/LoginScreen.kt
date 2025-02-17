package fr.insacvl.home2sec.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import fr.insacvl.home2sec.data.DeviceRepository

@Composable
fun LoginScreen(navHostController: NavHostController, repository: DeviceRepository, modifier: Modifier = Modifier){
    val loginViewModel: LoginViewModel = viewModel { LoginViewModel (repository, navHostController) }
    Surface(modifier = modifier.fillMaxSize()) {
        when (val uistate = loginViewModel.uiState) {
            is LoginUiState.FormState -> LoginForm(loginViewModel, uistate, modifier.fillMaxSize().padding(10.dp))
            is LoginUiState.LoadingState -> {}
        }
    }
}


@Composable
fun LoginForm(viewModel: LoginViewModel, uiState: LoginUiState.FormState, modifier: Modifier = Modifier) {
    val isError = uiState.isLoginError
    val onSubmit = { viewModel.onSubmit() }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = modifier
    ) {
        OutlinedTextField(
            value = viewModel.usernameFormText,
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            onValueChange = { viewModel.set_username(it) },
            label = { Text("username") },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.passwordFormText,
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            onValueChange = { viewModel.set_password(it) },
            label = { Text("Password") },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = { onSubmit() }
            ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Button(
            onClick = onSubmit,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Login")
        }
    }
}