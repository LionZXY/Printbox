package ru.lionzxy.printbox.data.api

import io.reactivex.Completable
import retrofit2.http.*

interface AuthApi {

    @POST("/login/?next=/printing/")
    @FormUrlEncoded
    fun login(@Field("username") login: String, @Field("password") password: String): Completable

    @POST("/login/?next=/printing/")
    @FormUrlEncoded
    fun register(@Field("username") login: String, @Field("email") email: String,
                 @Field("password1") password: String, @Field("password2") repeatPassword: String): Completable

    @GET("/complete/vk-oauth2/")
    fun vkLogin(@QueryMap params: Map<String, String>): Completable
}