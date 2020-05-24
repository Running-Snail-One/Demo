package com.example.sin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //自定义异常信息
//    ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>() {{
//        add(new ResponseMessageBuilder().code(200).message("成功").build());
//        add(new ResponseMessageBuilder().code(400).message("请求参数错误")
//                .responseModel(new ModelRef("Error")).build());
//        add(new ResponseMessageBuilder().code(-1).message("未知异常")
//                .responseModel(new ModelRef("Error")).build());
//    }};

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("AUTHORIZATION_HEADER").description("Bearer token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(tokenPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.sin.controller"))
                .paths(PathSelectors.any())
                .build();
//                .globalResponseMessage(RequestMethod.POST, responseMessages)
//                .globalResponseMessage(RequestMethod.GET, responseMessages);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("范先生的APIS")
                .description("swagger集成测试")
                .termsOfServiceUrl("https://localhost:8080")
                .contact(new Contact("fanshuaibing", "https://localhost:8080", "sinfan@yeah.net"))
                .version("1.0")
                .build();
    }

}