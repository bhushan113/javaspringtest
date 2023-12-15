package com.technoelevate.test.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.technoelevate.test.response.ErrorResponse;


@RestControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		details.add(builder.toString());
		return ResponseEntity.ok(ErrorResponse.builder().error(true).message(details).build());

	}

	// handleHttpMessageNotReadable : triggers when the JSON is malformed
		@Override
		protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
			String[] errMsg = ex.getMessage().split(":");
			String string = errMsg[errMsg.length - 4];
			return ResponseEntity.ok(ErrorResponse.builder().error(true)
					.message(errMsg[0] + " : " + errMsg[1] + string.substring(0, string.length() - 7) + ")").build());
		}

	// handleMethodArgumentNotValid : triggers when @Valid fails
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.ok(ErrorResponse.builder().error(true)
				.message(ex.getBindingResult().getFieldErrors().stream()
						.map(error -> error.getField() + " : " + error.getDefaultMessage())
						.collect(Collectors.toList()))
				.build());
	}

	// handleMissingServletRequestParameter : triggers when there are missing
	// parameters
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.ok(
				ErrorResponse.builder().error(true).message(ex.getParameterName() + " parameter is missing").build());
	}

	// handleMethodArgumentTypeMismatch : triggers when a parameter's type does not
	// match
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());
	}

	// handleConstraintViolationException : triggers when @Validated fails
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException exception,
			WebRequest request) {
		Map<String, Object> errors = new HashMap<>();
		if (!exception.getConstraintViolations().isEmpty()) {
			for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
				String fieldName = null;
				for (Node node : constraintViolation.getPropertyPath()) {
					fieldName = node.getName();
				}
				errors.put(fieldName, constraintViolation.getMessage());
			}
		}
		return ResponseEntity.ok(ErrorResponse.builder().error(true).message(errors).build());
	}

	// dataIntegrityViolationException : triggers when @Validated fails
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException exception,
			WebRequest request) {
		return ResponseEntity.ok(ErrorResponse.builder().error(true)
				.message(exception.getMostSpecificCause().getMessage().split("for")[0]).build());
	}

	// handleNoHandlerFoundException : triggers when the handler method is invalid
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.ok(ErrorResponse.builder().error(true).message(
				String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
				.build());

	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ErrorResponse> handleAll(Exception ex, WebRequest request) {
		return ResponseEntity.ok(ErrorResponse.builder().error(true).message(ex.getMessage()).build());

	}

}