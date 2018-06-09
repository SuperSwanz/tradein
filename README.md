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
            },
            {
                "time": "09:03",
                "value": 35431.56
            },
            {
                "time": "09:04",
                "value": 35393.45
            },
            {
                "time": "09:05",
                "value": 35366.69
            },
            {
                "time": "09:06",
                "value": 35391.03
            },
            {
                "time": "09:07",
                "value": 35406.47
            },
            {
                "time": "09:08",
                "value": 35406.47
            },
            {
                "time": "09:09",
                "value": 35406.47
            },
            {
                "time": "09:10",
                "value": 35406.47
            },
            {
                "time": "09:11",
                "value": 35406.47
            },
            {
                "time": "09:12",
                "value": 35406.47
            },
            {
                "time": "09:13",
                "value": 35406.47
            },
            {
                "time": "09:14",
                "value": 35406.47
            },
            {
                "time": "09:15",
                "value": 35344.76
            },
            {
                "time": "09:16",
                "value": 35321.34
            },
            {
                "time": "09:17",
                "value": 35323.37
            },
            {
                "time": "09:18",
                "value": 35343.67
            },
            {
                "time": "09:19",
                "value": 35355.46
            },
            {
                "time": "09:20",
                "value": 35332.85
            },
            {
                "time": "09:21",
                "value": 35315.39
            },
            {
                "time": "09:22",
                "value": 35337.52
            },
            {
                "time": "09:23",
                "value": 35367.08
            },
            {
                "time": "09:24",
                "value": 35359.19
            },
            {
                "time": "09:25",
                "value": 35353.17
            },
            {
                "time": "09:26",
                "value": 35363.53
            },
            {
                "time": "09:27",
                "value": 35371.8
            },
            {
                "time": "09:28",
                "value": 35372.41
            },
            {
                "time": "09:29",
                "value": 35370.94
            },
            {
                "time": "09:30",
                "value": 35353.4
            },
            {
                "time": "09:31",
                "value": 35359.11
            },
            {
                "time": "09:32",
                "value": 35356.14
            },
            {
                "time": "09:33",
                "value": 35347.68
            },
            {
                "time": "09:34",
                "value": 35345.4
            },
            {
                "time": "09:35",
                "value": 35341.91
            },
            {
                "time": "09:36",
                "value": 35334.95
            },
            {
                "time": "09:37",
                "value": 35339.16
            },
            {
                "time": "09:38",
                "value": 35341.09
            },
            {
                "time": "09:39",
                "value": 35358.22
            },
            {
                "time": "09:40",
                "value": 35357.74
            },
            {
                "time": "09:41",
                "value": 35375.17
            },
            {
                "time": "09:42",
                "value": 35385.72
            },
            {
                "time": "09:43",
                "value": 35381.12
            },
            {
                "time": "09:44",
                "value": 35380.07
            },
            {
                "time": "09:45",
                "value": 35372.28
            },
            {
                "time": "09:46",
                "value": 35371
            },
            {
                "time": "09:47",
                "value": 35370.41
            },
            {
                "time": "09:48",
                "value": 35375.18
            },
            {
                "time": "09:49",
                "value": 35385
            },
            {
                "time": "09:50",
                "value": 35378.26
            },
            {
                "time": "09:51",
                "value": 35372.24
            },
            {
                "time": "09:52",
                "value": 35371.23
            },
            {
                "time": "09:53",
                "value": 35381.58
            },
            {
                "time": "09:54",
                "value": 35394.44
            },
            {
                "time": "09:55",
                "value": 35391.01
            },
            {
                "time": "09:56",
                "value": 35386.99
            },
            {
                "time": "09:57",
                "value": 35408.17
            },
            {
                "time": "09:58",
                "value": 35408.58
            },
            {
                "time": "09:59",
                "value": 35403.79
            },
            {
                "time": "10:00",
                "value": 35393.74
            },
            {
                "time": "10:01",
                "value": 35397.75
            },
            {
                "time": "10:02",
                "value": 35392.9
            },
            {
                "time": "10:03",
                "value": 35402.34
            },
            {
                "time": "10:04",
                "value": 35406.9
            },
            {
                "time": "10:05",
                "value": 35405.79
            },
            {
                "time": "10:06",
                "value": 35402.79
            },
            {
                "time": "10:07",
                "value": 35395.55
            },
            {
                "time": "10:08",
                "value": 35391.98
            },
            {
                "time": "10:09",
                "value": 35388.34
            },
            {
                "time": "10:10",
                "value": 35382.75
            },
            {
                "time": "10:11",
                "value": 35373.63
            },
            {
                "time": "10:12",
                "value": 35388
            },
            {
                "time": "10:13",
                "value": 35381.11
            },
            {
                "time": "10:14",
                "value": 35384.28
            },
            {
                "time": "10:15",
                "value": 35395.57
            },
            {
                "time": "10:16",
                "value": 35397.48
            },
            {
                "time": "10:17",
                "value": 35402.03
            },
            {
                "time": "10:18",
                "value": 35403.52
            },
            {
                "time": "10:19",
                "value": 35399.92
            },
            {
                "time": "10:20",
                "value": 35405.69
            },
            {
                "time": "10:21",
                "value": 35400.25
            },
            {
                "time": "10:22",
                "value": 35408.09
            },
            {
                "time": "10:23",
                "value": 35393.33
            },
            {
                "time": "10:24",
                "value": 35388.83
            },
            {
                "time": "10:25",
                "value": 35377.38
            },
            {
                "time": "10:26",
                "value": 35360.56
            },
            {
                "time": "10:27",
                "value": 35362.49
            },
            {
                "time": "10:28",
                "value": 35367.6
            },
            {
                "time": "10:29",
                "value": 35366.33
            },
            {
                "time": "10:30",
                "value": 35350.77
            },
            {
                "time": "10:31",
                "value": 35345.45
            },
            {
                "time": "10:32",
                "value": 35351.61
            },
            {
                "time": "10:33",
                "value": 35362.14
            },
            {
                "time": "10:34",
                "value": 35355.17
            },
            {
                "time": "10:35",
                "value": 35350.98
            },
            {
                "time": "10:36",
                "value": 35353.99
            },
            {
                "time": "10:37",
                "value": 35361.29
            },
            {
                "time": "10:38",
                "value": 35360.95
            },
            {
                "time": "10:39",
                "value": 35368.17
            },
            {
                "time": "10:40",
                "value": 35370.96
            },
            {
                "time": "10:41",
                "value": 35366.76
            },
            {
                "time": "10:42",
                "value": 35368.66
            },
            {
                "time": "10:43",
                "value": 35371.56
            },
            {
                "time": "10:44",
                "value": 35361.31
            },
            {
                "time": "10:45",
                "value": 35357.85
            },
            {
                "time": "10:46",
                "value": 35361.16
            },
            {
                "time": "10:47",
                "value": 35361.61
            },
            {
                "time": "10:48",
                "value": 35372.29
            },
            {
                "time": "10:49",
                "value": 35365.64
            },
            {
                "time": "10:50",
                "value": 35368.22
            },
            {
                "time": "10:51",
                "value": 35360.98
            },
            {
                "time": "10:52",
                "value": 35338.47
            },
            {
                "time": "10:53",
                "value": 35329.59
            },
            {
                "time": "10:54",
                "value": 35337.77
            },
            {
                "time": "10:55",
                "value": 35351.64
            },
            {
                "time": "10:56",
                "value": 35352.29
            },
            {
                "time": "10:57",
                "value": 35357.45
            },
            {
                "time": "10:58",
                "value": 35350.36
            },
            {
                "time": "10:59",
                "value": 35361.07
            },
            {
                "time": "11:00",
                "value": 35369.84
            },
            {
                "time": "11:01",
                "value": 35372.73
            },
            {
                "time": "11:02",
                "value": 35378.86
            },
            {
                "time": "11:03",
                "value": 35382.14
            },
            {
                "time": "11:04",
                "value": 35373.62
            },
            {
                "time": "11:05",
                "value": 35368.2
            },
            {
                "time": "11:06",
                "value": 35370.6
            },
            {
                "time": "11:07",
                "value": 35364.16
            },
            {
                "time": "11:08",
                "value": 35368.72
            },
            {
                "time": "11:09",
                "value": 35371.73
            },
            {
                "time": "11:10",
                "value": 35365.57
            },
            {
                "time": "11:11",
                "value": 35365.18
            },
            {
                "time": "11:12",
                "value": 35366.8
            },
            {
                "time": "11:13",
                "value": 35356.86
            },
            {
                "time": "11:14",
                "value": 35351.43
            },
            {
                "time": "11:15",
                "value": 35351.78
            },
            {
                "time": "11:16",
                "value": 35349.18
            },
            {
                "time": "11:17",
                "value": 35335.62
            },
            {
                "time": "11:18",
                "value": 35342.8
            },
            {
                "time": "11:19",
                "value": 35340.56
            },
            {
                "time": "11:20",
                "value": 35342.65
            },
            {
                "time": "11:21",
                "value": 35343.36
            },
            {
                "time": "11:22",
                "value": 35345.37
            },
            {
                "time": "11:23",
                "value": 35358.62
            },
            {
                "time": "11:24",
                "value": 35360.42
            },
            {
                "time": "11:25",
                "value": 35369.81
            },
            {
                "time": "11:26",
                "value": 35375.77
            },
            {
                "time": "11:27",
                "value": 35373.72
            },
            {
                "time": "11:28",
                "value": 35376.46
            },
            {
                "time": "11:29",
                "value": 35373.02
            },
            {
                "time": "11:30",
                "value": 35361.93
            },
            {
                "time": "11:31",
                "value": 35354.99
            },
            {
                "time": "11:32",
                "value": 35344.87
            },
            {
                "time": "11:33",
                "value": 35339.15
            },
            {
                "time": "11:34",
                "value": 35338.85
            },
            {
                "time": "11:35",
                "value": 35338.55
            },
            {
                "time": "11:36",
                "value": 35329.35
            },
            {
                "time": "11:37",
                "value": 35337.13
            },
            {
                "time": "11:38",
                "value": 35341.4
            },
            {
                "time": "11:39",
                "value": 35339.41
            },
            {
                "time": "11:40",
                "value": 35337.34
            },
            {
                "time": "11:41",
                "value": 35332.34
            },
            {
                "time": "11:42",
                "value": 35327.58
            },
            {
                "time": "11:43",
                "value": 35329.79
            },
            {
                "time": "11:44",
                "value": 35330.55
            },
            {
                "time": "11:45",
                "value": 35331.09
            },
            {
                "time": "11:46",
                "value": 35316.8
            },
            {
                "time": "11:47",
                "value": 35318.65
            },
            {
                "time": "11:48",
                "value": 35321.93
            },
            {
                "time": "11:49",
                "value": 35324.9
            },
            {
                "time": "11:50",
                "value": 35327.72
            },
            {
                "time": "11:51",
                "value": 35325.16
            },
            {
                "time": "11:52",
                "value": 35334.15
            },
            {
                "time": "11:53",
                "value": 35332.11
            },
            {
                "time": "11:54",
                "value": 35320.77
            },
            {
                "time": "11:55",
                "value": 35319.58
            },
            {
                "time": "11:56",
                "value": 35320.36
            },
            {
                "time": "11:57",
                "value": 35314.83
            },
            {
                "time": "11:58",
                "value": 35315.43
            },
            {
                "time": "11:59",
                "value": 35312.66
            },
            {
                "time": "12:00",
                "value": 35314.14
            },
            {
                "time": "12:01",
                "value": 35316.71
            },
            {
                "time": "12:02",
                "value": 35320.85
            },
            {
                "time": "12:03",
                "value": 35324.48
            },
            {
                "time": "12:04",
                "value": 35325.12
            },
            {
                "time": "12:05",
                "value": 35317.56
            },
            {
                "time": "12:06",
                "value": 35317.49
            },
            {
                "time": "12:07",
                "value": 35312.77
            },
            {
                "time": "12:08",
                "value": 35317.19
            },
            {
                "time": "12:09",
                "value": 35312.75
            },
            {
                "time": "12:10",
                "value": 35314.01
            },
            {
                "time": "12:11",
                "value": 35311.93
            },
            {
                "time": "12:12",
                "value": 35294.26
            },
            {
                "time": "12:13",
                "value": 35292.95
            },
            {
                "time": "12:14",
                "value": 35296.9
            },
            {
                "time": "12:15",
                "value": 35281.08
            },
            {
                "time": "12:16",
                "value": 35278.91
            },
            {
                "time": "12:17",
                "value": 35272.19
            },
            {
                "time": "12:18",
                "value": 35261.3
            },
            {
                "time": "12:19",
                "value": 35268.81
            },
            {
                "time": "12:20",
                "value": 35282.81
            },
            {
                "time": "12:21",
                "value": 35295.52
            },
            {
                "time": "12:22",
                "value": 35308.87
            },
            {
                "time": "12:23",
                "value": 35302.13
            },
            {
                "time": "12:24",
                "value": 35299.74
            },
            {
                "time": "12:25",
                "value": 35304.11
            },
            {
                "time": "12:26",
                "value": 35299.32
            },
            {
                "time": "12:27",
                "value": 35299.5
            },
            {
                "time": "12:28",
                "value": 35301.46
            },
            {
                "time": "12:29",
                "value": 35306.82
            },
            {
                "time": "12:30",
                "value": 35290.25
            },
            {
                "time": "12:31",
                "value": 35285.28
            },
            {
                "time": "12:32",
                "value": 35286.18
            },
            {
                "time": "12:33",
                "value": 35278.89
            },
            {
                "time": "12:34",
                "value": 35286.47
            },
            {
                "time": "12:35",
                "value": 35283.17
            },
            {
                "time": "12:36",
                "value": 35280.12
            },
            {
                "time": "12:37",
                "value": 35277.57
            },
            {
                "time": "12:38",
                "value": 35275.13
            },
            {
                "time": "12:39",
                "value": 35273.65
            },
            {
                "time": "12:40",
                "value": 35267.76
            },
            {
                "time": "12:41",
                "value": 35267.5
            },
            {
                "time": "12:42",
                "value": 35273.88
            },
            {
                "time": "12:43",
                "value": 35273.19
            },
            {
                "time": "12:44",
                "value": 35271.22
            },
            {
                "time": "12:45",
                "value": 35278.55
            },
            {
                "time": "12:46",
                "value": 35290.62
            },
            {
                "time": "12:47",
                "value": 35299.83
            },
            {
                "time": "12:48",
                "value": 35305.52
            },
            {
                "time": "12:49",
                "value": 35308.13
            },
            {
                "time": "12:50",
                "value": 35310.23
            },
            {
                "time": "12:51",
                "value": 35316.1
            },
            {
                "time": "12:52",
                "value": 35311.29
            },
            {
                "time": "12:53",
                "value": 35310.8
            },
            {
                "time": "12:54",
                "value": 35308.56
            },
            {
                "time": "12:55",
                "value": 35318.64
            },
            {
                "time": "12:56",
                "value": 35315.85
            },
            {
                "time": "12:57",
                "value": 35311.64
            },
            {
                "time": "12:58",
                "value": 35298.64
            },
            {
                "time": "12:59",
                "value": 35300.25
            },
            {
                "time": "13:00",
                "value": 35303.87
            },
            {
                "time": "13:01",
                "value": 35303.73
            },
            {
                "time": "13:02",
                "value": 35302.49
            },
            {
                "time": "13:03",
                "value": 35307.6
            },
            {
                "time": "13:04",
                "value": 35301.01
            },
            {
                "time": "13:05",
                "value": 35293.68
            },
            {
                "time": "13:06",
                "value": 35282.79
            },
            {
                "time": "13:07",
                "value": 35283.6
            },
            {
                "time": "13:08",
                "value": 35279.03
            },
            {
                "time": "13:09",
                "value": 35282.38
            },
            {
                "time": "13:10",
                "value": 35293.73
            },
            {
                "time": "13:11",
                "value": 35292.91
            },
            {
                "time": "13:12",
                "value": 35302.97
            },
            {
                "time": "13:13",
                "value": 35303.71
            },
            {
                "time": "13:14",
                "value": 35309.99
            },
            {
                "time": "13:15",
                "value": 35314.64
            },
            {
                "time": "13:16",
                "value": 35317.77
            },
            {
                "time": "13:17",
                "value": 35329.18
            },
            {
                "time": "13:18",
                "value": 35334.82
            },
            {
                "time": "13:19",
                "value": 35325.88
            },
            {
                "time": "13:20",
                "value": 35328.58
            },
            {
                "time": "13:21",
                "value": 35329.71
            },
            {
                "time": "13:22",
                "value": 35341.31
            },
            {
                "time": "13:23",
                "value": 35341.33
            },
            {
                "time": "13:24",
                "value": 35333.99
            },
            {
                "time": "13:25",
                "value": 35319.15
            },
            {
                "time": "13:26",
                "value": 35316.12
            },
            {
                "time": "13:27",
                "value": 35323.17
            },
            {
                "time": "13:28",
                "value": 35322.84
            },
            {
                "time": "13:29",
                "value": 35329.42
            },
            {
                "time": "13:30",
                "value": 35323.81
            },
            {
                "time": "13:31",
                "value": 35331.25
            },
            {
                "time": "13:32",
                "value": 35329.79
            },
            {
                "time": "13:33",
                "value": 35340.21
            },
            {
                "time": "13:34",
                "value": 35339.95
            },
            {
                "time": "13:35",
                "value": 35338.26
            },
            {
                "time": "13:36",
                "value": 35315.85
            },
            {
                "time": "13:37",
                "value": 35320.29
            },
            {
                "time": "13:38",
                "value": 35312.33
            },
            {
                "time": "13:39",
                "value": 35320.45
            },
            {
                "time": "13:40",
                "value": 35331.09
            },
            {
                "time": "13:41",
                "value": 35331
            },
            {
                "time": "13:42",
                "value": 35345.34
            },
            {
                "time": "13:43",
                "value": 35344.76
            },
            {
                "time": "13:44",
                "value": 35333.51
            },
            {
                "time": "13:45",
                "value": 35336.95
            },
            {
                "time": "13:46",
                "value": 35337.55
            },
            {
                "time": "13:47",
                "value": 35343.82
            },
            {
                "time": "13:48",
                "value": 35342.97
            },
            {
                "time": "13:49",
                "value": 35343.11
            },
            {
                "time": "13:50",
                "value": 35337.1
            },
            {
                "time": "13:51",
                "value": 35318.66
            },
            {
                "time": "13:52",
                "value": 35327.16
            },
            {
                "time": "13:53",
                "value": 35326.82
            },
            {
                "time": "13:54",
                "value": 35325.96
            },
            {
                "time": "13:55",
                "value": 35338.85
            },
            {
                "time": "13:56",
                "value": 35332.24
            },
            {
                "time": "13:57",
                "value": 35335.86
            },
            {
                "time": "13:58",
                "value": 35340.23
            },
            {
                "time": "13:59",
                "value": 35336.64
            },
            {
                "time": "14:00",
                "value": 35344.47
            },
            {
                "time": "14:01",
                "value": 35338.05
            },
            {
                "time": "14:02",
                "value": 35334.05
            },
            {
                "time": "14:03",
                "value": 35350.54
            },
            {
                "time": "14:04",
                "value": 35345.26
            },
            {
                "time": "14:05",
                "value": 35360.37
            },
            {
                "time": "14:06",
                "value": 35362.76
            },
            {
                "time": "14:07",
                "value": 35357.2
            },
            {
                "time": "14:08",
                "value": 35356.05
            },
            {
                "time": "14:09",
                "value": 35359.39
            },
            {
                "time": "14:10",
                "value": 35357.08
            },
            {
                "time": "14:11",
                "value": 35371.99
            },
            {
                "time": "14:12",
                "value": 35388.71
            },
            {
                "time": "14:13",
                "value": 35389.71
            },
            {
                "time": "14:14",
                "value": 35382.61
            },
            {
                "time": "14:15",
                "value": 35390.75
            },
            {
                "time": "14:16",
                "value": 35401.14
            },
            {
                "time": "14:17",
                "value": 35381.28
            },
            {
                "time": "14:18",
                "value": 35369.95
            },
            {
                "time": "14:19",
                "value": 35369.69
            },
            {
                "time": "14:20",
                "value": 35367.4
            },
            {
                "time": "14:21",
                "value": 35381.96
            },
            {
                "time": "14:22",
                "value": 35375.34
            },
            {
                "time": "14:23",
                "value": 35364.83
            },
            {
                "time": "14:24",
                "value": 35355.71
            },
            {
                "time": "14:25",
                "value": 35357.94
            },
            {
                "time": "14:26",
                "value": 35357.36
            },
            {
                "time": "14:27",
                "value": 35370.11
            },
            {
                "time": "14:28",
                "value": 35376.88
            },
            {
                "time": "14:29",
                "value": 35384.41
            },
            {
                "time": "14:30",
                "value": 35377.28
            },
            {
                "time": "14:31",
                "value": 35382.98
            },
            {
                "time": "14:32",
                "value": 35391.39
            },
            {
                "time": "14:33",
                "value": 35388.59
            },
            {
                "time": "14:34",
                "value": 35391.93
            },
            {
                "time": "14:35",
                "value": 35394.5
            },
            {
                "time": "14:36",
                "value": 35410.64
            },
            {
                "time": "14:37",
                "value": 35424.65
            },
            {
                "time": "14:38",
                "value": 35421.09
            },
            {
                "time": "14:39",
                "value": 35416.09
            },
            {
                "time": "14:40",
                "value": 35436.44
            },
            {
                "time": "14:41",
                "value": 35432.1
            },
            {
                "time": "14:42",
                "value": 35442.82
            },
            {
                "time": "14:43",
                "value": 35457.15
            },
            {
                "time": "14:44",
                "value": 35462.31
            },
            {
                "time": "14:45",
                "value": 35446.68
            },
            {
                "time": "14:46",
                "value": 35428.9
            },
            {
                "time": "14:47",
                "value": 35429.29
            },
            {
                "time": "14:48",
                "value": 35437.93
            },
            {
                "time": "14:49",
                "value": 35448.26
            },
            {
                "time": "14:50",
                "value": 35444.84
            },
            {
                "time": "14:51",
                "value": 35438.25
            },
            {
                "time": "14:52",
                "value": 35438.44
            },
            {
                "time": "14:53",
                "value": 35439.73
            },
            {
                "time": "14:54",
                "value": 35420.48
            },
            {
                "time": "14:55",
                "value": 35419.27
            },
            {
                "time": "14:56",
                "value": 35423.96
            },
            {
                "time": "14:57",
                "value": 35437.71
            },
            {
                "time": "14:58",
                "value": 35434.91
            },
            {
                "time": "14:59",
                "value": 35432.69
            },
            {
                "time": "15:00",
                "value": 35437.46
            },
            {
                "time": "15:01",
                "value": 35447.78
            },
            {
                "time": "15:02",
                "value": 35448.27
            },
            {
                "time": "15:03",
                "value": 35461.55
            },
            {
                "time": "15:04",
                "value": 35469.47
            },
            {
                "time": "15:05",
                "value": 35475.01
            },
            {
                "time": "15:06",
                "value": 35470.4
            },
            {
                "time": "15:07",
                "value": 35463.52
            },
            {
                "time": "15:08",
                "value": 35472.57
            },
            {
                "time": "15:09",
                "value": 35468.12
            },
            {
                "time": "15:10",
                "value": 35453.52
            },
            {
                "time": "15:11",
                "value": 35454.42
            },
            {
                "time": "15:12",
                "value": 35449.3
            },
            {
                "time": "15:13",
                "value": 35454.41
            },
            {
                "time": "15:14",
                "value": 35446.48
            },
            {
                "time": "15:15",
                "value": 35439
            },
            {
                "time": "15:16",
                "value": 35430.25
            },
            {
                "time": "15:17",
                "value": 35420.04
            },
            {
                "time": "15:18",
                "value": 35422.84
            },
            {
                "time": "15:19",
                "value": 35437.42
            },
            {
                "time": "15:20",
                "value": 35430.15
            },
            {
                "time": "15:21",
                "value": 35435.34
            },
            {
                "time": "15:22",
                "value": 35427.56
            },
            {
                "time": "15:23",
                "value": 35439.73
            },
            {
                "time": "15:24",
                "value": 35433.22
            },
            {
                "time": "15:25",
                "value": 35437.14
            },
            {
                "time": "15:26",
                "value": 35425.3
            },
            {
                "time": "15:27",
                "value": 35416.04
            },
            {
                "time": "15:28",
                "value": 35405.24
            },
            {
                "time": "15:29",
                "value": 35411.92
            },
            {
                "time": "15:30",
                "value": 35443.67
            },
            {
                "time": "15:31",
                "value": 35443.67
            },
            {
                "time": "15:32",
                "value": 35443.67
            },
            {
                "time": "15:33",
                "value": 35443.67
            },
            {
                "time": "15:34",
                "value": 35443.67
            },
            {
                "time": "15:35",
                "value": 35443.67
            },
            {
                "time": "15:36",
                "value": 35443.67
            },
            {
                "time": "15:37",
                "value": 35443.67
            },
            {
                "time": "15:38",
                "value": 35443.67
            },
            {
                "time": "15:39",
                "value": 35443.67
            },
            {
                "time": "15:40",
                "value": 35443.67
            },
            {
                "time": "15:41",
                "value": 35443.67
            },
            {
                "time": "15:42",
                "value": 35443.67
            },
            {
                "time": "15:43",
                "value": 35443.67
            },
            {
                "time": "15:44",
                "value": 35443.67
            },
            {
                "time": "15:45",
                "value": 35443.67
            },
            {
                "time": "15:46",
                "value": 35443.67
            },
            {
                "time": "15:47",
                "value": 35443.67
            },
            {
                "time": "15:48",
                "value": 35443.67
            },
            {
                "time": "15:49",
                "value": 35443.67
            },
            {
                "time": "15:50",
                "value": 35443.67
            },
            {
                "time": "15:51",
                "value": 35443.67
            },
            {
                "time": "15:52",
                "value": 35443.67
            },
            {
                "time": "15:53",
                "value": 35443.67
            },
            {
                "time": "15:54",
                "value": 35443.67
            },
            {
                "time": "15:55",
                "value": 35443.67
            },
            {
                "time": "15:56",
                "value": 35443.67
            },
            {
                "time": "15:57",
                "value": 35443.67
            },
            {
                "time": "15:58",
                "value": 35443.67
            },
            {
                "time": "15:59",
                "value": 35443.67
            }
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
            },
            {
                "time": "09:03",
                "value": 35431.56
            },
            {
                "time": "09:04",
                "value": 35393.45
            },
            {
                "time": "09:05",
                "value": 35366.69
            },
            {
                "time": "09:06",
                "value": 35391.03
            },
            {
                "time": "09:07",
                "value": 35406.47
            },
            {
                "time": "09:08",
                "value": 35406.47
            },
            {
                "time": "09:09",
                "value": 35406.47
            },
            {
                "time": "09:10",
                "value": 35406.47
            },
            {
                "time": "09:11",
                "value": 35406.47
            },
            {
                "time": "09:12",
                "value": 35406.47
            },
            {
                "time": "09:13",
                "value": 35406.47
            },
            {
                "time": "09:14",
                "value": 35406.47
            },
            {
                "time": "09:15",
                "value": 35344.76
            },
            {
                "time": "09:16",
                "value": 35321.34
            },
            {
                "time": "09:17",
                "value": 35323.37
            },
            {
                "time": "09:18",
                "value": 35343.67
            },
            {
                "time": "09:19",
                "value": 35355.46
            },
            {
                "time": "09:20",
                "value": 35332.85
            },
            {
                "time": "09:21",
                "value": 35315.39
            },
            {
                "time": "09:22",
                "value": 35337.52
            },
            {
                "time": "09:23",
                "value": 35367.08
            },
            {
                "time": "09:24",
                "value": 35359.19
            },
            {
                "time": "09:25",
                "value": 35353.17
            },
            {
                "time": "09:26",
                "value": 35363.53
            },
            {
                "time": "09:27",
                "value": 35371.8
            },
            {
                "time": "09:28",
                "value": 35372.41
            },
            {
                "time": "09:29",
                "value": 35370.94
            },
            {
                "time": "09:30",
                "value": 35353.4
            },
            {
                "time": "09:31",
                "value": 35359.11
            },
            {
                "time": "09:32",
                "value": 35356.14
            },
            {
                "time": "09:33",
                "value": 35347.68
            },
            {
                "time": "09:34",
                "value": 35345.4
            },
            {
                "time": "09:35",
                "value": 35341.91
            },
            {
                "time": "09:36",
                "value": 35334.95
            },
            {
                "time": "09:37",
                "value": 35339.16
            },
            {
                "time": "09:38",
                "value": 35341.09
            },
            {
                "time": "09:39",
                "value": 35358.22
            },
            {
                "time": "09:40",
                "value": 35357.74
            },
            {
                "time": "09:41",
                "value": 35375.17
            },
            {
                "time": "09:42",
                "value": 35385.72
            },
            {
                "time": "09:43",
                "value": 35381.12
            },
            {
                "time": "09:44",
                "value": 35380.07
            },
            {
                "time": "09:45",
                "value": 35372.28
            },
            {
                "time": "09:46",
                "value": 35371
            },
            {
                "time": "09:47",
                "value": 35370.41
            },
            {
                "time": "09:48",
                "value": 35375.18
            },
            {
                "time": "09:49",
                "value": 35385
            },
            {
                "time": "09:50",
                "value": 35378.26
            },
            {
                "time": "09:51",
                "value": 35372.24
            },
            {
                "time": "09:52",
                "value": 35371.23
            },
            {
                "time": "09:53",
                "value": 35381.58
            },
            {
                "time": "09:54",
                "value": 35394.44
            },
            {
                "time": "09:55",
                "value": 35391.01
            },
            {
                "time": "09:56",
                "value": 35386.99
            },
            {
                "time": "09:57",
                "value": 35408.17
            },
            {
                "time": "09:58",
                "value": 35408.58
            },
            {
                "time": "09:59",
                "value": 35403.79
            },
            {
                "time": "10:00",
                "value": 35393.74
            },
            {
                "time": "10:01",
                "value": 35397.75
            },
            {
                "time": "10:02",
                "value": 35392.9
            },
            {
                "time": "10:03",
                "value": 35402.34
            },
            {
                "time": "10:04",
                "value": 35406.9
            },
            {
                "time": "10:05",
                "value": 35405.79
            },
            {
                "time": "10:06",
                "value": 35402.79
            },
            {
                "time": "10:07",
                "value": 35395.55
            },
            {
                "time": "10:08",
                "value": 35391.98
            },
            {
                "time": "10:09",
                "value": 35388.34
            },
            {
                "time": "10:10",
                "value": 35382.75
            },
            {
                "time": "10:11",
                "value": 35373.63
            },
            {
                "time": "10:12",
                "value": 35388
            },
            {
                "time": "10:13",
                "value": 35381.11
            },
            {
                "time": "10:14",
                "value": 35384.28
            },
            {
                "time": "10:15",
                "value": 35395.57
            },
            {
                "time": "10:16",
                "value": 35397.48
            },
            {
                "time": "10:17",
                "value": 35402.03
            },
            {
                "time": "10:18",
                "value": 35403.52
            },
            {
                "time": "10:19",
                "value": 35399.92
            },
            {
                "time": "10:20",
                "value": 35405.69
            },
            {
                "time": "10:21",
                "value": 35400.25
            },
            {
                "time": "10:22",
                "value": 35408.09
            },
            {
                "time": "10:23",
                "value": 35393.33
            },
            {
                "time": "10:24",
                "value": 35388.83
            },
            {
                "time": "10:25",
                "value": 35377.38
            },
            {
                "time": "10:26",
                "value": 35360.56
            },
            {
                "time": "10:27",
                "value": 35362.49
            },
            {
                "time": "10:28",
                "value": 35367.6
            },
            {
                "time": "10:29",
                "value": 35366.33
            },
            {
                "time": "10:30",
                "value": 35350.77
            },
            {
                "time": "10:31",
                "value": 35345.45
            },
            {
                "time": "10:32",
                "value": 35351.61
            },
            {
                "time": "10:33",
                "value": 35362.14
            },
            {
                "time": "10:34",
                "value": 35355.17
            },
            {
                "time": "10:35",
                "value": 35350.98
            },
            {
                "time": "10:36",
                "value": 35353.99
            },
            {
                "time": "10:37",
                "value": 35361.29
            },
            {
                "time": "10:38",
                "value": 35360.95
            },
            {
                "time": "10:39",
                "value": 35368.17
            },
            {
                "time": "10:40",
                "value": 35370.96
            },
            {
                "time": "10:41",
                "value": 35366.76
            },
            {
                "time": "10:42",
                "value": 35368.66
            },
            {
                "time": "10:43",
                "value": 35371.56
            },
            {
                "time": "10:44",
                "value": 35361.31
            },
            {
                "time": "10:45",
                "value": 35357.85
            },
            {
                "time": "10:46",
                "value": 35361.16
            },
            {
                "time": "10:47",
                "value": 35361.61
            },
            {
                "time": "10:48",
                "value": 35372.29
            },
            {
                "time": "10:49",
                "value": 35365.64
            },
            {
                "time": "10:50",
                "value": 35368.22
            },
            {
                "time": "10:51",
                "value": 35360.98
            },
            {
                "time": "10:52",
                "value": 35338.47
            },
            {
                "time": "10:53",
                "value": 35329.59
            },
            {
                "time": "10:54",
                "value": 35337.77
            },
            {
                "time": "10:55",
                "value": 35351.64
            },
            {
                "time": "10:56",
                "value": 35352.29
            },
            {
                "time": "10:57",
                "value": 35357.45
            },
            {
                "time": "10:58",
                "value": 35350.36
            },
            {
                "time": "10:59",
                "value": 35361.07
            },
            {
                "time": "11:00",
                "value": 35369.84
            },
            {
                "time": "11:01",
                "value": 35372.73
            },
            {
                "time": "11:02",
                "value": 35378.86
            },
            {
                "time": "11:03",
                "value": 35382.14
            },
            {
                "time": "11:04",
                "value": 35373.62
            },
            {
                "time": "11:05",
                "value": 35368.2
            },
            {
                "time": "11:06",
                "value": 35370.6
            },
            {
                "time": "11:07",
                "value": 35364.16
            },
            {
                "time": "11:08",
                "value": 35368.72
            },
            {
                "time": "11:09",
                "value": 35371.73
            },
            {
                "time": "11:10",
                "value": 35365.57
            },
            {
                "time": "11:11",
                "value": 35365.18
            },
            {
                "time": "11:12",
                "value": 35366.8
            },
            {
                "time": "11:13",
                "value": 35356.86
            },
            {
                "time": "11:14",
                "value": 35351.43
            },
            {
                "time": "11:15",
                "value": 35351.78
            },
            {
                "time": "11:16",
                "value": 35349.18
            },
            {
                "time": "11:17",
                "value": 35335.62
            },
            {
                "time": "11:18",
                "value": 35342.8
            },
            {
                "time": "11:19",
                "value": 35340.56
            },
            {
                "time": "11:20",
                "value": 35342.65
            },
            {
                "time": "11:21",
                "value": 35343.36
            },
            {
                "time": "11:22",
                "value": 35345.37
            },
            {
                "time": "11:23",
                "value": 35358.62
            },
            {
                "time": "11:24",
                "value": 35360.42
            },
            {
                "time": "11:25",
                "value": 35369.81
            },
            {
                "time": "11:26",
                "value": 35375.77
            },
            {
                "time": "11:27",
                "value": 35373.72
            },
            {
                "time": "11:28",
                "value": 35376.46
            },
            {
                "time": "11:29",
                "value": 35373.02
            },
            {
                "time": "11:30",
                "value": 35361.93
            },
            {
                "time": "11:31",
                "value": 35354.99
            },
            {
                "time": "11:32",
                "value": 35344.87
            },
            {
                "time": "11:33",
                "value": 35339.15
            },
            {
                "time": "11:34",
                "value": 35338.85
            },
            {
                "time": "11:35",
                "value": 35338.55
            },
            {
                "time": "11:36",
                "value": 35329.35
            },
            {
                "time": "11:37",
                "value": 35337.13
            },
            {
                "time": "11:38",
                "value": 35341.4
            },
            {
                "time": "11:39",
                "value": 35339.41
            },
            {
                "time": "11:40",
                "value": 35337.34
            },
            {
                "time": "11:41",
                "value": 35332.34
            },
            {
                "time": "11:42",
                "value": 35327.58
            },
            {
                "time": "11:43",
                "value": 35329.79
            },
            {
                "time": "11:44",
                "value": 35330.55
            },
            {
                "time": "11:45",
                "value": 35331.09
            },
            {
                "time": "11:46",
                "value": 35316.8
            },
            {
                "time": "11:47",
                "value": 35318.65
            },
            {
                "time": "11:48",
                "value": 35321.93
            },
            {
                "time": "11:49",
                "value": 35324.9
            },
            {
                "time": "11:50",
                "value": 35327.72
            },
            {
                "time": "11:51",
                "value": 35325.16
            },
            {
                "time": "11:52",
                "value": 35334.15
            },
            {
                "time": "11:53",
                "value": 35332.11
            },
            {
                "time": "11:54",
                "value": 35320.77
            },
            {
                "time": "11:55",
                "value": 35319.58
            },
            {
                "time": "11:56",
                "value": 35320.36
            },
            {
                "time": "11:57",
                "value": 35314.83
            },
            {
                "time": "11:58",
                "value": 35315.43
            },
            {
                "time": "11:59",
                "value": 35312.66
            },
            {
                "time": "12:00",
                "value": 35314.14
            },
            {
                "time": "12:01",
                "value": 35316.71
            },
            {
                "time": "12:02",
                "value": 35320.85
            },
            {
                "time": "12:03",
                "value": 35324.48
            },
            {
                "time": "12:04",
                "value": 35325.12
            },
            {
                "time": "12:05",
                "value": 35317.56
            },
            {
                "time": "12:06",
                "value": 35317.49
            },
            {
                "time": "12:07",
                "value": 35312.77
            },
            {
                "time": "12:08",
                "value": 35317.19
            },
            {
                "time": "12:09",
                "value": 35312.75
            },
            {
                "time": "12:10",
                "value": 35314.01
            },
            {
                "time": "12:11",
                "value": 35311.93
            },
            {
                "time": "12:12",
                "value": 35294.26
            },
            {
                "time": "12:13",
                "value": 35292.95
            },
            {
                "time": "12:14",
                "value": 35296.9
            },
            {
                "time": "12:15",
                "value": 35281.08
            },
            {
                "time": "12:16",
                "value": 35278.91
            },
            {
                "time": "12:17",
                "value": 35272.19
            },
            {
                "time": "12:18",
                "value": 35261.3
            },
            {
                "time": "12:19",
                "value": 35268.81
            },
            {
                "time": "12:20",
                "value": 35282.81
            },
            {
                "time": "12:21",
                "value": 35295.52
            },
            {
                "time": "12:22",
                "value": 35308.87
            },
            {
                "time": "12:23",
                "value": 35302.13
            },
            {
                "time": "12:24",
                "value": 35299.74
            },
            {
                "time": "12:25",
                "value": 35304.11
            },
            {
                "time": "12:26",
                "value": 35299.32
            },
            {
                "time": "12:27",
                "value": 35299.5
            },
            {
                "time": "12:28",
                "value": 35301.46
            },
            {
                "time": "12:29",
                "value": 35306.82
            },
            {
                "time": "12:30",
                "value": 35290.25
            },
            {
                "time": "12:31",
                "value": 35285.28
            },
            {
                "time": "12:32",
                "value": 35286.18
            },
            {
                "time": "12:33",
                "value": 35278.89
            },
            {
                "time": "12:34",
                "value": 35286.47
            },
            {
                "time": "12:35",
                "value": 35283.17
            },
            {
                "time": "12:36",
                "value": 35280.12
            },
            {
                "time": "12:37",
                "value": 35277.57
            },
            {
                "time": "12:38",
                "value": 35275.13
            },
            {
                "time": "12:39",
                "value": 35273.65
            },
            {
                "time": "12:40",
                "value": 35267.76
            },
            {
                "time": "12:41",
                "value": 35267.5
            },
            {
                "time": "12:42",
                "value": 35273.88
            },
            {
                "time": "12:43",
                "value": 35273.19
            },
            {
                "time": "12:44",
                "value": 35271.22
            },
            {
                "time": "12:45",
                "value": 35278.55
            },
            {
                "time": "12:46",
                "value": 35290.62
            },
            {
                "time": "12:47",
                "value": 35299.83
            },
            {
                "time": "12:48",
                "value": 35305.52
            },
            {
                "time": "12:49",
                "value": 35308.13
            },
            {
                "time": "12:50",
                "value": 35310.23
            },
            {
                "time": "12:51",
                "value": 35316.1
            },
            {
                "time": "12:52",
                "value": 35311.29
            },
            {
                "time": "12:53",
                "value": 35310.8
            },
            {
                "time": "12:54",
                "value": 35308.56
            },
            {
                "time": "12:55",
                "value": 35318.64
            },
            {
                "time": "12:56",
                "value": 35315.85
            },
            {
                "time": "12:57",
                "value": 35311.64
            },
            {
                "time": "12:58",
                "value": 35298.64
            },
            {
                "time": "12:59",
                "value": 35300.25
            },
            {
                "time": "13:00",
                "value": 35303.87
            },
            {
                "time": "13:01",
                "value": 35303.73
            },
            {
                "time": "13:02",
                "value": 35302.49
            },
            {
                "time": "13:03",
                "value": 35307.6
            },
            {
                "time": "13:04",
                "value": 35301.01
            },
            {
                "time": "13:05",
                "value": 35293.68
            },
            {
                "time": "13:06",
                "value": 35282.79
            },
            {
                "time": "13:07",
                "value": 35283.6
            },
            {
                "time": "13:08",
                "value": 35279.03
            },
            {
                "time": "13:09",
                "value": 35282.38
            },
            {
                "time": "13:10",
                "value": 35293.73
            },
            {
                "time": "13:11",
                "value": 35292.91
            },
            {
                "time": "13:12",
                "value": 35302.97
            },
            {
                "time": "13:13",
                "value": 35303.71
            },
            {
                "time": "13:14",
                "value": 35309.99
            },
            {
                "time": "13:15",
                "value": 35314.64
            },
            {
                "time": "13:16",
                "value": 35317.77
            },
            {
                "time": "13:17",
                "value": 35329.18
            },
            {
                "time": "13:18",
                "value": 35334.82
            },
            {
                "time": "13:19",
                "value": 35325.88
            },
            {
                "time": "13:20",
                "value": 35328.58
            },
            {
                "time": "13:21",
                "value": 35329.71
            },
            {
                "time": "13:22",
                "value": 35341.31
            },
            {
                "time": "13:23",
                "value": 35341.33
            },
            {
                "time": "13:24",
                "value": 35333.99
            },
            {
                "time": "13:25",
                "value": 35319.15
            },
            {
                "time": "13:26",
                "value": 35316.12
            },
            {
                "time": "13:27",
                "value": 35323.17
            },
            {
                "time": "13:28",
                "value": 35322.84
            },
            {
                "time": "13:29",
                "value": 35329.42
            },
            {
                "time": "13:30",
                "value": 35323.81
            },
            {
                "time": "13:31",
                "value": 35331.25
            },
            {
                "time": "13:32",
                "value": 35329.79
            },
            {
                "time": "13:33",
                "value": 35340.21
            },
            {
                "time": "13:34",
                "value": 35339.95
            },
            {
                "time": "13:35",
                "value": 35338.26
            },
            {
                "time": "13:36",
                "value": 35315.85
            },
            {
                "time": "13:37",
                "value": 35320.29
            },
            {
                "time": "13:38",
                "value": 35312.33
            },
            {
                "time": "13:39",
                "value": 35320.45
            },
            {
                "time": "13:40",
                "value": 35331.09
            },
            {
                "time": "13:41",
                "value": 35331
            },
            {
                "time": "13:42",
                "value": 35345.34
            },
            {
                "time": "13:43",
                "value": 35344.76
            },
            {
                "time": "13:44",
                "value": 35333.51
            },
            {
                "time": "13:45",
                "value": 35336.95
            },
            {
                "time": "13:46",
                "value": 35337.55
            },
            {
                "time": "13:47",
                "value": 35343.82
            },
            {
                "time": "13:48",
                "value": 35342.97
            },
            {
                "time": "13:49",
                "value": 35343.11
            },
            {
                "time": "13:50",
                "value": 35337.1
            },
            {
                "time": "13:51",
                "value": 35318.66
            },
            {
                "time": "13:52",
                "value": 35327.16
            },
            {
                "time": "13:53",
                "value": 35326.82
            },
            {
                "time": "13:54",
                "value": 35325.96
            },
            {
                "time": "13:55",
                "value": 35338.85
            },
            {
                "time": "13:56",
                "value": 35332.24
            },
            {
                "time": "13:57",
                "value": 35335.86
            },
            {
                "time": "13:58",
                "value": 35340.23
            },
            {
                "time": "13:59",
                "value": 35336.64
            },
            {
                "time": "14:00",
                "value": 35344.47
            },
            {
                "time": "14:01",
                "value": 35338.05
            },
            {
                "time": "14:02",
                "value": 35334.05
            },
            {
                "time": "14:03",
                "value": 35350.54
            },
            {
                "time": "14:04",
                "value": 35345.26
            },
            {
                "time": "14:05",
                "value": 35360.37
            },
            {
                "time": "14:06",
                "value": 35362.76
            },
            {
                "time": "14:07",
                "value": 35357.2
            },
            {
                "time": "14:08",
                "value": 35356.05
            },
            {
                "time": "14:09",
                "value": 35359.39
            },
            {
                "time": "14:10",
                "value": 35357.08
            },
            {
                "time": "14:11",
                "value": 35371.99
            },
            {
                "time": "14:12",
                "value": 35388.71
            },
            {
                "time": "14:13",
                "value": 35389.71
            },
            {
                "time": "14:14",
                "value": 35382.61
            },
            {
                "time": "14:15",
                "value": 35390.75
            },
            {
                "time": "14:16",
                "value": 35401.14
            },
            {
                "time": "14:17",
                "value": 35381.28
            },
            {
                "time": "14:18",
                "value": 35369.95
            },
            {
                "time": "14:19",
                "value": 35369.69
            },
            {
                "time": "14:20",
                "value": 35367.4
            },
            {
                "time": "14:21",
                "value": 35381.96
            },
            {
                "time": "14:22",
                "value": 35375.34
            },
            {
                "time": "14:23",
                "value": 35364.83
            },
            {
                "time": "14:24",
                "value": 35355.71
            },
            {
                "time": "14:25",
                "value": 35357.94
            },
            {
                "time": "14:26",
                "value": 35357.36
            },
            {
                "time": "14:27",
                "value": 35370.11
            },
            {
                "time": "14:28",
                "value": 35376.88
            },
            {
                "time": "14:29",
                "value": 35384.41
            },
            {
                "time": "14:30",
                "value": 35377.28
            },
            {
                "time": "14:31",
                "value": 35382.98
            },
            {
                "time": "14:32",
                "value": 35391.39
            },
            {
                "time": "14:33",
                "value": 35388.59
            },
            {
                "time": "14:34",
                "value": 35391.93
            },
            {
                "time": "14:35",
                "value": 35394.5
            },
            {
                "time": "14:36",
                "value": 35410.64
            },
            {
                "time": "14:37",
                "value": 35424.65
            },
            {
                "time": "14:38",
                "value": 35421.09
            },
            {
                "time": "14:39",
                "value": 35416.09
            },
            {
                "time": "14:40",
                "value": 35436.44
            },
            {
                "time": "14:41",
                "value": 35432.1
            },
            {
                "time": "14:42",
                "value": 35442.82
            },
            {
                "time": "14:43",
                "value": 35457.15
            },
            {
                "time": "14:44",
                "value": 35462.31
            },
            {
                "time": "14:45",
                "value": 35446.68
            },
            {
                "time": "14:46",
                "value": 35428.9
            },
            {
                "time": "14:47",
                "value": 35429.29
            },
            {
                "time": "14:48",
                "value": 35437.93
            },
            {
                "time": "14:49",
                "value": 35448.26
            },
            {
                "time": "14:50",
                "value": 35444.84
            },
            {
                "time": "14:51",
                "value": 35438.25
            },
            {
                "time": "14:52",
                "value": 35438.44
            },
            {
                "time": "14:53",
                "value": 35439.73
            },
            {
                "time": "14:54",
                "value": 35420.48
            },
            {
                "time": "14:55",
                "value": 35419.27
            },
            {
                "time": "14:56",
                "value": 35423.96
            },
            {
                "time": "14:57",
                "value": 35437.71
            },
            {
                "time": "14:58",
                "value": 35434.91
            },
            {
                "time": "14:59",
                "value": 35432.69
            },
            {
                "time": "15:00",
                "value": 35437.46
            },
            {
                "time": "15:01",
                "value": 35447.78
            },
            {
                "time": "15:02",
                "value": 35448.27
            },
            {
                "time": "15:03",
                "value": 35461.55
            },
            {
                "time": "15:04",
                "value": 35469.47
            },
            {
                "time": "15:05",
                "value": 35475.01
            },
            {
                "time": "15:06",
                "value": 35470.4
            },
            {
                "time": "15:07",
                "value": 35463.52
            },
            {
                "time": "15:08",
                "value": 35472.57
            },
            {
                "time": "15:09",
                "value": 35468.12
            },
            {
                "time": "15:10",
                "value": 35453.52
            },
            {
                "time": "15:11",
                "value": 35454.42
            },
            {
                "time": "15:12",
                "value": 35449.3
            },
            {
                "time": "15:13",
                "value": 35454.41
            },
            {
                "time": "15:14",
                "value": 35446.48
            },
            {
                "time": "15:15",
                "value": 35439
            },
            {
                "time": "15:16",
                "value": 35430.25
            },
            {
                "time": "15:17",
                "value": 35420.04
            },
            {
                "time": "15:18",
                "value": 35422.84
            },
            {
                "time": "15:19",
                "value": 35437.42
            },
            {
                "time": "15:20",
                "value": 35430.15
            },
            {
                "time": "15:21",
                "value": 35435.34
            },
            {
                "time": "15:22",
                "value": 35427.56
            },
            {
                "time": "15:23",
                "value": 35439.73
            },
            {
                "time": "15:24",
                "value": 35433.22
            },
            {
                "time": "15:25",
                "value": 35437.14
            },
            {
                "time": "15:26",
                "value": 35425.3
            },
            {
                "time": "15:27",
                "value": 35416.04
            },
            {
                "time": "15:28",
                "value": 35405.24
            },
            {
                "time": "15:29",
                "value": 35411.92
            },
            {
                "time": "15:30",
                "value": 35443.67
            },
            {
                "time": "15:31",
                "value": 35443.67
            },
            {
                "time": "15:32",
                "value": 35443.67
            },
            {
                "time": "15:33",
                "value": 35443.67
            },
            {
                "time": "15:34",
                "value": 35443.67
            },
            {
                "time": "15:35",
                "value": 35443.67
            },
            {
                "time": "15:36",
                "value": 35443.67
            },
            {
                "time": "15:37",
                "value": 35443.67
            },
            {
                "time": "15:38",
                "value": 35443.67
            },
            {
                "time": "15:39",
                "value": 35443.67
            },
            {
                "time": "15:40",
                "value": 35443.67
            },
            {
                "time": "15:41",
                "value": 35443.67
            },
            {
                "time": "15:42",
                "value": 35443.67
            },
            {
                "time": "15:43",
                "value": 35443.67
            },
            {
                "time": "15:44",
                "value": 35443.67
            },
            {
                "time": "15:45",
                "value": 35443.67
            },
            {
                "time": "15:46",
                "value": 35443.67
            },
            {
                "time": "15:47",
                "value": 35443.67
            },
            {
                "time": "15:48",
                "value": 35443.67
            },
            {
                "time": "15:49",
                "value": 35443.67
            },
            {
                "time": "15:50",
                "value": 35443.67
            },
            {
                "time": "15:51",
                "value": 35443.67
            },
            {
                "time": "15:52",
                "value": 35443.67
            },
            {
                "time": "15:53",
                "value": 35443.67
            },
            {
                "time": "15:54",
                "value": 35443.67
            },
            {
                "time": "15:55",
                "value": 35443.67
            },
            {
                "time": "15:56",
                "value": 35443.67
            },
            {
                "time": "15:57",
                "value": 35443.67
            },
            {
                "time": "15:58",
                "value": 35443.67
            },
            {
                "time": "15:59",
                "value": 35443.67
            }
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
