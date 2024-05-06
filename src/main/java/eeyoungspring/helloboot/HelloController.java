package eeyoungspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


public class HelloController {
    public String hello(String name) {
        SimpleHelloService helloService = new SimpleHelloService();
        return helloService.sayHello(Objects.requireNonNull(name)); // 파라미터가 null인지 아닌지 체크
    }
}
