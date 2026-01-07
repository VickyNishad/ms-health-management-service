package com.common.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.common.models.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Centralized HTTP‐client utility for invoking external APIs in a consistent, reusable way.
 * Supports synchronous and asynchronous invocation of HTTP endpoints with:
 * <ul>
 *   <li>HTTP methods: GET, POST, PUT, PATCH, DELETE</li>
 *   <li>Query parameters and request bodies (JSON or other payloads)</li>
 *   <li>Custom headers (Authorization, Content‑Type, API keys etc.)</li>
 *   <li>Timeouts and configurable retry logic with exponential back‑off</li>
 * </ul>
 *
 * <p><strong>Usage examples:</strong></p>
 * <pre>{@code
 * // Synchronous GET
 * ApiResponse<String> response = ExternalApiClient.request(
 *     "https://api.example.com/users",
 *     "GET",
 *     Map.of("Authorization", "Bearer MY_TOKEN"),
 *     Map.of("userId", "42"),
 *     null,
 *     2
 * );
 *
 * // Synchronous POST with body + query params
 * ApiResponse<String> postResp = ExternalApiClient.request(
 *     "https://api.example.com/users",
 *     "POST",
 *     Map.of("Content‑Type", "application/json", "Authorization", "Bearer MY_TOKEN"),
 *     Map.of("ref", "signup"),
 *     "{\"name\":\"John\",\"email\":\"john@example.com\"}",
 *     3
 * );
 *
 * // Asynchronous request
 * ExternalApiClient.requestAsync(
 *     "https://api.example.com/data",
 *     "GET",
 *     Map.of("Authorization", "Bearer MY_TOKEN"),
 *     Map.of("type", "summary"),
 *     null,
 *     1
 * ).thenAccept(asyncResp -> {
 *     if (asyncResp.isSuccess()) {
 *         System.out.println("Async data: " + asyncResp.getData());
 *     } else {
 *         System.err.println("Async error: " + asyncResp.getMessage());
 *     }
 * });
 * }</pre>
 *
 * <p><strong>Note on retry logic:</strong>
 * Retries are attempted only for transient errors (server errors 5xx, or status 429/503 indicating rate‑limit/back‑off).
 * Client errors (4xx other than 429) are not retried. Exponential back‑off is applied between attempts.
 * </p>
 *
 * <p><strong>Future improvements:</strong>
 * <ul>
 *   <li>Dynamic timeout per request</li>
 *   <li>Jitter + more sophisticated back‑off strategy</li>
 *   <li>Circuit breaker integration</li>
 *   <li>Automatic JSON (de)serialization of request/response bodies</li>
 * </ul>
 * </p>
 */
