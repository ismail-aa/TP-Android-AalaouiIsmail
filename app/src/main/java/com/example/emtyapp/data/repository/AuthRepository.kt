package com.example.emtyapp.data.repository

import com.example.emtyapp.data.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore // Changed from private to public
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

    suspend fun getUsers(): List<User> {
        return try {
            firestore.collection("users").get().await().toObjects(User::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try {
            firestore.collection("users")
                .document(user.uid)
                .set(user)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteUser(uid: String): Boolean {
        return try {
            // First delete auth user
            firebaseAuth.currentUser?.let { currentUser ->
                if (currentUser.uid == uid) {
                    currentUser.delete().await()
                } else {
                    // Admin deleting another user - requires admin privileges
                    // You might need to use Firebase Admin SDK on your backend
                    // for this functionality
                }
            }
            // Then delete user document
            firestore.collection("users").document(uid).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}