package ua.dimkas71

import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val htmlContent = """
        <table class="table-response mfm-table mfcur-table-lg mfcur-table-lg-currency has-no-tfoot">
         <thead>
          <tr>
           <th class="mfcur-table-cur mfm-text-grey">КУРС ДO ГРИВНІ</th>
           <th><span class="mfcur-thead-title"> <a href="/ua/currency/banks/">Середній курс в банках</a> <i class="icon-help-circled-alt mfm-tooltip mfm-tooltip-top"> <span class="mfm-tooltip-body"> Середній курс валюти в банках України 
               <hr> </span> </i> </span>
            <div class="mfm-text-nowrap"><small class="mfm-txt-grey">Купівля / Продаж</small>
            </div></th>
           <th><span class="mfcur-thead-title"><a href="/ua/currency/nbu/">НБУ</a></span></th>
           <th><span class="mfcur-thead-title"> <a href="/ua/currency/auction/">Чорний ринок</a> <i class="icon-help-circled-alt mfm-tooltip mfm-tooltip-top"> <span class="mfm-tooltip-body"> Приватні оголошення про обмін валют в Україні<br> <a href="/ua/currency/auction/usd/buy/">Перейти</a>
               <hr> </span> </i> </span>
            <div class="mfm-text-nowrap "><small class="mfm-txt-grey">Купують / Продають</small>
            </div></th>
          </tr>
         </thead>
         <tbody>
          <tr>
           <td class="mfcur-table-cur" data-title="Курс гривні"><a href="/ua/currency/usd/">USD</a></td>
           <td class="mfm-text-nowrap" data-title="Середній курс в банках" data-small="Купівля / Продаж">40,000 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-open">0.0000</span> / <span class="mfm-hover-show mfm-table-trend icon-up-open">0.0500</span> </span> 40,550</td>
           <td data-title="НБУ"><span class="mfcur-nbu-full-wrap"> 36.<span class="mfm-text-grey">5686</span> <br> <span class="mfcur-nbu-full mfm-text-grey mfm-text-nowrap mfm-hover-show icon-open"> 0</span> </span></td>
           <td class="mfm-text-nowrap" data-title="Чорний ринок" data-small="Купівля / Продаж">40,450 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-open"></span> / <span class="mfm-hover-show mfm-table-trend icon-open"></span> </span> 40,600</td>
          </tr>
          <tr>
           <td class="mfcur-table-cur" data-title="Курс гривні"><a href="/ua/currency/eur/">EUR</a></td>
           <td class="mfm-text-nowrap" data-title="Середній курс в банках" data-small="Купівля / Продаж">38,750 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-up-open">0.1250</span> / <span class="mfm-hover-show mfm-table-trend icon-up-open">0.2000</span> </span> 39,700</td>
           <td data-title="НБУ"><span class="mfcur-nbu-full-wrap"> 35.<span class="mfm-text-grey">9817</span> <br> <span class="mfcur-nbu-full mfm-text-grey mfm-text-nowrap mfm-hover-show icon-open"> 0</span> </span></td>
           <td class="mfm-text-nowrap" data-title="Чорний ринок" data-small="Купівля / Продаж">39,500 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-open"></span> / <span class="mfm-hover-show mfm-table-trend icon-open"></span> </span> 39,800</td>
          </tr>
          <tr>
           <td class="mfcur-table-cur" data-title="Курс гривні"><a href="/ua/currency/pln/">PLN</a></td>
           <td class="mfm-text-nowrap" data-title="Середній курс в банках" data-small="Купівля / Продаж">8,000 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-up-open">0.1000</span> / <span class="mfm-hover-show mfm-table-trend icon-up-open">0.0550</span> </span> 8,465</td>
           <td data-title="НБУ"><span class="mfcur-nbu-full-wrap"> 7.<span class="mfm-text-grey">6839</span> <br> <span class="mfcur-nbu-full mfm-text-grey mfm-text-nowrap mfm-hover-show icon-open"> 0</span> </span></td>
           <td class="mfm-text-nowrap" data-title="Чорний ринок" data-small="Купівля / Продаж">8,250 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-open"></span> / <span class="mfm-hover-show mfm-table-trend icon-open"></span> </span> 8,490</td>
          </tr>
          <tr>
           <td class="mfcur-table-cur" data-title="Курс гривні"><a href="/ua/currency/gbp/">GBP</a></td>
           <td class="mfm-text-nowrap" data-title="Середній курс в банках" data-small="Купівля / Продаж">43,000 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-up-open">0.5000</span> / <span class="mfm-hover-show mfm-table-trend icon-down-open">-0.0500</span> </span> 45,700</td>
           <td data-title="НБУ"><span class="mfcur-nbu-full-wrap"> 41.<span class="mfm-text-grey">1744</span> <br> <span class="mfcur-nbu-full mfm-text-grey mfm-text-nowrap mfm-hover-show icon-open"> 0</span> </span></td>
           <td class="mfm-text-nowrap" data-title="Чорний ринок" data-small="Купівля / Продаж">44,500 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-open"></span> / <span class="mfm-hover-show mfm-table-trend icon-open"></span> </span> 45,675</td>
          </tr>
          <tr>
           <td class="mfcur-table-cur" data-title="Курс гривні"><a href="/ua/currency/chf/">CHF</a></td>
           <td class="mfm-text-nowrap" data-title="Середній курс в банках" data-small="Купівля / Продаж">38,000 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-up-open">0.5000</span> / <span class="mfm-hover-show mfm-table-trend icon-up-open">0.2000</span> </span> 40,500</td>
           <td data-title="НБУ"><span class="mfcur-nbu-full-wrap"> 36.<span class="mfm-text-grey">5084</span> <br> <span class="mfcur-nbu-full mfm-text-grey mfm-text-nowrap mfm-hover-show icon-open"> 0</span> </span></td>
           <td class="mfm-text-nowrap" data-title="Чорний ринок" data-small="Купівля / Продаж">39,700 <span class="mfm-text-light-grey mfm-posr"> <span class="mfm-hover-show mfm-table-trend icon-open"></span> / <span class="mfm-hover-show mfm-table-trend icon-open"></span> </span> 40,900</td>
          </tr>
         </tbody>
        </table>
    """.trimIndent()
    val document = Jsoup.parse(htmlContent)
    val currencies = document.select("tbody").select(".mfcur-table-cur").map {
        it.text()
    }
    println(currencies)
    println("******For black market*****")
    println(document.select("tbody").select("td.mfm-text-nowrap[data-title]").filter {
        element -> element.attr("data-title").contains("Чорн")
    }.map {
        it.text()
    })

    println("-----For avg market------")

    val buySellPairs = document.select("tbody").select("td.mfm-text-nowrap[data-title]").filter {
            element -> element.attr("data-title").contains("Середн")
    }.map {
        println(it.text())
        val splitted = it.text().split("/", " ").filter { !it.isEmpty() }.map {
            it.replace(",", ".")
        }
        println(splitted)
        Pair<Float, Float>(splitted[0].toFloat(), splitted[3].toFloat())
    }

    println(currencies.zip(buySellPairs).map {
        CurrencyRate(it.first, "UAH", it.second.first, it.second.second, SimpleDateFormat("YYYY-MM-dd").format(Date()))
    })
    val data = document.select("tbody").select("td[data-title]").filter {
            element -> element.attr("data-title").contains("НБУ")
    }.map {
        it.text().split(" ").get(0).toFloat()
    }

    val currencyRates = currencies.zip(data).map { pair ->
        CurrencyRate(pair.first, "UAH", pair.second, pair.second, SimpleDateFormat("YYYY-MM-dd").format(Date()))
    }

    println(currencyRates)
}