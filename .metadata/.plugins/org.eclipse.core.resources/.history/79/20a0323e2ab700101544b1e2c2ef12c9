/**
 * 
 */
package com.common.utils;
/**
 * =================================================================================================
 *  ApiExecutionUtils
 * =================================================================================================
 * Utility class providing standardized executors for different types of operations:
 *
 * <ul>
 *   <li><b>ApiExecutor</b> → For API/business logic execution</li>
 *   <li><b>DbExecutor</b> → For database or transactional operations</li>
 *   <li><b>AsyncExecutor</b> → For background or asynchronous operations</li>
 * </ul>
 *
 * <p>This class helps ensure consistency, clean code, and centralized error handling
 * across all parts of the application — controllers, services, and repositories.</p>
 *
 * -------------------------------------------------------------------------------------------------
 * 🧩 <b>Example Usage</b>
 * -------------------------------------------------------------------------------------------------
 *
 * <pre>{@code
 * // Example 1️⃣ — API Execution (with validation)
 * ApiResponse<User> response = ApiExecutionUtils.ApiExecutor.processRequest(
 *     userRequest,
 *     req -> { if (req.getName() == null) throw new IllegalArgumentException("Name is required"); },
 *     () -> userService.createUser(userRequest),
 *     ApiResponse::success
 * );
 *
 * // Example 2️⃣ — Database Execution (safe query)
 * ApiResponse<UserEntity> dbResponse = ApiExecutionUtils.DbExecutor.runDbQuery(
 *     () -> userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User not found"))
 * );
 *
 * // Example 3️⃣ — Async Execution (background task)
 * CompletableFuture<ApiResponse<String>> asyncResponse =
 *     ApiExecutionUtils.AsyncExecutor.processAsyncRequest(
 *         "John",
 *         name -> { if (name.isEmpty()) throw new IllegalArgumentException("Invalid name"); },
 *         () -> "Hello, " + "John!",
 *         ApiResponse::success
 *     );
 *
 * asyncResponse.thenAccept(res -> System.out.println(res.getMessage()));
 * }</pre>
 *
 * -------------------------------------------------------------------------------------------------
 * ✅ <b>Benefits</b>:
 * <ul>
 *   <li>Centralized error handling</li>
 *   <li>Cleaner controller/service code</li>
 *   <li>Consistent API responses</li>
 *   <li>Reduced boilerplate (no repeated try-catch blocks)</li>
 *   <li>Supports synchronous and asynchronous flows</li>
 * </ul>
 * -------------------------------------------------------------------------------------------------
 */


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.models.ApiResponse;

public class ApiExecutionUtils {

	// ==============================================================================================
	// 1️⃣ API EXECUTOR
	// ==============================================================================================

	/**
	 * Provides reusable methods to handle API/business logic execution in a
	 * consistent manner.
	 * <p>
	 * Includes input validation, execution of business logic, and standardized
	 * response handling.
	 * </p>
	 */
	public static class ApiExecutor {
		
		private static final Logger logger = LoggerFactory.getLogger(ApiExecutor.class);

