package shaileedemo.project.data

import shaileedemo.project.data.models.CompanyNewsServerModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CompanyNewsServerModelTest {

    @Test
    fun `test  CompanyNewsServerModel properties`() {
        // Create an instance of  CompanyNewsServerModel
        val companyNewsServerModel =  CompanyNewsServerModel(
            	id = 1,
            	name = "Test apple.com",
            	source = "Internet",
 	       	author = "Jennifer",
 	        title = "His Majesty King Charles III visits Apple’s U.K. headquarters",
	        description = "Tim Cook welcomes His Majesty King Charles III to Battersea Power Station, Apple’s U.K. headquarters in London",
 	        url = "https://www.apple.com/newsroom/topics/company-news/",
 	        urlToImage = "https://picsum.photos/200/300",
            	publishedAt = "2024-12-12T12:00:00Z",
            	content = "On Thursday, December 12, His Majesty King Charles III visited Apple’s Battersea office in London to meet Apple employees, 			members of the British creative community, and students who have learned vital new skills like coding from educational programs supported by 		Apple and U.K.-based nonprofit The King’s Trust."
        )

        // Verify the properties
    	assert Equals(1, companyNewsServerModel.id)
    	assertEquals("Test apple.com", companyNewsServerModel.name)
 	assertEquals("Internet", companyNewsServerModel.source)
 	assertEquals("Jennifer", companyNewsServerModel.author)
 	assertEquals("His Majesty King Charles III visits Apple’s U.K. headquarters", companyNewsServerModel.title)
 	assertEquals("Tim Cook welcomes His Majesty King Charles III to Battersea Power Station, Apple’s U.K. headquarters in London", companyNewsServerModel.description)
 	assertEquals("https://www.apple.com/newsroom/topics/company-news/", companyNewsServerModel.url)
 	assertEquals("https://picsum.photos/200/300", companyNewsServerModel.urlToImage)
    	assertEquals("2024-12-12T12:00:00Z", companyNewsServerModel.publishedAt)
    	assertEquals("On Thursday, December 12, His Majesty King Charles III visited Apple’s Battersea office in London to meet Apple employees, members of the British creative community, and students who have learned vital new skills 	like coding from educational programs supported by Apple and U.K.-based nonprofit The King’s Trust.", companyNewsServerModel.content)
  
    }
}
