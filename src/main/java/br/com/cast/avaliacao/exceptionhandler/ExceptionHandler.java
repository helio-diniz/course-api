package br.com.cast.avaliacao.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.cast.avaliacao.service.BusinessException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String userMessage = messageSource.getMessage("menssage.invalid", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.getCause().toString();

		List<ApiError> errors = Arrays.asList(new ApiError(userMessage, developerMessage));
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ApiError> errors = this.createErrorList(ex.getBindingResult());
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<ApiError> createErrorList(BindingResult bindingResult) {
		List<ApiError> errors = new ArrayList<>();
		for (FieldError error : bindingResult.getFieldErrors()) {
			String userMessage = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			String developerMessage = error.toString();
			errors.add(new ApiError(userMessage, developerMessage));
		}
		return errors;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<ApiError> errors = Arrays.asList(new ApiError(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("resource.operation-forbidden", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

		List<ApiError> erros = Arrays.asList(new ApiError(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleDataBusinessException(BusinessException ex, WebRequest request) {
		String mensagemUsuario = ex.getMessage();
		String mensagemDesenvolvedor = ex.getClass().getName() + ": " + ex.getMessage();

		List<ApiError> errors = Arrays.asList(new ApiError(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	public static class ApiError {
		private String userMessage;
		private String developerMessage;

		public ApiError() {
			super();
		}

		public ApiError(String userMessage, String developerMessage) {
			super();
			this.userMessage = userMessage;
			this.developerMessage = developerMessage;
		}

		public String getUserMessage() {
			return userMessage;
		}

		public String getDeveloperMessage() {
			return developerMessage;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((developerMessage == null) ? 0 : developerMessage.hashCode());
			result = prime * result + ((userMessage == null) ? 0 : userMessage.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ApiError other = (ApiError) obj;
			if (developerMessage == null) {
				if (other.developerMessage != null)
					return false;
			} else if (!developerMessage.equals(other.developerMessage))
				return false;
			if (userMessage == null) {
				if (other.userMessage != null)
					return false;
			} else if (!userMessage.equals(other.userMessage))
				return false;
			return true;
		}

	}
}
