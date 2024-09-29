package com.ibra.dev.android.storibankapp.login.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.ibra.dev.android.storibankapp.login.data.contracts.LoginRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class LoginRemoteDataSourceImpl(
    private val database: FirebaseFirestore
) : LoginRemoteDataSource {
    override suspend fun getCredentials(email: String): Flow<String> = flow {
       val snapshot = database.collection(CLIENTS_COLLECTION).document(email).get().await()

        if (snapshot.exists()) {
            val password = snapshot.getString(PASSWORD_FIELD).orEmpty()
            emit(password)
        } else {
            throw Exception("User not found")
        }
    }

    companion object {
        const val CLIENTS_COLLECTION = "clients"
        const val PASSWORD_FIELD = "password"
    }
}