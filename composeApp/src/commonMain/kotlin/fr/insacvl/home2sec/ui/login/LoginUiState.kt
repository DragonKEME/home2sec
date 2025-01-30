package fr.insacvl.home2sec.ui.login

sealed interface LoginUiState {
    data class FormState(
        val isLoginError: Boolean
    ) : LoginUiState

    data object LoadingState : LoginUiState
}