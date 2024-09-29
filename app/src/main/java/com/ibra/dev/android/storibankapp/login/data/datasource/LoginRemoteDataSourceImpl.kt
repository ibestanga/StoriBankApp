package com.ibra.dev.android.storibankapp.login.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRemoteDataSourceImpl(
    private val database: FirebaseFirestore
) : LoginRemoteDataSource {
    override suspend fun getCredentials(email: String): Flow<String> = flow {
        Log.i(TAG, "getCredentials: isConnectedToFirestore --> ${isConnectedToFirestore()}")
        database.collection(CLIENTS_COLLECTION).document(email)
            .set(
                hashMapOf(
                    PASSWORD_FIELD to "12345",
                    "name" to "ibra"
                )
            )

        emit("hola $email")
    }

    suspend fun isConnectedToFirestore(): Boolean = withContext(Dispatchers.IO) {
        try {
            val result = database.enableNetwork().await()
            database.disableNetwork().await() // Volvemos al estado anterior para no afectar otras operaciones
            true
        } catch (e: Exception) {
            false
        }
    }


    companion object {
        const val TAG = "LoginRemoteDataSourceImpl"
        const val CLIENTS_COLLECTION = "clients"
        const val PASSWORD_FIELD = "password"
    }
}