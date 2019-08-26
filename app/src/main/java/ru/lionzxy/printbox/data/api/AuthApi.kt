package ru.lionzxy.printbox.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.lionzxy.printbox.data.model.User

interface AuthApi {

    @POST("/api/v1/user/login/")
    @FormUrlEncoded
    fun login(@Field("login") login: String, @Field("password") password: String): Completable

    @POST("/api/v1/user/register/")
    @FormUrlEncoded
    fun register(@Field("login") email: String,
                 @Field("password") password: String): Completable

    @GET("users/profile/")
    fun currentUser(): Single<User>
}