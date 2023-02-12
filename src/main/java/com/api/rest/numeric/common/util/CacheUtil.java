package com.api.rest.numeric.common.util;

import com.api.rest.numeric.domain.redis.SpringCacheProvider;

import java.util.Optional;

public abstract class CacheUtil {
    public final static String NUMERIC_PERCENTAGE_KEY = "numeric_percentage_key";
    public final static String LAST_NUMERIC_PERCENTAGE_KEY = "LAST_NUMERIC_PERCENTAGE_KEY";
    public final static int THIRTY_MINUTES = 1800;

    private static SpringCacheProvider cacheProvider;

    public static void setCacheProvider(SpringCacheProvider cacheProvider) {
        CacheUtil.cacheProvider = cacheProvider;
    }

    public static SpringCacheProvider getCacheProvider() {
        return CacheUtil.cacheProvider;
    }

    public static Optional<String> getKey(String key) {
        return CacheUtil.cacheProvider.get(key);
    }

    public static Boolean deleteKey(String key) {
        return CacheUtil.cacheProvider.delete(key);
    }

}
