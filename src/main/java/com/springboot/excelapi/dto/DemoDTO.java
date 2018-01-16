package com.springboot.excelapi.dto;

import org.springframework.context.annotation.Description;

@Description(value = "Demo DTO class.")
public class DemoDTO {

    private String name;
    private String address;
    private Double age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }
}
