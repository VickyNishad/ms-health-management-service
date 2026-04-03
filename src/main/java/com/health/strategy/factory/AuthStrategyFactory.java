package com.health.strategy.factory;

import com.health.strategy.AuthStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthStrategyFactory {

    @Autowired
    private Map<String,AuthStrategy> strategies;

    public AuthStrategy getAuthStrategy(String type) {
        return strategies.get(type);
    }
}
