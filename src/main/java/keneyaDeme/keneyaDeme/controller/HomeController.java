package keneyaDeme.keneyaDeme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/keneya/home")
@CrossOrigin(origins = "*")

@Controller
public class HomeController {
  @ResponseBody
  @RequestMapping(value = "",method = RequestMethod.GET)
  public String hello() {
    return "Welcome to KeneyaDeme API!";
  }
}
