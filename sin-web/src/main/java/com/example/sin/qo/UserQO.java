package com.example.sin.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserQO {

    @ApiModelProperty(value = "用户姓名", dataType = "String")
    private String name;

    @NonNull
    @ApiModelProperty(value = "用户年龄", dataType = "String")
    private String age;

    @ApiModelProperty(value = "用户身高", dataType = "String")
    private String height;
}
