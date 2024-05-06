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

import java.io.IOError;
import java.io.IOException;

public class HellobootApplication {
	public static void main(String[] args) {
		GenericApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.registerBean(HelloController.class); // bean 클래스 지정해서 bean 등록
		applicationContext.refresh(); // container 초기화

		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // Tomcat 서블릿 컨테이너 띄우기
		WebServer webServer = serverFactory.getWebServer(servletContext -> { // 매소드가 하나인 FunctionalInterface이기 때문에 람다식으로 대체 가능, servletContext 라는 파라미터 받기

			servletContext.addServlet("frontcontroller", new HttpServlet() { // 익명클래스 사용

				 @Override
				 protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {// frontcontroller 등록하는 코드
					 // 모든 요청에 대해서 매핑과 공통 코드
					 if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
						 String name = req.getParameter("name"); // request parameter 받을 수 있음

						 HelloController helloController = applicationContext.getBean(HelloController.class); // 클래스 타입 지정
						 String ret = helloController.hello(name); // 로직 분리 & 바인딩


						 resp.setContentType(MediaType.TEXT_PLAIN_VALUE); // text 그냥 입력하는 건 오타의 위험이 있음 -> 미리 정의된 enum 활용
						 resp.getWriter().println(ret);
					 }
					 else if (req.getRequestURI().equals("/user")) {
						 //
					 }
					 else {
						 resp.setStatus(HttpStatus.NOT_FOUND.value());
					 }
				 }
			}).addMapping("/*"); // 모든 요청에 대하여
        });
		webServer.start();
	}

}
