package com.example.sin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {
    private Integer id;

    private String name;

    private String age;

    private String height;

}