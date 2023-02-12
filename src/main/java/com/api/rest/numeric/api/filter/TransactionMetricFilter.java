package com.api.rest.numeric.api.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Order(1)
@Slf4j
class TransactionMetricFilter implements Filter {

    private ContentTraceManager traceManager;

    public TransactionMetricFilter(ContentTraceManager traceManager) {
        this.traceManager = traceManager;
    }

    @SneakyThrows(ServletException.class)
    @Override
    public void init(FilterConfig filterConfig) { }

    @SneakyThrows({IOException.class, ServletException.class})
    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain filterChain
    ) {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(
            (HttpServletRequest) request, 1000);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(
            (HttpServletResponse) response);
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
            traceManager.registerTrace(
                    wrappedRequest,
                    wrappedResponse,
                    (HttpServletRequest) request,
                    (HttpServletResponse) response
            );
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
    }

    @Override
    public void destroy() {}
}

