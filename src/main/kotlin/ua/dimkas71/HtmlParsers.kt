package ua.dimkas71

import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.RuntimeException

interface HtmlParser {
    fun parse(content: String): List<String>

    companion object {
        const val delimiter = ":"
    }
}
private fun selectTable(content: String): Elements {
    val cssQueryToSearchTable = ".table-response.mfm-table.mfcur-table-lg.mfcur-table-lg-currency.has-no-tfoot"
    val tbodyText = "tbody"
    val document = Jsoup.parse(content)

    return document.body().select(cssQueryToSearchTable).select(tbodyText)
}

object CurrencyParser : HtmlParser {
    private const val CSS_CLASS_SEARCH =  ".mfcur-table-cur"

    override fun parse(content: String): List<String> {
        val tableSelector = selectTable(content)

        return tableSelector.select(CSS_CLASS_SEARCH).map {
            it.text()
        }
    }

}

object NbuHtmlParser : HtmlParser {

    private const val TABLE_DATA_TITLE_ATTRIBUTE = "td[data-title]"
    private const val SEARCH_TEXT_NBU = "НБУ"
    private const val DATA_TITLE_TEXT = "data-title"

    override fun parse(content: String): List<String> {
        val tableSelector = selectTable(content)
        return tableSelector.select(TABLE_DATA_TITLE_ATTRIBUTE).filter { element ->
            element.attr(DATA_TITLE_TEXT).contains(SEARCH_TEXT_NBU)
        }.map {
            it.text().split(" ")[0]
        }
    }

}

object BlackHtmlParser : HtmlParser {

    private const val CLASS_TEXT_NO_WRAP_WITH_ATTRIBUTES = "td.mfm-text-nowrap[data-title]"
    private const val DATA_TITLE_TEXT = "data-title"
    private const val BLACK_MARKET_SEARCH_TEXT = "Чорн"

    override fun parse(content: String): List<String> {

        return selectTable(content).select(CLASS_TEXT_NO_WRAP_WITH_ATTRIBUTES).filter { element ->
            element.attr(DATA_TITLE_TEXT).contains(BLACK_MARKET_SEARCH_TEXT)
        }.map { element ->
            element.text().split("/").filter { it.isNotEmpty() }.map {
                it.replace(",", ".").trim()
            }
        }.map {
            it.joinToString(HtmlParser.delimiter)
        }.toList()
    }

}

object AvgHtmlParser: HtmlParser {

    private const val CLASS_TEXT_NO_WRAP_WITH_ATTRIBUTES = "td.mfm-text-nowrap[data-title]"
    private const val DATA_TITLE_TEXT = "data-title"
    private const val AVG_SEARCH_TEXT = "Середн"

    override fun parse(content: String): List<String> {
        return selectTable(content).select(CLASS_TEXT_NO_WRAP_WITH_ATTRIBUTES).filter { element ->
            element.attr(DATA_TITLE_TEXT).contains(AVG_SEARCH_TEXT)
        }.map { element ->
                element.text().split("/", " ").filter { it.isNotEmpty()}.filter { !it.isEmpty() }
                    .map {
                        it.replace(",", ".").trim()
                    }
        }.map {
            it.joinToString(HtmlParser.delimiter)
        }
    }

}
class HtmlParserException: RuntimeException()