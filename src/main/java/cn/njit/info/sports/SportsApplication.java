package cn.njit.info.sports;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.util.TimeZone;

@SpringBootApplication
@MapperScan("cn.njit.info.sports.dao")
public class SportsApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SportsApplication.class, args);
  }


  /**
   * 时区设置
   */
  @PostConstruct
  void setDefaultTimezone() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
    //设置单个文件上传大小
    multipartConfigFactory.setMaxFileSize("10240KB");//KB,MB
    return multipartConfigFactory.createMultipartConfig();
  }

  @Bean //必须new 一个RestTemplate并放入spring容器当中,否则启动时报错
  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
    httpRequestFactory.setConnectTimeout(30 * 3000);
    httpRequestFactory.setReadTimeout(30 * 3000);
    return new RestTemplate(httpRequestFactory);
  }
}
