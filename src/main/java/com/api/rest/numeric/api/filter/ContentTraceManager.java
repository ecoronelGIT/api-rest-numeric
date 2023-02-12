package com.api.rest.numeric.api.filter;

import com.api.rest.numeric.domain.model.NumericLogEntity;
import com.api.rest.numeric.domain.repository.NumericLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ContentTraceManager {
    private final List<String> filterUrls = Arrays.asList(
            "/prometheus",
            "/health",
            "/swagger-ui",
            "/swagger-resources",
            "/api-docs",
            "/springfox-swagger-ui",
            "/csrf");

    private NumericLogRepository numericLogRepository;

    public ContentTraceManager(NumericLogRepository numericLogRepository) {
        this.numericLogRepository = numericLogRepository;
    }

    public void registerTrace(ContentCachingRequestWrapper wrappedRequest,
                              ContentCachingResponseWrapper wrappedResponse,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            if(shouldFilter(request)) {
                NumericLogEntity numericLogEntity = new NumericLogEntity();
                numericLogEntity.setCreatedAt(LocalDateTime.now());
                numericLogEntity.setMethod(request.getMethod());
                numericLogEntity.setRequestHeaders(this.getRequestHeaders(request).toString());
                numericLogEntity.setResponseHeaders(this.getResponseHeaders(response).toString());
                numericLogEntity.setResponseStatus(response.getStatus());
                numericLogEntity.setUrl(request.getServletPath());
                numericLogEntity.setResponse(this.getResponseBody(wrappedResponse));
                numericLogEntity.setRequest(this.getRequestBody(wrappedRequest));
                this.numericLogRepository.save(numericLogEntity);
            }
        } catch (Exception ex) {
            log.error("Error saving trace log in database " + ex.getMessage());
        }
    }

    private boolean shouldFilter(HttpServletRequest request) {
        return filterUrls.stream().noneMatch(url -> request.getServletPath().contains(url));
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Iterator<String> iterator = request.getHeaderNames().asIterator();
        Map<String, String> map = new HashMap();
        while (iterator.hasNext()) {
            String next = iterator.next();
            map.put(next, request.getHeader(next));
        }
        return map;
    }

    private Map<String, String> getResponseHeaders(HttpServletResponse response) {
        Iterator<String> iterator = response.getHeaderNames().iterator();
        Map<String, String> map = new HashMap();
        while (iterator.hasNext()) {
            String next = iterator.next();
            map.put(next, response.getHeader(next));
        }
        return map;
    }


    private String getRequestBody(ContentCachingRequestWrapper wrappedRequest) {
        try {
            if (wrappedRequest.getContentLength() <= 0) {
                return null;
            }
            return new String(wrappedRequest.getContentAsByteArray(), 0,
                wrappedRequest.getContentLength(),
                wrappedRequest.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("Could not read cached request body: " + e.getMessage());
            return null;
        }

    }

    private String getResponseBody(ContentCachingResponseWrapper wrappedResponse) {
        try {
            if (wrappedResponse.getContentSize() <= 0) {
                return null;
            }
            return new String(wrappedResponse.getContentAsByteArray(), 0,
                wrappedResponse.getContentSize(),
                wrappedResponse.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("Could not read cached response body: " + e.getMessage());
            return null;
        }

    }

}
