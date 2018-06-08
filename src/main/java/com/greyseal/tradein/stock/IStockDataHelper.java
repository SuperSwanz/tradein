package com.greyseal.tradein.stock;

import com.greyseal.vertx.boot.httpclient.HttpClientResponse;
import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

public interface IStockDataHelper {
    public Single<JsonObject> doExecute(final JsonObject data) throws ClassNotFoundException;
}