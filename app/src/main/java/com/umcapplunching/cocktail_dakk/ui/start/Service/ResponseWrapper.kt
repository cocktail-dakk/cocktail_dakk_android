package com.umcapplunching.cocktail_dakk.ui.start.Service

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class ResponseWrapper<T>(
    @SerializedName("code")var code: Int,
    @SerializedName("isSuccess")var isSuccess: Boolean,
    @SerializedName("message")var message: String,
    @SerializedName("result")var result: @RawValue T? = null
)