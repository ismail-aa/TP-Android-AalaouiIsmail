package com.example.emtyapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emtyapp.data.entities.User
import com.example.emtyapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.LoadUserData -> loadUserData()
            is ProfileIntent.UpdateUser -> updateUser(intent.user)
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val currentUser = authRepository.currentUser
                currentUser?.let { user ->
                    val userDoc = authRepository.firestore
                        .collection("users")
                        .document(user.uid)
                        .get()
                        .await()

                    userDoc.toObject(User::class.java)?.let {
                        _state.value = _state.value.copy(
                            user = it,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to load user data",
                    isLoading = false
                )
            }
        }
    }

    private fun updateUser(user: User) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                authRepository.firestore
                    .collection("users")
                    .document(user.uid)
                    .set(user)
                    .await()

                _state.value = _state.value.copy(
                    user = user,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to update user",
                    isLoading = false
                )
            }
        }
    }
}