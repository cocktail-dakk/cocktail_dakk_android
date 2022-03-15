package com.umcapplunching.cocktail_dakk.ui.start.Service

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue

//나중에 건드리기 인터셉터 하면서 해야할 것 같은데 못하겠음
data class ResponseWrapper<T>(
    @SerializedName("code")var code: Int,
    @SerializedName("isSuccess")var isSuccess: Boolean,
    @SerializedName("message")var message: String,
    @SerializedName("result")var result: @RawValue T? = null
)