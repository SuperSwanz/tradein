package com.greyseal.tradein.market;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;

public interface IProductDataHelper {
    public Single<JsonObject> doExecute(final JsonObject data);
}
