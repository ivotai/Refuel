package com.unicorn.refuel.data.api

import com.unicorn.refuel.data.model.Car
import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.base.BaseResponse
import com.unicorn.refuel.data.model.base.EncryptionRequest
import com.unicorn.refuel.data.model.base.PageResponse
import com.unicorn.refuel.data.model.param.UserLoginParam
import com.unicorn.refuel.data.model.response.LoggedUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<BaseResponse<LoggedUser>>

    @POST("Maintenance/CarFuelList")
    fun getCarFuelList(@Body encryptionRequest: EncryptionRequest): Single<PageResponse<CarFuel>>

    @POST("Maintenance/CarList")
    fun getCarlList(@Body encryptionRequest: EncryptionRequest): Single<PageResponse<Car>>

    @POST("Maintenance/CarFuelAdd")
    fun addCarFuel(@Body encryptionRequest: EncryptionRequest): Single<BaseResponse<CarFuel>>

    @POST("Maintenance/CarFuelUpdate")
    fun updateCarFuel(@Body encryptionRequest: EncryptionRequest): Single<BaseResponse<CarFuel>>

}