package ru.lionzxy.printbox.data.api

import io.reactivex.Completable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    @FormUrlEncoded
    fun login(@Field("username") login: String, @Field("password") password: String): Completable

    @POST("/login")
    @FormUrlEncoded
    fun register(@Field("username") login: String, @Field("email") email: String,
                 @Field("password1") password: String, @Field("password2") repeatPassword: String): Completable
}