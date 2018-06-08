package com.greyseal.tradein.market;

import com.greyseal.tradein.stock.IStockDataHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class ProductDataProviderFactory {
    private static Map<String, Class<IProductDataHelper>> marketProviderMapping = new HashMap<>();
    private static Set<ProductDataProvider> apiProviders = new HashSet<>();

    static {
        final Reflections reflections = new Reflections("com.greyseal.tradein.market");
        final Set<Class<?>> clazzes = reflections.getTypesAnnotatedWith(ProductDataProvider.class);
        if (null != clazzes && !clazzes.isEmpty()) {
            clazzes.forEach(x -> {
                ProductDataProvider dataProvider = x.getAnnotation(ProductDataProvider.class);
                if (null != dataProvider && IProductDataHelper.class.isAssignableFrom(x)) {
                    apiProviders.add(dataProvider);
                    marketProviderMapping.put(dataProvider.name().toUpperCase(), (Class<IProductDataHelper>) x);
                }
            });
        }
    }

    public static IProductDataHelper loadProvider(final String name) throws ClassNotFoundException {
        Class<IProductDataHelper> clazz = null;
        IProductDataHelper apiProvider = null;
        try {
            clazz = marketProviderMapping.get(name.toUpperCase());
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
