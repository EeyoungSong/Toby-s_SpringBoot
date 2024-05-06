package eeyoungspring.helloboot;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOError;
import java.io.IOException;

public class HellobootApplication {
	public static void main(String[] args) {
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);// bean 클래스 지정해서 bean 등록
		applicationContext.refresh(); // spring container 초기화

		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // Tomcat 서블릿 컨테이너 띄우기
		WebServer webServer = serverFactory.getWebServer(servletContext -> { // 매소드가 하나인 FunctionalInterface이기 때문에 람다식으로 대체 가능, servletContext 라는 파라미터 받기
			servletContext.addServlet("dispatcherServlet",
						new DispatcherServlet(applicationContext)
					).addMapping("/*");
        });
		webServer.start();
	}

}
