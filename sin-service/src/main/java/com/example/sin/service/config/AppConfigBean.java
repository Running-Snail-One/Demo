package com.example.sin.service.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("appConfigBean")
@Getter
@ToString
public class AppConfigBean {

    @Value("${app.port:8078}")
    private Integer port;

    //---------------- jdbc config -----------------------
    @Value("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8")
    private String jdbcUrl;
    @Value("com.mysql.cj.jdbc.Driver")
    private String jdbcDriverClassName;
    @Value("root")
    private String jdbcUsername;
    @Value("123")
    private String jdbcPassword;
    @Value("10")
    private Integer jdbcInitialSize;
    @Value("30")
    private Integer jdbcMaxActive;
}