public class ExternalApiClient {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiClient.class);

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Executes an HTTP request synchronously, with retry logic for transient failures.
     *
     * @param url         The target external API endpoint.
     * @param method      HTTP method (GET, POST, PUT, PATCH, DELETE).
     * @param headers     Map of HTTP headers; may be {@code null}.
     * @param queryParams Map of query parameter names to values; may be {@code null}.
     * @param body        Request payload (for POST/PUT/PATCH); may be {@code null}.
     * @param maxRetries  Maximum number of retry attempts for transient failures.
     * @return ApiResponse<String> with the response body on success, or error details on failure.
     */
    public static ApiResponse<String> request(
            String url,
            String method,
            Map<String, String> headers,
            Map<String, String> queryParams,
            String body,
            int maxRetries) {

        int attempt = 0;
        long backoff = 500L; // initial back‑off in milliseconds

        while (true) {
            attempt++;
            try {
                String fullUrl = buildUrlWithParams(url, queryParams);
                HttpRequest.Builder builder = HttpRequest.newBuilder()
                        .uri(URI.create(fullUrl))
                        .timeout(Duration.ofSeconds(10));

                if (headers != null) {
                    headers.forEach(builder::header);
                }

                String upperMethod = method.toUpperCase();
                HttpRequest.BodyPublisher publisher = HttpRequest.BodyPublishers.noBody();

                if ("POST".equals(upperMethod) || "PUT".equals(upperMethod) || "PATCH".equals(upperMethod)) {
                    publisher = HttpRequest.BodyPublishers.ofString(body != null ? body : "");
                    builder.header("Content‑Type", headers != null && headers.containsKey("Content‑Type")
                            ? headers.get("Content‑Type")
                            : "application/json");
                }

                builder.method(upperMethod, publisher);

                logger.info("Attempt {} for URL: {} with method {}", attempt, fullUrl, upperMethod);
                HttpResponse<String> response = client.send(builder.build(), HttpResponse.BodyHandlers.ofString());
                int status = response.statusCode();
                String responseBody = response.body();

                if (status >= 200 && status < 300) {
                    logger.info("Request succeeded (status {}) in attempt {}", status, attempt);
                    return ApiResponse.success(responseBody);
                }

                // Retry logic for transient errors
                if ((status == 429 || status == 503) && attempt <= maxRetries) {
                    String retryAfter = response.headers().firstValue("Retry‑After").orElse(null);
                    long waitMillis = backoff;
                    if (retryAfter != null) {
                        try {
                            waitMillis = Long.parseLong(retryAfter) * 1000L;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    logger.warn("Transient status {} received. Retry‑After: {} ms. Retrying attempt {}/{}", status, waitMillis, attempt, maxRetries);
                    Thread.sleep(waitMillis);
                    backoff *= 2;
                    continue;
                }

                if (status >= 500 && status < 600 && attempt <= maxRetries) {
                    logger.warn("Server error status {}. Retrying after {} ms. attempt {}/{}", status, backoff, attempt, maxRetries);
                    Thread.sleep(backoff);
                    backoff *= 2;
                    continue;
                }

                // Non‑retryable response (client error etc.)
                logger.error("Request failed with non‑retryable status {}: {}", status, responseBody);
                return ApiResponse.error("HTTP Error: " + status + " - " + responseBody);

            } catch (IOException | InterruptedException e) {
                if (attempt > maxRetries) {
                    logger.error("Request failed after {} attempts: {}", attempt, e.getMessage(), e);
                    Thread.currentThread().interrupt();
                    return ApiResponse.error("Request failed after retries: " + e.getMessage());
                }
                logger.warn("Exception on attempt {}: {}. Retrying after {} ms", attempt, e.getMessage(), backoff, e);
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException ie) {
                    logger.error("Retry interrupted: {}", ie.getMessage(), ie);
                    Thread.currentThread().interrupt();
                    return ApiResponse.error("Retry interrupted: " + ie.getMessage());
                }
                backoff *= 2;
            }
        }
    }

    /**
     * Executes an HTTP request asynchronously. Internally delegates to {@link #request(...)}.
     *
     * @param url         The target external API endpoint.
     * @param method      HTTP method.
     * @param headers     Map of HTTP headers; may be {@code null}.
     * @param queryParams Map of query parameter names to values; may be {@code null}.
     * @param body        Request payload (for POST/PUT/PATCH); may be {@code null}.
     * @param maxRetries  Maximum number of retry attempts.
     * @return A CompletableFuture that will complete with ApiResponse when done.
     */
    public static CompletableFuture<ApiResponse<String>> requestAsync(
            String url,
            String method,
            Map<String, String> headers,
            Map<String, String> queryParams,
            String body,
            int maxRetries) {

        return CompletableFuture.supplyAsync(
                () -> request(url, method, headers, queryParams, body, maxRetries),
                executorService
        );
    }

    /**
     * Builds a URL by appending URL‑encoded query parameters if provided.
     *
     * @param url         The base URL without query parameters.
     * @param queryParams Map of parameter names to values; may be {@code null} or empty.
     * @return A full URL string including encoded query parameters (if any).
     */
    private static String buildUrlWithParams(String url, Map<String, String> queryParams) {
        if (queryParams == null || queryParams.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        sb.append(url.contains("?") ? "&" : "?");
        queryParams.forEach((k, v) -> {
            if (v != null) {
                sb.append(URLEncoder.encode(k, StandardCharsets.UTF_8))
                        .append("=")
                        .append(URLEncoder.encode(v, StandardCharsets.UTF_8))
                        .append("&");
            }
        });
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Shuts down the internal executor service used for asynchronous requests.
     * Should be called during application shutdown to release resources.
     */
    public static void shutdown() {
        executorService.shutdown();
        logger.info("Executor service for ExternalApiClient shutdown requested.");
    }
}
