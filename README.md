# tradein
Sample vertx web application to showcase the usage of [vertx-boot](https://github.com/greyseal/vertx-boot) library. This application shows the trading data from [BSE](https://www.bseindia.com/). This is just for demo purpose.

## Getting Started

Git clone the project on your local machine and import it to your favorite ide.

### Prerequisites

For runnning this, you will need
- [Vertx-Boot](https://github.com/greyseal/vertx-boot) library.
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.

## Brief
This sample application make use of [Vertx-Boot](https://github.com/greyseal/vertx-boot) library to expose a trading rest API 
- **HttpServerVerticle**       -> Default verticle from the vertx-boot library. Can be extened for the functionality.
- **ServerStatusHandler**      -> Sample handler to send a "OK" Json response.
- **MarketLiveHandler**        -> Handler to handle Trading Requests
- **PingHandler**              -> Default handler from the vertx-boot library to send a "OK" Json response.

## Running the app

For running the app, (IDE used here is IntelliJ)
- Open **appConfig.json** file and set the "http_server_port" as per your choice.
- Once, changes are done in **appConfig.json**, add/edit Run/Debug Configurations for the project("tradein") and set:
  * **Main class**: com.greyseal.vertx.boot.AppLauncher
  * **VM options**: -Dlogback.configurationFile=file:../tradein/src/main/resources/logback.xml
  * **Program arguments**: -run com.greyseal.vertx.boot.verticle.MainVerticle -conf ../tradein/src/main/resources/appConfig.json 
  * **Environment variables**: ENV=dev 
 <br /><br /> 

After setting the variables, Run/Debug the project. If app starts successfully, then try <br /><br /> 
**Type:** *GET http://localhost:8080/runner/api/status* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479fadddda* <br />

Response<br />
```
{
    "status": "OK"
}
```
Default rest API can also be tried... <br /><br />
**Type:** *GET http://localhost:8080/runner/api/ping* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479fadddda* <br />

Response<br />
```
{
    "status": "OK"
}
```
That's it.

## Tradein Rest APIs
- Currently added support for BSE. To run/debug...<br  /><br  />
* To get **BSE SENSEX Current Value** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_CURRENT_VALUE* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
 {
    "has_data": true,
    "data": {
        "value": 35443.67,
        "points_difference": 19.41,
        "percentage_difference": 0.05,
        "is_high": false,
        "time": "08 Jun 18 | 16:00",
        "status": "Close"
    }
}
 ```

* To get **BSE SENSEX HIGH-LOW Values** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_HIGH_LOW_VALUE* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
 {
    "has_data": true,
    "data": {
        "all_time": {
            "high": 36443.98,
            "high_on": "29/01/2018",
            "low": 947.14,
            "low_on": "25/01/1991"
        },
        "52_w": {
            "high": 36443.98,
            "high_on": "29/01/2018",
            "low": 30680.66,
            "low_on": "30/06/2017"
        }
    }
}
 ```

* To get **SENSEX Current Summary** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_CURRENT_SUMMARY* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
 {
    "has_data": true,
    "data": {
        "open": 35406.47,
        "high": 35484.94,
        "low": 35260,
        "current": 35443.67,
        "previous_close": 35463.08,
        "points_difference": 19.41,
        "percentage_difference": 0.05,
        "is_high": false,
        "time": "08 Jun 2018 | 16:00",
        "status": "Close"
    }
}
 ```

* To get **SENSEX Data Series -> 1(for 1 Day), 1M, 3M, 6M, 12M for Months** url <br /><br />
**timeframe values are 1, 1M, 3M, 6M, 12M** <br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_DATA_SERIES&timeframe=1* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "has_data": true,
    "data": {
        "requested_date": "2018/06/08",
        "y_axis_start": 35200,
        "y_axis_end": 35600,
        "stock_name": "S&P BSE SENSEX",
        "last_updated_time": "15:59",
        "open": 35406.47,
        "high": 35484.94,
        "low": 35260,
        "previous_close": 35463.08,
        "current": 35443.67,
        "points_difference": -19.41,
        "percentage_difference": -0.05,
        "is_high": false,
        "series": [
            {
                "time": "09:00",
                "value": 35504.02
            },
            {
                "time": "09:01",
                "value": 35461.12
            },
            {
                "time": "09:02",
                "value": 35439.36
            },...
        ],
        "timeframe": "1D"
    }
}
 ```
 
* To get **BSE SENSEX Full Summary** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_CURRENT_FULL_SUMMARY* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "has_data": true,
    "data": {
        "requested_date": "2018/06/08",
        "y_axis_start": 35200,
        "y_axis_end": 35600,
        "stock_name": "S&P BSE SENSEX",
        "last_updated_time": "15:59",
        "open": 35406.47,
        "high": 35484.94,
        "low": 35260,
        "previous_close": 35463.08,
        "current": 35443.67,
        "points_difference": -19.41,
        "percentage_difference": -0.05,
        "is_high": false,
        "series": [
            {
                "time": "09:00",
                "value": 35504.02
            },
            {
                "time": "09:01",
                "value": 35461.12
            },
            {
                "time": "09:02",
                "value": 35439.36
            },...
        ],
        "timeframe": "1D",
        "all_time": {
            "high": 36443.98,
            "high_on": "29/01/2018",
            "low": 947.14,
            "low_on": "25/01/1991"
        },
        "52_w": {
            "high": 36443.98,
            "high_on": "29/01/2018",
            "low": 30680.66,
            "low_on": "30/06/2017"
        }
    }
}
 ```
 
* To get **EQUITY Company Search** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_COMPANY_SEARCH&product=EQ&name=ptc* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "has_data": true,
    "data": {
        "companies": [
            {
                "code": "PTC",
                "name": "PTC INDIA LTD",
                "id": 532524
            },
            {
                "code": "PFS",
                "name": "PTC INDIA FINANCIAL SERVICES LTD",
                "id": 533344
            },
            {
                "code": "PTCIL",
                "name": "PTC INDUSTRIES LTD",
                "id": 539006
            }
        ]
    }
}
 ```
 
* To get **EQUITY Company Info** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=STOCK_COMPANY_INFO&product=EQ&code=532524* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "has_data": true,
    "data": {
        "security_id": "PTC",
        "group_index": "A / S&P BSE 500",
        "face_value": 10,
        "security_code": 532524,
        "isin": "INE877F01012",
        "industry": "Electric Utilities"
    }
}
 ```
 
* To get **EQUITY Current Data** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=EQ_CURRENT_DATA&product=EQ&code=532524* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "has_data": true,
    "data": {
        "category": "Listed",
        "previous_close": 84.1,
        "open": 83.5,
        "high": 85,
        "low": 82.8,
        "current": 84.15,
        "points_difference": 0.05,
        "percentage_difference": 0.06,
        "is_high": true,
        "date_time": "As on 08 Jun 18 | 16:00"
    }
}
```

* To get **EQUITY Quarter Results** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=EQ_Q_RESULTS&product=EQ&code=532524* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "data": {
        "q_results": [
            {
                "q_time": "Mar-18",
                "values": [
                    {
                        "name": "Revenue",
                        "value": "4,007.89"
                    },
                    {
                        "name": "Net Profit",
                        "value": "64.37"
                    },
                    {
                        "name": "EPS",
                        "value": "2.17"
                    },
                    {
                        "name": "Cash EPS",
                        "value": "2.20"
                    },
                    {
                        "name": "OPM %",
                        "value": "2.45"
                    },
                    {
                        "name": "NPM %",
                        "value": "1.61"
                    }
                ]
            },
            {
                "q_time": "Dec-17",
                "values": [
                    {
                        "name": "Revenue",
                        "value": "4,490.07"
                    },
                    {
                        "name": "Net Profit",
                        "value": "58.94"
                    },
                    {
                        "name": "EPS",
                        "value": "1.99"
                    },
                    {
                        "name": "Cash EPS",
                        "value": "2.02"
                    },
                    {
                        "name": "OPM %",
                        "value": "1.95"
                    },
                    {
                        "name": "NPM %",
                        "value": "1.31"
                    }
                ]
            },
            {
                "q_time": "FY17-18",
                "values": [
                    {
                        "name": "Revenue",
                        "value": "18,189.04"
                    },
                    {
                        "name": "Net Profit",
                        "value": "319.20"
                    },
                    {
                        "name": "EPS",
                        "value": "10.78"
                    },
                    {
                        "name": "Cash EPS",
                        "value": "10.88"
                    },
                    {
                        "name": "OPM %",
                        "value": "2.46"
                    },
                    {
                        "name": "NPM %",
                        "value": "1.75"
                    }
                ]
            }
        ]
    }
}
```

* To get **EQUITY High-Low Values** url <br /><br />
**Type:** *POST http://localhost:8080/runner/api/live?stock=BSE&function=EQ_Q_RESULTS&product=EQ&code=532524* <br />
**Headers:** *Content-Type: application/json*;  *Trace-Id: c1d887063c3e492b9951b0479faddddu* <br />
**Response:**
```
{
    "data": {
        "values": [
            {
                "name": "52 Week High (adjusted)",
                "value": 62.5,
                "date": "15/01/2018"
            },
            {
                "name": "52 Week Low (adjusted)",
                "value": 22.1,
                "date": "28/06/2017"
            },
            {
                "name": "52 Week High (Unadjusted)",
                "value": 62.5,
                "date": "15/01/2018"
            },
            {
                "name": "52 Week Low (Unadjusted)",
                "value": 22.1,
                "date": "28/06/2017"
            },
            {
                "name": "Month H/L",
                "value": "57.90/42.75"
            },
            {
                "name": "Week H/L",
                "value": "49.70/42.75"
            }
        ]
    }
}
```

## Built With
* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
