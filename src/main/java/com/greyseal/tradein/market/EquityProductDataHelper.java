package com.greyseal.tradein.market;

import com.greyseal.tradein.util.CommonUtil;
import com.greyseal.vertx.boot.httpclient.HttpRequestProcessor;
import io.reactivex.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@ProductDataProvider(name = "EQ")
public class EquityProductDataHelper extends AbstractProductDataHelper {
    private static final String BASE_URL = "https://www.bseindia.com";
    private static final String COMPANY_SEARCH_URL = "/SiteCache/1D/GetQuoteData.aspx?Type=EQ&text=#text";
    private static final String COMPANY_INFO_URL = "/SiteCache/1D/CompanyHeader.aspx?Type=EQ&text=#text";
    private static final String EQ_CURRENT_DATA_URL = "/stock-share-price/SiteCache/EQHeaderData.aspx?text=#text";
    private static final String EQ_Q_RESULTS_URL = "/stock-share-price/SiteCache/TabResult.aspx?type=results&text=#text";
    private static final String EQ_HIGH_LOW_VALUE = "/stock-share-price/SiteCache/52WeekHigh.aspx?Type=EQ&text=#text";
    private static final String EQ_DATA_SERIES_URL = "/BSEGraph/Graphs/GetStockReachVolPriceDatav1.aspx?index=#text";


    @Override
    public Single<JsonObject> doExecute(JsonObject data) {
        final JsonObject _data = data.copy();
        final String function = _data.getString("function", "");
        long companyCode;
        switch (function) {
            case "STOCK_COMPANY_SEARCH":
                final String toSearch = _data.getString("name", "");
                return doGetCompanies(toSearch);
            case "STOCK_COMPANY_INFO":
                companyCode = Long.parseLong(_data.getString("code", ""));
                return doGetCompanyInfo(companyCode);
            case "EQ_CURRENT_DATA":
                companyCode = Long.parseLong(_data.getString("code", ""));
                return doGetCurrentData(companyCode);
            case "EQ_Q_RESULTS":
                companyCode = Long.parseLong(_data.getString("code", ""));
                return doGetQResults(companyCode);
            case "EQ_HIGH_LOW_VALUE":
                companyCode = Long.parseLong(_data.getString("code", ""));
                return doGetHighLowValue(companyCode);
            case "EQ_DATA_SERIES":
                companyCode = Long.parseLong(_data.getString("code", ""));
                return doGetDataSeries(companyCode);
            default:
                throw new NotImplementedException("No implementation found");
        }
    }

