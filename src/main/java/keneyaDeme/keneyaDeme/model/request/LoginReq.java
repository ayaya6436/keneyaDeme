package keneyaDeme.keneyaDeme.model.request;

public class LoginReq {
  private String email;
  private String password;

  // Getters and setters

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LoginReq(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
