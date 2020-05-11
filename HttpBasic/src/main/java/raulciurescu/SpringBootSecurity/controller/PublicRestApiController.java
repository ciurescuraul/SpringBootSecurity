package raulciurescu.SpringBootSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
public class PublicRestApiController {

    public PublicRestApiController() {
    }

    @GetMapping("api1")
    public String api1() {
        return "API1 Test";
    }

    @GetMapping("api2")
    public String api2() {
        return "API2 Test";
    }
}
