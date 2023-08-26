package com.example.ihsanstask.data.di.impls

import android.content.Context
import com.example.ihsanstask.utils.DataSource
import com.example.ihsanstask.utils.Results
import com.example.ihsanstask.utils.debugPrint
import com.example.ihsanstask.utils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : DataSource {

    override suspend fun <T> getResult(call: suspend () -> Response<T>): Results<T> =
        withContext(Dispatchers.IO) {
            if (context.isNetworkAvailable())
                try {
                    val response = call()

                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) Results.success(body, response.code())
                        else error(response)
                    } else error(response)
                } catch (e: Exception) {
                    error(e.message ?: e.toString())
                }
            else Results.error<T>(message = "Internet error").apply {
                "Network call has failed for a following reason: Internet error".debugPrint()
            }
        }

    private fun <T> error(message: String): Results<T> =
        Results.error<T>(message = "Network call has failed for a following reason: $message")
            .apply {
                "Network call has failed for a following reason: $message".debugPrint()
            }

    private fun <T> error(error: Response<T>): Results<T> {
        var data: String = error.errorBody()?.string() ?: ""
        try {
            val jObjError = JSONObject(data)
            data = jObjError.getString("message")
            val errors = jObjError.getString("errors")
            "s;ldkldklslsd: $errors".debugPrint()
        } catch (e: java.lang.Exception) {
            "RemoteDataSourceImpl: error(): $e".debugPrint()
        }
        return Results.error<T>(code = error.code(), message = data).apply {
            "Network call has failed for a following code and reason: ${error.code()}, ${
                error.errorBody().toString()
            }".debugPrint()
        }
    }
}
