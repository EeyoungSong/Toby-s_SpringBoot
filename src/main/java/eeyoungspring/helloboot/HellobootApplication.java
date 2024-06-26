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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOError;
import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory(); // Tomcat 서블릿 컨테이너 띄우기
		WebServer webServer = serverFactory.getWebServer(servletContext -> { // 매소드가 하나인 FunctionalInterface이기 때문에 람다식으로 대체 가능, servletContext 라는 파라미터 받기
         	servletContext.addServlet("hello", new HttpServlet() { // 익명클래스 사용
				 @Override
				 protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { // servlet 등록하는 코드
					 String name = req.getParameter("name");

					 resp.setStatus(HttpStatus.OK.value());
					 resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // text 그냥 입력하는 건 오타의 위험이 있음 -> 미리 정의된 enum 활용
					 resp.getWriter().println("Hello " + name);
				 }
			}).addMapping("/hello"); // 요청마다 서블릿 하나씩 매핑
        });
		webServer.start();
	}

}
