package ua.dimkas71

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

lateinit var client: HttpClient

fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    environment.monitor.subscribe(ApplicationStarted) {
        client = HttpClient(CIO)
    }

    environment.monitor.unsubscribe(ApplicationStopped) {
        client.close()
    }
    install(StatusPages) {
        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respondText(text = "500: Internal server error${call.request}", status = status)
        }
    }
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/currency/help") {
            call.respondText(status = HttpStatusCode.OK) {
                """
                    Українською:
                     Сервіс для отримання курсів валют (EUR, USD, GBP, PLN) по відношенню до гривні(UAH)
                     на поточну дату, та на певну дату. Дата повинна вводитись в форматі ISO YYYY-MM-dd.
                     Наприклад: 2022-11-07(07.11.2022)
                     Дані отримуються із сайту Мінфіну України https://minfin.com.ua/ua/currency/
                     Підтримуються наступні курси: 
                        НБУ -> Приклад запиту: <external service ip address>/currency/nbu, <service ip>/currency/nbu/2022-11-07
                        Чорний ринок -> Приклад запиту: <external service ip address>/currency/black, <service ip>/currency/black/2022-11-07
                        Середньоденний -> Приклад запиту: <external service ip address>/currency/avg, <service ip>/currency/avg/2022-11-07
                        Приклад відповід у форматі JSON:
                        [
                            {
                                "ccy": "USD",
                                "base_ccy": "UAH",
                                "buy": 40.1,
                                "sell": 40.65,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "EUR",
                                "base_ccy": "UAH",
                                "buy": 39.3,
                                "sell": 40.2,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "PLN",
                                "base_ccy": "UAH",
                                "buy": 8.15,
                                "sell": 8.57,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "GBP",
                                "base_ccy": "UAH",
                                "buy": 43.1,
                                "sell": 46.33,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "CHF",
                                "base_ccy": "UAH",
                                "buy": 39.1,
                                "sell": 41.3,
                                "date": "2022-11-10"
                            }
                        ]
                        Опис обєкту що повертається: 
                            ccy-Код валюти,
                            base_ccy-гривня,
                            buy-курс покупки валюти,
                            sell-курс продажу валюти,
                            date-дата курсу
                     In English:
                        Service for receiving exchange rates (EUR, USD, GBP, PLN) in relation to the hryvna (UAH)
                     on the current date and on the specific date. The date must be entered in ISO format (YYYY-MM-dd).
                     For example: 2022-11-07
                     The data are obtained from the website of The Ministry of Finance of Ukraine: https://minfin.com.ua/ua/currency/
                     The following courses are supported: 
                        NBU(The National Bank of Ukraine) -> Sample request: <external service ip address>/currency/nbu, <service ip>/currency/nbu/2022-11-07
                        Black(Black market) -> Sample request: <external service ip address>/currency/black, <service ip>/currency/black/2022-11-07
                        Avg(Midday) -> Sample request: <external service ip address>/currency/avg, <service ip>/currency/avg/2022-11-07
                        An example of a response in JSON format:
                        [
                            {
                                "ccy": "USD",
                                "base_ccy": "UAH",
                                "buy": 40.1,
                                "sell": 40.65,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "EUR",
                                "base_ccy": "UAH",
                                "buy": 39.3,
                                "sell": 40.2,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "PLN",
                                "base_ccy": "UAH",
                                "buy": 8.15,
                                "sell": 8.57,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "GBP",
                                "base_ccy": "UAH",
                                "buy": 43.1,
                                "sell": 46.33,
                                "date": "2022-11-10"
                            },
                            {
                                "ccy": "CHF",
                                "base_ccy": "UAH",
                                "buy": 39.1,
                                "sell": 41.3,
                                "date": "2022-11-10"
                            }
                        ]
                        The description of the returned object: 
                            ccy-Currency code,
                            base_ccy-Ukrainian hryvna's code,
                            buy-buy currency purchase rate,
                            sell-rate of selling currency,
                            date-date of the course       
                """.trimIndent()
            }
        }

        get("/currency/nbu") {
            call.respond(currencyRatesFor(client, CurrencyRateType.NBU))
        }
        get("/currency/nbu/{date}") {
            try {
                val date = LocalDate.parse(call.parameters["date"])
                call.respond(currencyRatesFor(client, CurrencyRateType.NBU, date))
            } catch (ex: DateTimeParseException) {
                call.respond(
                    HttpStatusCode.NotFound, """
                    Page not found, parameter date should be in a format: YYYYY-MM-dd (ex. 2022-11-07)
                """.trimIndent()
                )
            }
        }
        get("/currency/avg") {
            call.respond(HttpStatusCode.OK, currencyRatesFor(client, CurrencyRateType.AVG))
        }
        get("/currency/avg/{date}") {
            try {
                val date = LocalDate.parse(call.parameters["date"])
                call.respond(HttpStatusCode.OK, currencyRatesFor(client, CurrencyRateType.AVG, date))
            } catch (ex: DateTimeParseException) {
                call.respond(
                    HttpStatusCode.NotFound, """
                    Page not found, parameter date should be in a format: YYYYY-MM-dd (ex. 2022-11-07)
                """.trimIndent()
                )
            }
        }
        get("/currency/black") {
            call.respond(currencyRatesFor(client, CurrencyRateType.BLACK))
        }
        get("/currency/black/{date}") {
            try {
                val date = LocalDate.parse(call.parameters["date"])
                call.respond(currencyRatesFor(client, CurrencyRateType.BLACK, date))
            } catch (ex: DateTimeParseException) {

                call.respond(
                    HttpStatusCode.NotFound, """
                    Page not found, parameter date should be in a format: YYYYY-MM-dd (ex. 2022-11-07)
                """.trimIndent()
                )
            }
        }
    }
}

