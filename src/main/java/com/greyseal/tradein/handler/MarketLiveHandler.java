package com.greyseal.tradein.handler;

import com.greyseal.tradein.stock.IStockDataHelper;
import com.greyseal.tradein.stock.StockProviderFactory;
import com.greyseal.vertx.boot.Constant.MediaType;
import com.greyseal.vertx.boot.annotation.RequestMapping;
import com.greyseal.vertx.boot.handler.BaseHandler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.ext.web.RoutingContext;

public class MarketLiveHandler extends BaseHandler {
    public MarketLiveHandler(final Vertx vertx) {
        super(vertx);
    }

    @Override
    @RequestMapping(path = "/live", method = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void handle(RoutingContext event) {
        try {
            final String providerName = event.request().getParam("stock");
            IStockDataHelper helper = StockProviderFactory.loadProvider(providerName);
            JsonObject json = new JsonObject();
            json.put("request_params", getRequestParams(event.request().params()));
            json.put("request_body", event.getBody().length() > 0 ? event.getBodyAsJson() : new JsonObject());
            helper.doExecute(json)
                    .subscribe(response -> {
                        try {
                            event.setBody(Buffer.buffer(response.toString()));
                            event.next();
                        } catch (Exception ex) {
                            event.fail(ex);
                        }
                    }, error -> {
                        event.fail(error);
                    });
        } catch (Exception ex) {
            event.fail(ex);
        }
    }


    private JsonObject getRequestParams(final MultiMap requestParams) {
        JsonObject _params = new JsonObject();
        if (requestParams != null) {
            for (String key : requestParams.names()) {
                _params.put(key, requestParams.get(key));
            }
        }
        return _params;
    }
}