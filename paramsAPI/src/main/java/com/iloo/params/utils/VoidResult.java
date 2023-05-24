package com.iloo.params.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.iloo.params.exceptions.VoidResultException;

/**
 * Represents the result of a void operation, indicating whether it was
 * successful or not, and optionally storing an error message and an exception.
 */
public final class VoidResult {

	/**
	 * The success variable.
	 */
	private final boolean isSuccess;

	/**
	 * The error message.
	 */
	private final String errorMessage;

	/**
	 * The thrown exception.
	 */
	private final Throwable exception;

	private VoidResult(boolean isSuccess, String errorMessage, Throwable exception) {
		this.isSuccess = isSuccess;
		this.errorMessage = errorMessage;
		this.exception = exception;
	}

	/**
	 * Creates a successful VoidResult.
	 *
	 * @return A successful VoidResult.
	 */
	public static VoidResult ok() {
		return new VoidResult(true, null, null);
	}

	/**
	 * Creates an unsuccessful VoidResult with the given error message.
	 *
	 * @param errorMessage The error message.
	 * @return An unsuccessful VoidResult with the error message.
	 */
	public static VoidResult error(String errorMessage) {
		return new VoidResult(false, errorMessage, null);
	}

	/**
	 * Creates an unsuccessful VoidResult with the given exception.
	 *
	 * @param exception The exception.
	 * @return An unsuccessful VoidResult with the exception.
	 */
	public static VoidResult error(Throwable exception) {
		return new VoidResult(false, null, exception);
	}

	/**
	 * Creates an unsuccessful VoidResult with the given error message and
	 * exception.
	 *
	 * @param errorMessage The error message.
	 * @param exception    The exception.
	 * @return An unsuccessful VoidResult with the error message and exception.
	 */
	public static VoidResult error(String errorMessage, Throwable exception) {
		return new VoidResult(false, errorMessage, exception);
	}

	/**
	 * Checks if the operation was successful.
	 *
	 * @return {@code true} if the operation was successful, {@code false}
	 *         otherwise.
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * Checks if the operation was unsuccessful.
	 *
	 * @return {@code true} if the operation was unsuccessful, {@code false}
	 *         otherwise.
	 */
	public boolean isError() {
		return !isSuccess;
	}

	/**
	 * Gets the error message associated with the result.
	 *
	 * @return The error message, or {@code null} if the operation was successful.
	 */
	public String errorMessage() {
		return errorMessage;
	}

	/**
	 * Gets the exception associated with the result.
	 *
	 * @return The exception, or {@code null} if the operation was successful.
	 */
	public Throwable exception() {
		return exception;
	}

	/**
	 * Throws the exception associated with the result if the operation was
	 * unsuccessful. If there is no exception, it throws a {@code RuntimeException}
	 * with the error message.
	 *
	 * @throws Throwable The exception associated with the result, or
	 *                   {@code VoidResultException} if no exception exists.
	 */
	public void throwIfError() throws VoidResultException {
		if (isError()) {
			throw new VoidResultException(errorMessage);
		}
	}

	/**
	 * Executes the given void operation and returns a VoidResult indicating its
	 * success or failure.
	 *
	 * @param operation The void operation to execute.
	 * @return A VoidResult indicating the success or failure of the operation.
	 */
	public static VoidResult of(VoidOperation operation) {
		try {
			operation.execute();
			return ok();
		} catch (Exception e) {
			return error("Error occurred during operation", e);
		}
	}

	/**
	 * Represents a void operation.
	 */
	@FunctionalInterface
	public interface VoidOperation {
		/**
		 * Executes the void operation.
		 *
		 * @throws Exception if an error occurs during the operation.
		 */
		void execute() throws VoidResultException;
	}

	/**
	 * Executes the given supplier operation and returns a VoidResult indicating its
	 * success or failure.
	 *
	 * @param supplier The supplier operation to execute.
	 * @return A VoidResult indicating the success or failure of the operation.
	 */
	public static VoidResult from(Supplier<VoidResult> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			return error("Error occurred during supplier operation", e);
		}
	}

	/**
	 * Executes the given consumer operation and returns a VoidResult indicating its
	 * success or failure.
	 *
	 * @param consumer The consumer operation to execute.
	 * @return A VoidResult indicating the success or failure of the operation.
	 */
	public static VoidResult accept(Consumer<VoidResult> consumer) {
		try {
			consumer.accept(ok());
			return ok();
		} catch (Exception e) {
			return error("Error occurred during consumer operation", e);
		}
	}
}
