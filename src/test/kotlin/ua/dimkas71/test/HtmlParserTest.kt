package ua.dimkas71.test

import org.junit.jupiter.api.BeforeAll
import ua.dimkas71.AvgHtmlParser
import ua.dimkas71.BlackHtmlParser
import ua.dimkas71.NbuHtmlParser
import java.nio.file.Path
import kotlin.test.*

class HtmlParserTest {

    @Test
    fun `if content doesn't present NBU parser should return emptyList`() {
        val content = ""
        assertEquals(emptyList<String>(),  NbuHtmlParser.parse(content))
    }

    @Test
    fun `if content doesn't present Avg parser should return emptyList`() {
        val content = ""
        assertEquals(emptyList<String>(),  AvgHtmlParser.parse(content))
    }

    @Test
    fun `if content doesn't present Black parser should return emptyList`() {
        val content = ""
        assertEquals(emptyList<String>(),  BlackHtmlParser.parse(content))
    }

    @Test
    fun `if content is not empty and doesn't contain correct html page NBU parser should return emptyList`() {
        val content = """
            <html>
                <body>
                <table>
                </table>
                </body>
            </html>
        """.trimIndent()
        assertEquals(emptyList<String>(),  NbuHtmlParser.parse(content))
    }

    @Test
    fun `if content is not empty and doesn't present Avg parser should return emptyList`() {
        val content = """
            <html>
                <body>
                <table>
                </table>
                </body>
            </html>
        """.trimIndent()
        assertEquals(emptyList<String>(),  AvgHtmlParser.parse(content))
    }

    @Test
    fun `if content is not empty and doesn't present Black parser should return emptyList`() {
        val content = """
            <html>
                <body>
                <table>
                </table>
                </body>
            </html>
        """.trimIndent()
        assertEquals(emptyList<String>(),  BlackHtmlParser.parse(content))
    }


    @Test
    fun `for nbu and the First set of data it should be correct a piece of data`() {
        val actualList = NbuHtmlParser.parse(contentFirst)
        val expected = listOf<String>("36.5686", "36.3474", "7.726", "42.2312", "36.8208")
        assertEquals(expected, actualList)
    }

    @Test
    fun `for black and the First set of data it should be correct a piece of data`() {
        val actualList = BlackHtmlParser.parse(contentFirst)
        val expected = listOf<String>("40.430:40.600", "39.750:40.000", "8.350:8.550", "45.000:46.000", "40.000:40.950")
        assertEquals(expected, actualList)
    }

    @Test
    fun `for avg and the First set of data it should be correct a piece of data`() {
        val actualList = AvgHtmlParser.parse(contentFirst)
        val expectedList = listOf(
            "39.800:0.1500:0.2000:40.500",
            "38.600:0.1000:0.0000:39.500",
            "8.000:0.0000:-0.0200:8.380",
            "43.500:0.5000:0.1800:46.500",
            "38.000:0.0000:0.0500:40.550")
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `for nbu and the Second set of data it should be correct a piece of data`() {
        val actualList = NbuHtmlParser.parse(contentSecond)
        val expected = listOf<String>("36.5686", "35.9817", "7.6839", "41.1744", "36.5084")
        assertEquals(expected, actualList)
    }

    @Test
    fun `for black and the Second set of data it should be correct a piece of data`() {
        val actualList = BlackHtmlParser.parse(contentSecond)
        val expected = listOf<String>("40.423:40.600", "39.750:40.000", "8.350:8.548", "45.000:46.000", "40.000:40.967")
        assertEquals(expected, actualList)
    }

    @Test
    fun `for avg and the Second set of data it should be correct a piece of data`() {
        val actualList = AvgHtmlParser.parse(contentSecond)
        val expectedList = listOf(
            "40.000:0.0000:0.0500:40.550",
            "38.750:0.1250:0.2000:39.700",
            "8.000:0.1000:0.0550:8.465",
            "43.000:0.5000:-0.0500:45.700",
            "38.000:0.5000:0.2000:40.500")
        assertEquals(expectedList, actualList)
    }


    @Test
    fun `for nbu and the group set of data on days in future it should be correct a piece of data`() {
        val actualList = NbuHtmlParser.parse(contentAvgWithoutData)
        val expected = listOf<String>("36.5686", "36.7496", "7.8185", "41.8912", "37.2124")
        assertEquals(expected, actualList)
    }

    @Test
    fun `for black and the group set of data on days in future it should be correct a piece of data`() {
        val actualList = BlackHtmlParser.parse(contentAvgWithoutData)
        val expected = listOf<String>("40.400:40.590", "39.700:39.950", "8.350:8.535", "45.000:46.000", "40.000:40.950")
        assertEquals(expected, actualList)
    }

    @Test
    fun `for avg and the group set of data on days in future it should be correct a piece of data with dash symbols`() {
        val actualList = AvgHtmlParser.parse(contentAvgWithoutData)
        val expectedList = listOf(
            "-:-",
            "-:-",
            "-:-",
            "-:-",
            "-:-")
        assertEquals(expectedList, actualList)
    }


    companion object {

        private lateinit var contentFirst: String
        private lateinit var contentSecond: String
        private lateinit var contentAvgWithoutData: String

        @BeforeAll
        @JvmStatic
        fun setUp() {
            contentFirst = readPathAsText(
                Path.of(
                    "src",
                    "test",
                    "resources",
                    "2022-11-02.test"
                )
            )
            contentSecond = readPathAsText(
                Path.of(
                    "src",
                    "test",
                    "resources",
                    "2022-11-07.test"
                )
            )
            contentAvgWithoutData = readPathAsText(
                Path.of(
                    "src",
                    "test",
                    "resources",
                    "2022-11-10.test"
                )
            )
        }
    }
}