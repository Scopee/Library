package ru.pinguin.library.network.login

class LoginRemoteRepository(private val loginApi: LoginApi) {

    suspend fun login(username: String) = loginApi.login(username)
}