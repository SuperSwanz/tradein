package com.greyseal.tradein.stock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class StockProviderFactory {
    private static Map<String, Class<IStockDataHelper>> stockProvidersMapping = new HashMap<>();
    private static Set<StockProvider> stockProviders = new HashSet<>();

    static {
        final Reflections reflections = new Reflections("com.greyseal.tradein.stock");
        final Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(StockProvider.class);
        if (null != clazzes && !clazzes.isEmpty()) {
            clazzes.forEach(x -> {
                StockProvider dataProvider = x.getAnnotation(StockProvider.class);
                if (null != dataProvider && IStockDataHelper.class.isAssignableFrom(x)) {
                    stockProviders.add(dataProvider);
                    stockProvidersMapping.put(dataProvider.name().toUpperCase(), (Class<IStockDataHelper>) x);
                }
            });
        }
    }

    public static IStockDataHelper loadProvider(final String name) throws ClassNotFoundException {
        Class<IStockDataHelper> clazz = null;
        IStockDataHelper apiProvider = null;
        try {
            clazz = stockProvidersMapping.get(name.toUpperCase());
            apiProvider = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (null == apiProvider) {
            throw new ClassNotFoundException(String.join(" ", "Class ", name, " not found"));
        }
        return apiProvider;
    }
}
