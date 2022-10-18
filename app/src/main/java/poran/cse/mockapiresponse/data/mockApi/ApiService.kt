package poran.cse.mockapiresponse.data.mockApi

import poran.cse.mockapiresponse.data.dto.StudyMore
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("lessons")
    suspend fun getStudyData(@Query("page") page: Int): Response<StudyMore>
}