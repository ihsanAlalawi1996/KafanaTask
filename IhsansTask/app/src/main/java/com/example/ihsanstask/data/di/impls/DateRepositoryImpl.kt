package com.example.ihsanstask.data.di.impls

import com.example.ihsanstask.data.endpoints.Endpoints
import com.example.ihsanstask.data.models.BaseModel
import com.example.ihsanstask.data.repositories.DateRepository
import com.example.ihsanstask.utils.DataSource
import com.example.ihsanstask.utils.Results
import com.example.ihsanstask.utils.resultFlowData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(
    private val endPoint: Endpoints,
    private val remoteDataSource: DataSource
) : DateRepository {

    override suspend fun getHijriDate(date: String): Flow<Results<BaseModel?>> =
        resultFlowData(
            networkCall = {
                remoteDataSource.getResult {
                    endPoint.getScheduleMeeting(date)
                }
            }
        )
}
