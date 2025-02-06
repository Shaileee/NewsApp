package shaileedemo.project.presentation.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import shaileedemo.project.domain.model.CompanyNewsRepoModel
import shaileedemo.project.domain.repository.CompanyNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CompanyNewsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var companyNewsRepository: CompanyNewsRepository

    private lateinit var companyNewsViewModel: CompanyNewsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
)

        val mockCompanyNews = listOf(
CompanyNews(1, "Internet", "Jennifer", "His Majesty King Charles III visits Apple’s U.K. headquarters", "Tim    Cook welcomes His Majesty King Charles III to Battersea Power Station, Apple’s U.K. headquarters in London","https://www.apple.com/newsroom/topics/company-news/", "https://picsum.photos/200/300","2024-10-12T14:06:02Z","On Thursday, December 12, His Majesty King Charles III visited Apple’s Battersea office in London to meet Apple employees, members of the British creative community, and students who have learned vital new skills like coding from educational programs supported by Apple and U.K.-based nonprofit The King’s Trust."),
            CompanyNews(2, "ABC", "David", "The AI opportunity", "Google I/O 2024: An I/O for a new generation","https://blog.google/inside-google/company-announcements/", "https://picsum.photos/200/300","2024-12-12T14:06:02Z","We want everyone to benefit from what Gemini can do. So we’ve worked quickly to share these advances with all of you. Today more than 1.5 million developers use Gemini models across our tools. You’re using it to debug code, get new insights, and build the next generation of AI applications")
        )

        Mockito.`when`(companyNewsRepository.getCompanyNews()).thenReturn(flow { emit(mockCompanyNews) })

       //companyNewsViewModel = CompanyNewsViewModel(companyNewsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetCompanyNews() = testScope.runTest {
        val job = launch {
            companyNewsViewModel.companyNews.collect { companyNews ->
                Assert.assertEquals(2, companyNews.size)
                Assert.assertEquals("Internet", newsArticles[0].source)
                Assert.assertEquals("ABC", newsArticles[1].source)
            }
        }
        job.cancel()
    }

    @Test
    fun testLoadingState() = testScope.runTest {
        val loadingStates = mutableListOf<Boolean>()
        val job = launch {
            companyNewsViewModel.isLoading.collect { isLoading ->
                loadingStates.add(isLoading)
            }
        }

        // Allow some time for loading states to be updated
        advanceUntilIdle()

        // Initially, loading should be true, then false after loading
        Assert.assertEquals(listOf(false), loadingStates)

        job.cancel()
    }
}
