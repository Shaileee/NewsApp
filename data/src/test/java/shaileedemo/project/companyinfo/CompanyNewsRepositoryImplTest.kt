package shaileedemo.project.companyinfo
import kotlinx.coroutines.runBlocking

import shaileedemo.project.data.models.CompanyNewsServerModel
import shaileedemo.project.data.models.RetrofitResult
import shaileedemo.project.data.remote.CompanyNewsRemoteApi
import shaileedemo.project.data.repository.CompanyNewsRepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import kotlin.test.assertEquals

class CompanyNewsRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var companyNewsRepository: CompanyNewsRepositoryImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        companyNewsRepository = CompanyNewsRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCompanyNews should return list of News Articles`() {
        // Given
        val mockCompanyNews = CompanyNewsServerModel(
            id = 1, source="Internet" author = "Jennifer",title="His Majesty King Charles III visits Apple’s U.K. headquarters", 
		description = "Tim Cook welcomes His Majesty King Charles III to Battersea Power Station, Apple’s U.K. headquarters in London", 
		url = "https://www.apple.com/newsroom/topics/company-news/" , urlToImage="https://picsum.photos/200/300", 
		publishedAt = "2025-12-12T14:06:02Z",content = "On Thursday, December 12, His Majesty King Charles III visited Apple’s Battersea office in London to meet Apple employees, members of the British creative community, and students who have learned vital new skills like coding from educational programs supported by Apple and U.K.-based nonprofit The King’s Trust.")

        val mockApiResponse = ApiResponse(listOf(mockCompanyNews))
        val mockResponseBody = Gson().toJson(mockApiResponse)
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(mockResponseBody))

        // When
        val flow = companyNewsRepository.getArticles()



        // Then
        runBlocking {
            flow.collect { newsArticles ->
                assertEquals(1, newsArticles.size)
                assertEquals(1, newsArticles[0].id)
                assertEquals("Internet", newsArticles[0].source)
                assertEquals("Jennifer", newsArticles[0].author)
	        assertEquals("His Majesty King Charles III visits Apple’s U.K. headquarters", newsArticles[0].title)
	        assertEquals("Tim Cook welcomes His Majesty King Charles III to Battersea Power Station, Apple’s U.K. headquarters in    London",newsArticles[0].descripion)
	        assertEquals("https://www.apple.com/newsroom/topics/company-news/", newsArticles[0].url)
 		assertEquals("https://picsum.photos/200/300", newsArticles[0].urlToImage)
		assertEquals("2024-12-12T14:06:02Z", newsArticles[0].publishedAt)
 		assertEquals("On Thursday, December 12, His Majesty King Charles III visited Apple’s Battersea office in London to meet Apple employees, members of the British creative community, and students who have learned vital new skills like coding from educational programs supported by Apple and U.K.-based nonprofit The King’s Trust.", newsArticles[0].content)
            }
        }
    }
}
