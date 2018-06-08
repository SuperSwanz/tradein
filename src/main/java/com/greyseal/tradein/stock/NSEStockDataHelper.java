package com.greyseal.tradein.stock;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.NotImplementedException;

@StockProvider(name = "NSE")
public class NSEStockDataHelper extends AbstractStockDataHelper {


    @Override
    public Single<JsonObject> doExecute(JsonObject data) {
        throw new NotImplementedException("Method not implemented");
    }
}
