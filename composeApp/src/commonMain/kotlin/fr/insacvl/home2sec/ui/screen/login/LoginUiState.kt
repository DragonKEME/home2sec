package fr.insacvl.home2sec.ui.screen.login

sealed interface LoginUiState {
    data class FormState(
        val isLoginError: Boolean
    ) : LoginUiState

    data object LoadingState : LoginUiState
}