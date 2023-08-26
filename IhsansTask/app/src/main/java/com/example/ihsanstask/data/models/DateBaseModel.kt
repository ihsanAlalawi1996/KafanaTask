package com.example.ihsanstask.data.models

import com.google.gson.annotations.SerializedName

data class DateBaseModel(
    @SerializedName("hijri") var hijri: DateModel? = DateModel(),
    @SerializedName("gregorian") var gregorian: DateModel? = DateModel()

)
