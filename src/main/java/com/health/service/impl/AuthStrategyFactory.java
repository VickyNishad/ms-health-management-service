package com.health.service.impl;

import com.health.service.AuthStrategy;
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
