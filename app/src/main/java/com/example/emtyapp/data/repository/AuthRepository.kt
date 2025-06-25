package com.example.emtyapp.data.repository

import com.example.emtyapp.data.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser? get() = firebaseAuth.currentUser

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(user: User, password: String): Result<Unit> {
        return try {
            // Create auth user first
            val authResult = firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()

            // Then create user document in Firestore
            authResult.user?.let { firebaseUser ->
                val newUser = user.copy(uid = firebaseUser.uid)
                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(newUser)
                    .await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}