    public Single<JsonObject> doGetCompanies(final String toSearch) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", BASE_URL, COMPANY_SEARCH_URL.replace("#text", toSearch));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseGetCompanies(res));
    }

    public Single<JsonObject> doGetCompanyInfo(final long companyCode) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", BASE_URL, COMPANY_INFO_URL.replace("#text", companyCode + ""));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseCompanyInfo(res));
    }

    public Single<JsonObject> doGetCurrentData(final long companyCode) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", BASE_URL, EQ_CURRENT_DATA_URL.replace("#text", companyCode + ""));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseCurrentData(res));
    }

    public Single<JsonObject> doGetQResults(final long companyCode) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", BASE_URL, EQ_Q_RESULTS_URL.replace("#text", companyCode + ""));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseQResults(res));
    }

    public Single<JsonObject> doGetHighLowValue(final long companyCode) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", BASE_URL, EQ_HIGH_LOW_VALUE.replace("#text", companyCode + ""));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseHighLowValue(res));
    }

    public Single<JsonObject> doGetDataSeries(final long companyCode) {
        return new HttpRequestProcessor()
                .with($ -> {
                    $.httpMethod = HttpMethod.GET;
                    $.resourceURL = CommonUtil.toURI("", BASE_URL, EQ_HIGH_LOW_VALUE.replace("#text", companyCode + ""));
                })
                .buildRequest()
                .doExecute()
                .map(buffer -> buffer.getBody().toString("UTF-8"))
                .flatMap(res -> doParseDataSeries(res));
    }

    private Single<JsonObject> doParseGetCompanies(final String data) {
        final Document doc = Jsoup.parse(data);
        final Elements li = doc.select("li");
        final JsonObject response = new JsonObject();
        final JsonObject element = new JsonObject();
        final JsonArray array = new JsonArray();
        if (null != li) {
            for (int i = 0; i < li.size(); i++) {
                final JsonObject result = new JsonObject();
                Elements leftSpan = li.get(i).getElementsByClass("leftspan");
                Elements rightSpan = li.get(i).getElementsByClass("rightspan");
                Element idHref = li.get(i).select("a").first();
                String[] urlParams = idHref.attr("abs:href").split("/");
                result.put("code", rightSpan.text().trim());
                result.put("name", leftSpan.text().trim());
                result.put("id", CommonUtil.toLong(urlParams[urlParams.length - 1]));
                array.add(result);
            }
            element.put("companies", array);
            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", element);
        return Single.just(response);
    }

    private Single<JsonObject> doParseCompanyInfo(final String data) {
        final JsonObject response = new JsonObject();
        final JsonObject element = new JsonObject();
        if (null != data) {
            final Document doc = Jsoup.parse(data);
            final Element table = doc.getElementById("tblEQ");
            final Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                Element row = rows.get(i);
                Elements cols = row.select("td");
                for (int j = 1; j < cols.size(); j++) {
                    if (j == 2) {
                        element.put("security_id", cols.get(j).text());
                    }
                    if (j == 6) {
                        element.put("group_index", cols.get(j).text());
                    }
                    if (j == 10) {
                        element.put("face_value", CommonUtil.toFloat(cols.get(j).text()));
                    }
                    if (j == 14) {
                        element.put("security_code", CommonUtil.toInt(cols.get(j).text()));
                    }
                    if (j == 18) {
                        element.put("isin", cols.get(j).text());
                    }
                    if (j == 22) {
                        element.put("industry", cols.get(j).text());
                    }
                }
            }
            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", element);
        return Single.just(response);
    }

    private Single<JsonObject> doParseCurrentData(final String data) {
        final JsonObject response = new JsonObject();
        final JsonObject element = new JsonObject();
        if (null != data) {
            final String dateTime = StringUtils.substringBetween(data, "BSE##B#", "@");
            final String toParse = StringUtils.substringAfterLast(data, "#");
            final String[] array = toParse.split(",");
            final float previousClose = CommonUtil.toFloat(array[0]);
            final float current = CommonUtil.toFloat(array[4]);
            final float pointsDifference = CommonUtil.formatFloat(current - previousClose);
            final float percentDifference = CommonUtil.formatFloat((pointsDifference * 100) / current);

            element.put("category", array[5]);
            element.put("previous_close", previousClose);
            element.put("open", CommonUtil.toFloat(array[1]));
            element.put("high", CommonUtil.toFloat(array[2]));
            element.put("low", CommonUtil.toFloat(array[3]));
            element.put("current", current);
            element.put("points_difference", pointsDifference);
            element.put("percentage_difference", percentDifference);
            element.put("is_high", pointsDifference > 0 ? true : false);
            element.put("date_time", dateTime);
            response.put("has_data", true);
        } else {
            response.put("has_data", false);
        }
        response.put("data", element);
        return Single.just(response);
    }

    private Single<JsonObject> doParseQResults(final String data) {
        final JsonObject response = new JsonObject();
        final JsonArray q_results = new JsonArray();
        final JsonArray q_values = new JsonArray();

        if (null != data) {
            final Document doc = Jsoup.parse(data);
            final Elements tables = doc.select("table");
            final Element table = tables.get(2);
            final Elements head = table.getElementsByClass("indextabhead");
            final Elements text = table.getElementsByClass("newseoscriptext");
            final Elements values = table.getElementsByClass("newseoscripfig");

            int x = 0, y = 0;
            for (int j = 0; j < values.size() / 6; j++) {
                y = x;
                final JsonArray elements = new JsonArray();
                for (int i = 0; i < text.size(); i++) {
                    final JsonObject xElement = new JsonObject();
                    xElement.put("name", text.get(i).text());
                    xElement.put("value", fillQResultValues(values, x));
                    x = x + 3;
                    elements.add(xElement);
                }
                x = y + 1;
                q_values.add(elements);
            }
            for (int k = 1; k < head.size(); k++) {
                final JsonObject element = new JsonObject();
                element.put("q_time", head.get(k).text());
                element.put("values", q_values.getJsonArray(k - 1));
                q_results.add(element);
            }
        } else {
            response.put("has_data", false);
        }
        JsonObject json = new JsonObject();
        json.put("q_results", q_results);
        response.put("data", json);
        return Single.just(response);
    }

    private String fillQResultValues(final Elements values, int index) {
        return values.get(index).text();
    }

    private Single<JsonObject> doParseHighLowValue(final String data) {
        final JsonObject response = new JsonObject();
        final JsonArray root = new JsonArray();
        if (null != data) {
            final Document doc = Jsoup.parse(data);
            final Elements tables = doc.select("table");
            final Element table = tables.get(1);
            final Elements head = table.getElementsByClass("newseoscriptext");
            final Elements text = table.getElementsByClass("newseoscripfig");


            for (int i = 0; i < head.size(); i++) {
                final JsonObject element = new JsonObject();
                final String name = head.get(i).text();
                final String value = text.get(i).text();
                element.put("name", name);
                if ("52".equalsIgnoreCase(name.substring(0, 2))) {
                    element.put("value", CommonUtil.toFloat(StringUtils.substringBefore(value, "(")));
                    element.put("date", StringUtils.substringBetween(value, "(", ")"));
                } else {
                    element.put("value", value);
                }
                root.add(element);
            }
        } else {
            response.put("has_data", false);
        }
        response.put("data", new JsonObject().put("values", root));
        return Single.just(response);
    }

    private Single<JsonObject> doParseDataSeries(final String data) {
        final JsonObject response = new JsonObject();
        final JsonArray root = new JsonArray();
        if (null != data) {
            final Document doc = Jsoup.parse(data);
            final Elements tables = doc.select("table");
            final Element table = tables.get(1);
            final Elements head = table.getElementsByClass("newseoscriptext");
            final Elements text = table.getElementsByClass("newseoscripfig");


            for (int i = 0; i < head.size(); i++) {
                final JsonObject element = new JsonObject();
                final String name = head.get(i).text();
                final String value = text.get(i).text();
                element.put("name", name);
                if ("52".equalsIgnoreCase(name.substring(0, 2))) {
                    element.put("value", CommonUtil.toFloat(StringUtils.substringBefore(value, "(")));
                    element.put("date", StringUtils.substringBetween(value, "(", ")"));
                } else {
                    element.put("value", value);
                }
                root.add(element);
            }
        } else {
            response.put("has_data", false);
        }
        response.put("data", new JsonObject().put("values", root));
        return Single.just(response);
    }
}