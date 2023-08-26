package com.example.ihsanstask.data.repositories

import com.example.ihsanstask.data.models.BaseModel
import com.example.ihsanstask.utils.Results
import kotlinx.coroutines.flow.Flow

interface DateRepository {

    suspend fun getHijriDate(date: String): Flow<Results<BaseModel?>>

}
