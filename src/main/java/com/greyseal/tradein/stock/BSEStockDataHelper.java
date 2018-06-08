package com.greyseal.tradein.stock;

import com.greyseal.tradein.market.IProductDataHelper;
import com.greyseal.tradein.market.ProductDataProviderFactory;
import com.greyseal.tradein.util.CommonUtil;
import com.greyseal.vertx.boot.httpclient.HttpRequestProcessor;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.List;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * <p>This is the concrete class to get the data from the SENSEX (BSE) </p>
 *
 * @see <a href="https://www.bseindia.com/sensexview/indexview_new.aspx?index_Code=16&iname=BSE30#">BSE | SENSEX</a>
 */
@StockProvider(name = "BSE")
public class BSEStockDataHelper extends AbstractStockDataHelper {

    /**
     * <p>This URL is the BASE url for the SENSEX (BSE) </p>
     */
    private static final String STOCK_BASE_URL = "https://www.bseindia.com";

    /**
     * <p>This URL is used to get current SENSEX value with points difference and %age change </p>
     *
     * @param random: is random number of the format -- YYYYMDDHHMM
     */
    private static final String STOCK_CURRENT_VALUE = "/markets/Equity/SensexData.aspx?radn=#random";

    /**
     * <p>This URL is used to get data for 52 Week High and Low and All time High and Low </p>
     *
     * @param random: is random number of the format -- YYYYMDDHHMM
     */
    private static final String STOCK_HIGH_LOW_VALUE = "/SensexView/SensexViewbackPage.aspx?flag=SLIDER&indexcode=16&random=#random";

    /**
     * <p>This URL is used To get data with Previous close, Today Open, Today High, Today Low SENSEX values </p>
     *
     * @param random: is random number of the format -- YYYYMDDHHMM
     */
    private static final String STOCK_CURRENT_SUMMARY = "/SensexView/SensexViewbackPage.aspx?flag=INDEX&indexcode=16&random=#random";

    /**
     * <p>This URL is used to fetch the SENSEX data for 1 Day, 1 Month, 3 Months, 6 Months, 12 Months </p>
     *
     * @param flag: is the time frame to get the data for. 1(for 1 Day), 1M, 3M, 6M, 12M for Months
     * @param random: is random number of the format -- YYYYMDDHHMM
     */
    private static final String STOCK_DATA_SERIES = "/BSEGraph/Graphs/GetSensexViewData1.aspx?index=16&flag=#flag&random=#random";

    /**
     * <p>This URL is used to get the COMPANY NAME and COMPANY CODE </p>
     *
     * @param Type: type of the Market [Equity, Derivative etc]. EQ for Equity, DR for Derivative, MF for Mutual Funds and DB for Debt/Others
     * @param text: Security Name / Code / ID to search
     */
    private static final String STOCK_COMPANY_SEARCH_URL = "/SiteCache/1D/GetQuoteData.aspx?Type=#type&text=#text";

    @Override
    public Single<JsonObject> doExecute(final JsonObject data) throws ClassNotFoundException {
        final JsonObject _data = data.copy().getJsonObject("request_params");
        final String product = _data.getString("product", "");
        if (product.length() > 1) {
            return processProductData(_data, product);
        } else {
            final String function = _data.getString("function", "");
            final String timeframe = _data.getString("timeframe", "1");
            return processSensexData(function, timeframe);
        }
    }

    private Single<JsonObject> processProductData(final JsonObject data, final String product) throws ClassNotFoundException {
        IProductDataHelper helper = ProductDataProviderFactory.loadProvider(product);
        return helper.doExecute(data);

    }

    private Single<JsonObject> processSensexData(final String function, final String timeframe) {
        switch (function) {
            case "STOCK_CURRENT_VALUE":
                return doGetStockCurrentValue();
            case "STOCK_HIGH_LOW_VALUE":
                return doGetStockHighLowValue();
            case "STOCK_CURRENT_SUMMARY":
                return doGetStockCurrentSummary();
            case "STOCK_CURRENT_FULL_SUMMARY":
                return doGetStockCurrentFullSummary();
            case "STOCK_DATA_SERIES":
                return doGetStockDataSeries(timeframe);
            default:
                throw new NotImplementedException("No implementation found");
        }
    }

    public Single<JsonObject> doGetStockCurrentValue() {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", STOCK_BASE_URL, STOCK_CURRENT_VALUE.replace("#random", CommonUtil.getBSERandom()));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseStockCurrentValue(res));
    }

    public Single<JsonObject> doGetStockHighLowValue() {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", STOCK_BASE_URL, STOCK_HIGH_LOW_VALUE.replace("#random", CommonUtil.getBSERandom()));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseStockHighLowValue(res));
    }

    public Single<JsonObject> doGetStockCurrentSummary() {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", STOCK_BASE_URL, STOCK_CURRENT_SUMMARY.replace("#random", CommonUtil.getBSERandom()));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseStockCurrentSummary(res));
    }