		/**
		 * Processes a complete API request with validation, execution, and optional
		 * custom response building.
		 *
		 * @param <I>             The input/request type.
		 * @param <O>             The output/response data type.
		 * @param request         Input request object (can be null).
		 * @param validator       Consumer to validate the input request (optional).
		 * @param executor        Supplier that executes business logic and returns a
		 *                        result.
		 * @param responseBuilder Optional function to build a custom ApiResponse.
		 * @return ApiResponse containing success or error information.
		 */
		public static <I, O> ApiResponse<O> processRequest(
				I request,
				Consumer<I> validator,
				Supplier<O> executor,
				Function<O, ApiResponse<O>> responseBuilder) {
			
			long startTime = System.currentTimeMillis();
			logger.info("API processRequest scheduled");
			try {
				if (validator != null)
					validator.accept(request);

				O data = (executor != null) ? executor.get() : null;

				logger.info("API processRequest completed in {} ms", System.currentTimeMillis() - startTime);
				return (responseBuilder != null)
						? responseBuilder.apply(data)
						: ApiResponse.success(data);
			} catch (Exception e) {
				
				logger.error("API processRequest failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
				return ApiResponse.error("API processing failed: " + e.getMessage());
			}
		}

		/**
		 * Simplified overload without a custom response builder.
		 */
		public static <I, O> ApiResponse<O> processRequest(I request, Consumer<I> validator, Supplier<O> executor) {
			return processRequest(request, validator, executor, null);
		}

		/**
		 * Simplified overload for executing logic without validation or custom response.
		 */
		public static <O> ApiResponse<O> processRequest(Supplier<O> executor) {
			return processRequest(null, null, executor, null);
		}

		/**
		 * Executes a task that performs an action but does not return data.
		 * <p>
		 * Useful for DELETE operations or actions without a response body.
		 * </p>
		 */
		public static ApiResponse<Void> processAction(Runnable executor) {
			long startTime = System.currentTimeMillis();
			logger.info("API processAction scheduled");
			
			try {
				if (executor != null)
					executor.run();
				
				logger.info("API processAction completed in {} ms", System.currentTimeMillis() - startTime);

				return ApiResponse.success(null);
			} catch (Exception e) {
				
				logger.error("API processAction failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
				return ApiResponse.error("Action failed: " + e.getMessage());
			}
		}

		/**
		 * Executes a task with a fallback value in case of failure.
		 * <p>
		 * Commonly used to return a default response when an exception occurs.
		 * </p>
		 */
		public static <O> ApiResponse<O> processOrDefault(Supplier<O> executor, O defaultValue) {
			long startTime = System.currentTimeMillis();
			logger.info("API processOrDefault scheduled");
			
			try {
				O data = (executor != null) ? executor.get() : defaultValue;
				
				logger.info("API processOrDefault completed in {} ms", System.currentTimeMillis() - startTime);

				return ApiResponse.success(data);
			} catch (Exception e) {
				
				logger.error("API processOrDefault failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
				return ApiResponse.error("Fallback used due to error: " + e.getMessage());
			}
		}
	}

	// ==============================================================================================
	// 2️⃣ DATABASE EXECUTOR
	// ==============================================================================================

	/**
	 * Provides standardized methods for executing database operations safely and
	 * consistently.
	 * <p>
	 * This includes CRUD actions, transactional logic, and fallback behavior.
	 * </p>
	 */
	public static class DbExecutor {
		
		private static final Logger logger = LoggerFactory.getLogger(DbExecutor.class);

		/**
		 * Executes a database operation (query or command) and returns a response.
		 *
		 * @param <O>         The output data type.
		 * @param dbOperation Supplier representing a database operation.
		 * @return ApiResponse with the query result or error details.
		 */
		public static <O> ApiResponse<O> runDbQuery(Supplier<O> dbOperation) {
			long startTime = System.currentTimeMillis();
			logger.info("DB runDbQuery scheduled");
			
			try {
				O result = (dbOperation != null) ? dbOperation.get() : null;
				
				logger.info("DB runDbQuery completed in {} ms", System.currentTimeMillis() - startTime);
				return ApiResponse.success(result);
			} catch (Exception e) {
				
				logger.error("DB runDbQuery failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
				return ApiResponse.error("Database query failed: " + e.getMessage());
			}
		}

		/**
		 * Executes a database action that does not return data (e.g., INSERT, UPDATE,
		 * DELETE).
		 */
		public static ApiResponse<Void> runDbAction(Runnable dbOperation) {
			long startTime = System.currentTimeMillis();
			logger.info("DB runDbAction scheduled");
			try {
				if (dbOperation != null)
					dbOperation.run();
				
				logger.info("DB runDbAction completed in {} ms", System.currentTimeMillis() - startTime);
				return ApiResponse.success(null);
			} catch (Exception e) {
				
				logger.error("DB runDbAction failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
				return ApiResponse.error("Database action failed: " + e.getMessage());
			}
		}

		/**
		 * Executes a database query with a fallback result in case of error.
		 */
		public static <O> ApiResponse<O> runDbQueryOrFallback(Supplier<O> dbOperation, O fallback) {
			long startTime = System.currentTimeMillis();
			logger.info("DB runDbQueryOrFallback scheduled");
			
			try {
				O result = (dbOperation != null) ? dbOperation.get() : fallback;
				
				logger.info("DB runDbQueryOrFallback completed in {} ms", System.currentTimeMillis() - startTime);
				return ApiResponse.success(result);
			} catch (Exception e) {
				
				logger.error("DB runDbQueryOrFallback failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
				return ApiResponse.error("Fallback used due to DB error: " + e.getMessage());
			}
		}
	}

	// ==============================================================================================
	// 3️⃣ ASYNC EXECUTOR
	// ==============================================================================================

	/**
	 * Provides support for executing asynchronous tasks using a shared thread pool.
	 * <p>
	 * Enables non-blocking operations such as background jobs or async service calls
	 * with consistent ApiResponse handling.
	 * </p>
	 */
	public static class AsyncExecutor {
		private static final Logger logger = LoggerFactory.getLogger(AsyncExecutor.class);

		/** Thread pool for background asynchronous task execution. */
		private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

		/**
		 * Runs an asynchronous task and returns a CompletableFuture of ApiResponse.
		 *
		 * @param <O>       The output/response data type.
		 * @param asyncTask Supplier that provides the async task logic.
		 * @return CompletableFuture containing ApiResponse with result or error.
		 */
		public static <O> CompletableFuture<ApiResponse<O>> runAsync(Supplier<O> asyncTask) {
			long startTime = System.currentTimeMillis();
			logger.info("Async task scheduled");
			
			return CompletableFuture.supplyAsync(() -> {
				try {
					O data = (asyncTask != null) ? asyncTask.get() : null;
					
					logger.info("Async task completed in {} ms", System.currentTimeMillis() - startTime);
					return ApiResponse.success(data);
				} catch (Exception e) {
					
					logger.error("Async task failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
					return ApiResponse.error("Async task failed: " + e.getMessage());
				}
			}, executorService);
		}

		/**
		 * Processes an asynchronous API request with validation and optional custom
		 * response building.
		 *
		 * @param <I>             The input/request type.
		 * @param <O>             The output/response data type.
		 * @param request         Input request object.
		 * @param validator       Consumer to validate the request (optional).
		 * @param asyncTask       Supplier that performs async logic.
		 * @param responseBuilder Optional custom ApiResponse builder.
		 * @return CompletableFuture of ApiResponse containing success or error.
		 */
		public static <I, O> CompletableFuture<ApiResponse<O>> processAsyncRequest(
				I request,
				Consumer<I> validator,
				Supplier<O> asyncTask,
				Function<O, ApiResponse<O>> responseBuilder) {
			long startTime = System.currentTimeMillis();
			logger.info("Async request scheduled");
			return CompletableFuture.supplyAsync(() -> {
				try {
					if (validator != null)
						validator.accept(request);

					O data = (asyncTask != null) ? asyncTask.get() : null;
					logger.info("Async request completed in {} ms", System.currentTimeMillis() - startTime);
					return (responseBuilder != null)
							? responseBuilder.apply(data)
							: ApiResponse.success(data);
				} catch (Exception e) {
					
					logger.error("Async request failed after {} ms: {}", System.currentTimeMillis() - startTime, e.getMessage(), e);
					return ApiResponse.error("Async processing failed: " + e.getMessage());
				}
			}, executorService);
		}

		/**
		 * Gracefully shuts down the shared asynchronous executor service.
		 * <p>
		 * Should be called during application shutdown to release resources.
		 * </p>
		 */
		public static void shutdown() {
			executorService.shutdown();
			logger.info("Async Executor service shutdown");
		}
	}
}
