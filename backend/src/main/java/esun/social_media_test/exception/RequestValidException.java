package esun.social_media_test.exception;

import org.springframework.validation.Errors;

public class RequestValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Errors errors;
	private String msg;

	public RequestValidException(Errors errors) {
		this.errors = errors;
	}

	public RequestValidException(String msg) {
		this.msg = msg;
	}

	public RequestValidException(String msg, Errors errors) {
		this.msg = msg;
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
