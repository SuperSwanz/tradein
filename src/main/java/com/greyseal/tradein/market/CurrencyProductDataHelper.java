package com.greyseal.tradein.market;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

@ProductDataProvider(name = "Currency")
public class CurrencyProductDataHelper extends AbstractProductDataHelper {


    @Override
    public Single<JsonObject> doExecute(JsonObject data) {
        return null;
    }
}