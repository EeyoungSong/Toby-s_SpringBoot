package eeyoungspring.helloboot;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service // componet 어노테이션 자동 적용
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
