package com.api.rest.numeric.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class PropertyOverrideContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            configurableApplicationContext,
            "resilience4j.ratelimiter.instances.rateLimiterApi.limit-for-period=1"
        );

        TestPropertySourceUtils.addPropertiesFilesToEnvironment(
            configurableApplicationContext, "application.properties");
    }
}