    public Single<JsonObject> doGetStockDataSeries(final String timeframe) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    final String _url = STOCK_DATA_SERIES.replace("#random", CommonUtil.getBSERandom()).replace("#flag", timeframe);
                    $.resourceURL = CommonUtil.toURI("", STOCK_BASE_URL, _url);
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseStockDataSeries(res, timeframe));
    }


    public Single<JsonObject> doGetStockCurrentFullSummary() {
        return doGetStockHighLowValue()
                .flatMap(resp -> doGetStockDataSeries("1")
                        .flatMap(res -> {
                            JsonObject stockHighLow = resp.getJsonObject("data", new JsonObject());
                            JsonObject stockDataSeries = res.getJsonObject("data", new JsonObject());
                            stockDataSeries.put("all_time", stockHighLow.getJsonObject("all_time", new JsonObject()));
                            stockDataSeries.put("52_w", stockHighLow.getJsonObject("52_w", new JsonObject()));
                            return Single.just(res);
                        }));
    }

    private Single<JsonObject> doParseStockCurrentValue(final String data) {
        final JsonObject response = new JsonObject();
        final JsonObject element = new JsonObject();
        if (null != data) {
            final String[] value = data.split("@");
            final String isHigh = value[2].substring(0, 1);
            element.put("value", CommonUtil.toFloat(value[1]));
            element.put("points_difference", CommonUtil.toFloat(value[2].substring(1)));
            element.put("percentage_difference", CommonUtil.toFloat(value[3].substring(1)));
            element.put("is_high", "+".equalsIgnoreCase(isHigh) ? true : false);
            element.put("time", value[5]);
            element.put("status", getMarketStatus(Integer.parseInt(value[6])));
            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", element);
        return Single.just(response);
    }

    private Single<JsonObject> doParseStockHighLowValue(final String data) {
        final JsonObject response = new JsonObject();
        final JsonObject root = new JsonObject();
        if (null != data) {
            final String[] value = data.split("@");
            JsonObject element = new JsonObject();
            element.put("high", CommonUtil.toFloat(value[0].replace("bseindia$#$", "")));
            element.put("high_on", value[5]);
            element.put("low", CommonUtil.toFloat(value[1]));
            element.put("low_on", value[6]);
            root.put("all_time", element);

            element = new JsonObject();
            element.put("high", CommonUtil.toFloat(value[2]));
            element.put("high_on", value[7]);
            element.put("low", CommonUtil.toFloat(value[3]));
            element.put("low_on", value[8]);
            root.put("52_w", element);

            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", root);
        return Single.just(response);
    }

    private Single<JsonObject> doParseStockCurrentSummary(final String data) {
        final JsonObject response = new JsonObject();
        final JsonObject element = new JsonObject();
        if (null != data) {
            final String[] value = data.split("@");
            element.put("open", CommonUtil.toFloat(value[2]));
            element.put("high", CommonUtil.toFloat(value[3]));
            element.put("low", CommonUtil.toFloat(value[4]));
            element.put("current", CommonUtil.toFloat(value[5]));
            element.put("previous_close", CommonUtil.toFloat(value[6]));

            final String isHigh = value[7].substring(0, 1);
            element.put("points_difference", CommonUtil.toFloat(value[7].substring(1)));
            element.put("percentage_difference", CommonUtil.toFloat(value[8].substring(1)));
            element.put("is_high", "+".equalsIgnoreCase(isHigh) ? true : false);
            element.put("time", value[9].trim());
            element.put("status", getMarketStatus(Integer.parseInt(value[10])));
            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", element);
        return Single.just(response);
    }

    private Single<JsonObject> doParseStockDataSeries(final String data, final String timeframe) {
        final JsonObject response = new JsonObject();
        final JsonObject root = new JsonObject();
        if (null != data) {
            final String line1 = StringUtils.substringBefore(data, "#@#");
            final String[] axisData = line1.split(",");
            root.put("requested_date", axisData[0]);
            root.put("y_axis_start", CommonUtil.toInt(axisData[2]));
            root.put("y_axis_end", CommonUtil.toInt(axisData[3]));
            root.put("stock_name", axisData[4]);
            root.put("last_updated_time", axisData[6]);

            final String summary = StringUtils.substringAfter(data, "##@#");
            final Document doc = Jsoup.parse(summary);
            final Elements li = doc.getElementsByClass("prevclosevalue");
            float previousValue = CommonUtil.toFloat(li.get(0).text());
            float currentValue = CommonUtil.toFloat(axisData[5]);
            float pointsDifference = CommonUtil.formatFloat(currentValue - previousValue);

            root.put("open", CommonUtil.toFloat(li.get(1).text()));
            root.put("high", CommonUtil.toFloat(li.get(2).text()));
            root.put("low", CommonUtil.toFloat(li.get(3).text()));
            root.put("previous_close", previousValue);
            root.put("current", currentValue);
            root.put("points_difference", pointsDifference);
            root.put("percentage_difference", CommonUtil.formatFloat((pointsDifference * 100) / currentValue));
            root.put("is_high", pointsDifference > 0);

            final String toParse = StringUtils.substringBetween(data, "#@#", "##@#");
            final String[] timeSeries = toParse.split("#");
            final JsonArray array = new JsonArray();
            for (String value : timeSeries) {
                JsonObject element = new JsonObject();
                final String[] _values = value.split(",");
                element.put("time", _values[0].trim());
                if (_values.length == 2) {
                    element.put("value", CommonUtil.toFloat(_values[1]));
                } else if (_values.length == 3) {
                    element.put("value", CommonUtil.toFloat(_values[2]));
                }
                array.add(element);
            }
            root.put("series", array);
            root.put("timeframe", StringUtils.endsWith(timeframe, "D") ? timeframe : String.join("", timeframe, "D"));
            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", root);
        return Single.just(response);
    }

    private String getMarketStatus(int status) {
        switch (status) {
            case 1:
                return "Pre-open";
            case 2:
                return "Close";
            case 0:
                return "Open";
            default:
                return "";
        }
    }
}
