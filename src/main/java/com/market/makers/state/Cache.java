package com.market.makers.state;

import com.market.makers.StartMarketMakers;
import com.market.makers.loaders.SecurityDataLoader;
import com.market.makers.model.Security;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Cache {

    private static Cache instance = new Cache();
    private Map<String, Security> securityDataMap = new HashMap<>();

    private Cache() {

    }

    public static Cache getInstance() {
        return instance;
    }

    public void loadSecurityData(final Properties properties){
        SecurityDataLoader.loadSecurityData(properties);
    }

    public void addSecurity(final Security security) {
        securityDataMap.putIfAbsent(security.getSecurityName(), security);
    }

    public Security getSecurity(final String ticker) {
        return securityDataMap.get(ticker);
    }

    public Collection<Security> getAllSecurities(){
        return securityDataMap.values();
    }
}
