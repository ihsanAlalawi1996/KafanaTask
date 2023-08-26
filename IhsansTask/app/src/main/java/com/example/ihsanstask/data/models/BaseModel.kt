package com.example.ihsanstask.data.models

import com.google.gson.annotations.SerializedName

data class BaseModel(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: DateBaseModel? = DateBaseModel()
)
