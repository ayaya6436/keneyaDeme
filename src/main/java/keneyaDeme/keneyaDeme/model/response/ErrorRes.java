package keneyaDeme.keneyaDeme.model.response;

import org.springframework.http.HttpStatus;

public class ErrorRes {
  HttpStatus httpstatus;
  String message;

  public ErrorRes(HttpStatus httpStatus, String message) {
    this.httpstatus = httpStatus;
    this.message = message;
  }

  public HttpStatus getHttpstatus() {
    return httpstatus;
  }

  public void setHttpstatus(HttpStatus httpstatus) {
    this.httpstatus = httpstatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
