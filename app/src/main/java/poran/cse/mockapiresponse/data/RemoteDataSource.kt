package poran.cse.mockapiresponse.data

import okhttp3.ResponseBody
import poran.cse.mockapiresponse.data.dto.MoreStudyItem
import poran.cse.mockapiresponse.data.dto.StudyMore
import poran.cse.mockapiresponse.data.dto.toStudyItems
import poran.cse.mockapiresponse.data.mockApi.ApiService
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getStudyData() = apiService.getStudyData(page = 1)
}