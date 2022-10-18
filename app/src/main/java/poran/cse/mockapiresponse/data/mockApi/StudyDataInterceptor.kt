package poran.cse.mockapiresponse.data.mockApi

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import poran.cse.mockapiresponse.data.MockResponseFileReader

class StudyDataInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        return if (chain.request().url.toUri().toString().endsWith("lessons")) {
            val responseString = MockResponseFileReader("study_response_200.json").content

            chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_1_0)
                .message(responseString)
                .body(
                    responseString
                    .toByteArray()
                        .toResponseBody(
                            "application/json".toMediaTypeOrNull()
                        )
                )
                .addHeader("content-type", "application/json")
                .build()

        } else {
            chain.proceed(chain.request())
        }
    }
}