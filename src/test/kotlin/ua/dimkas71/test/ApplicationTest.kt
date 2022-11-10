package ua.dimkas71.test

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.*


class ApplicationTest {

    @Test
    fun `if handler to nbu route exists`() = testApplication {
        val response = client.get("/currency/nbu")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `if handler to avg route exists`() = testApplication {
        val response = client.get("/currency/avg")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `if handler to black route exists`() = testApplication {
        val resp = client.get("/currency/black")
        assertEquals(HttpStatusCode.OK, resp.status)
    }

    @Test
    fun `if handler to help route exists`() = testApplication {
        val resp = client.get("/currency/help")
        assertEquals(HttpStatusCode.OK, resp.status)
    }

    @OptIn(InternalAPI::class)
    @Test
    fun `in route currency avg with future date an empty list should be returned`() = testApplication {
        val tomorrow = DateTimeFormatter.ISO_DATE.format(LocalDate.now().plusDays(1))
        val resp = client.get("/currency/avg/$tomorrow")
        assertEquals(HttpStatusCode.OK, resp.status)
        assertEquals(resp.content.totalBytesRead, 0)
    }
}