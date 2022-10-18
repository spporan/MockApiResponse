package poran.cse.mockapiresponse.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import poran.cse.mockapiresponse.data.dto.MoreStudyItem
import poran.cse.mockapiresponse.data.dto.StudyMore
import poran.cse.mockapiresponse.data.mockApi.ApiProvider
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class RequestViewModelTest {


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var dataSource: RemoteDataSource

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start()
        dataSource = RemoteDataSource(ApiProvider.getApiService())
    }

    @Test
    fun `read sample success json file`(){
        val reader = MockResponseFileReader("study_response_200.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch details and check response Code 200 returned`(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("study_response_200.json").content)
        mockWebServer.enqueue(response)
        // Act
       runBlocking {
           val  actualResponse = dataSource.getStudyData()
           // Assert
           assertEquals(response.toString().contains("200"), actualResponse.code().toString().contains("200"))
       }

    }

    @Test
    fun `fetch details and check response success returned`(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("study_response_200.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        // Act

        runBlocking {
            val  actualResponse = dataSource.getStudyData()

            assertEquals(mockResponse?.let { `parse mocked JSON response`(it) }, actualResponse.body()?.lessons)

        }
        // Assert
    }

    private fun `parse mocked JSON response`(mockResponse: String): List<MoreStudyItem?>? {
        val reader = Gson().fromJson(mockResponse, StudyMore::class.java)
        return reader.lessons
    }

    @Test
    fun `fetch details for failed response 400 returned`(){
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader("failed_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        runBlocking {
            val  actualResponse = dataSource.getStudyData()
            // Assert
            assertEquals(response.toString().contains("400"),actualResponse.toString().contains("400"))
        }

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}