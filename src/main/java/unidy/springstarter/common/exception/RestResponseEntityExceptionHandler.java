package unidy.springstarter.common.exception;

import javax.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	public RestResponseEntityExceptionHandler() {
		super();
	}

	// 400
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
		return handle(request, ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
		return handle(request, ex, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return handle(request, ex, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return handle(request, ex, HttpStatus.BAD_REQUEST);
	}

	// 403
	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(final Exception ex, final WebRequest request) {
		return new ResponseEntity<Object>("Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	// 404
	@ExceptionHandler(value = { EntityNotFoundException.class, RestfulException.class })
	protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
		return handle(request, ex, HttpStatus.NOT_FOUND);
	}

	// 409
	@ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class })
	protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
		return handle(request, ex, HttpStatus.CONFLICT);
	}

	// 412 & 500
	@ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class, IllegalStateException.class })
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		return handle(request, ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Object> handle(final WebRequest request, final Exception ex, HttpStatus status) {
		RestfulException e = new RestfulException(ex.getMessage());
		return handleExceptionInternal(ex, e.toString(), new HttpHeaders(), status, request);
	}
}
