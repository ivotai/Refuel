package com.unicorn.refuel.data.api

import com.unicorn.refuel.data.model.CarFuel
import com.unicorn.refuel.data.model.param.CarFuelListParam
import com.unicorn.refuel.data.model.param.UserLoginParam
import com.unicorn.refuel.data.model.base.PageRequest
import com.unicorn.refuel.data.model.response.LoggedUser
import com.unicorn.refuel.data.model.base.BaseResponse
import com.unicorn.refuel.data.model.base.PageResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SimpleApi {

    @POST("Authorization/UserLogin")
    fun login(@Body userLoginParam: UserLoginParam): Single<BaseResponse<LoggedUser>>

    @POST("Maintenance/CarFuelList")
    fun getCarFuelList(@Body pageRequest: PageRequest<CarFuelListParam>): Single<PageResponse<CarFuel>>

}