private suspend fun currencyRatesFor(
    client: HttpClient,
    currType: CurrencyRateType,
    date: LocalDate? = null
): List<CurrencyRate> {

    val currencyUri = "https://minfin.com.ua/ua/currency/${date?.format(DateTimeFormatter.ISO_DATE) ?: ""}"

    val content = client.get(currencyUri).bodyAsText()

    val currencies = CurrencyParser.parse(content)

    val uah = "UAH"

    return when (currType) {
        CurrencyRateType.NBU -> {

            val rates = NbuHtmlParser.parse(content)

            currencies.zip(rates).map {
                CurrencyRate(
                    it.first,
                    uah,
                    it.second.toFloat(),
                    it.second.toFloat(),
                    date?.format(DateTimeFormatter.ISO_DATE) ?: LocalDate.now().format(
                        DateTimeFormatter.ISO_DATE
                    )
                )
            }
        }

        CurrencyRateType.AVG -> {

            val tmpStream = AvgHtmlParser.parse(content).filter { !it.contains("-:-") }.map {
                it.split(HtmlParser.delimiter)
            }
            when {
                tmpStream.isEmpty() -> emptyList()
                else -> {
                    val buySellPairs = tmpStream.map {
                        Pair(it[0].toFloat(), it[3].toFloat())
                    }

                    currencies.zip(buySellPairs).map {
                        CurrencyRate(
                            it.first,
                            uah,
                            it.second.first,
                            it.second.second,
                            date?.format(DateTimeFormatter.ISO_DATE) ?: LocalDate.now().format(
                                DateTimeFormatter.ISO_DATE
                            )
                        )
                    }
                }
            }
        }

        CurrencyRateType.BLACK -> {

            val buySellPairs = BlackHtmlParser.parse(content).map {
                it.split(HtmlParser.delimiter)
            }.map {
                Pair(it[0].toFloat(), it[0].toFloat())
            }

            currencies.zip(buySellPairs).map {
                CurrencyRate(
                    it.first,
                    uah,
                    it.second.first,
                    it.second.second,
                    date?.format(DateTimeFormatter.ISO_DATE) ?: LocalDate.now().format(
                        DateTimeFormatter.ISO_DATE
                    )
                )
            }
        }
    }
}



