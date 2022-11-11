### Українською:

Сервіс для отримання курсів валют (EUR, USD, GBP, PLN) по відношенню до гривні(UAH)   на поточну дату, та на певну дату. Дата повинна вводитись в форматі ISO YYYY-MM-dd.

Наприклад: **2022-11-07** (07.11.2022)
Дані отримуються із сайту Мінфіну України https://minfin.com.ua/ua/currency/

Підтримуються наступні курси:
* НБУ -> Приклад запиту: __external service ip address__/currency/nbu, <service ip>/currency/nbu/2022-11-07__   
* Чорний ринок -> Приклад запиту: __external service ip address__/currency/black, <service ip>/currency/black/2022-11-07  
* Середньоденний -> Приклад запиту: __external service ip address__/currency/avg, <service ip>/currency/avg/2022-11-07

Приклад відповід у форматі JSON:
```json
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
```
Опис обєкту що повертається:
* ccy-Код валюти,
* base_ccy-гривня,  
* buy-курс покупки валюти,
* sell-курс продажу валюти,
* date-дата курсу

### In English:

Service for receiving exchange rates (**EUR, USD, GBP, PLN**) in relation to the hryvna (**UAH**)
on the current date and on the specific date. The date must be entered in ISO format (**YYYY-MM-dd**).  

For example: **2022-11-07**

The data are obtained from the website of The Ministry of Finance of Ukraine: https://minfin.com.ua/ua/currency/  

The following courses are supported:
* NBU(The National Bank of Ukraine) -> Sample request: __external service ip address__/currency/nbu, __service ip__/currency/nbu/2022-11-07  
* Black(Black market) -> Sample request: __external service ip address__/currency/black, __service ip__/currency/black/2022-11-07  
* Avg(Midday) -> Sample request: <external service ip address>/currency/avg, <service ip>/currency/avg/2022-11-07

An example of a response in JSON format:
```json
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
```
The description of the returned object:
* ccy-Currency code,
* base_ccy-Ukrainian hryvna's code,
* buy-buy currency purchase rate,
* sell-rate of selling currency,
* date-date of the course