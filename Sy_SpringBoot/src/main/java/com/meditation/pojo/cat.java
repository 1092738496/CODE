package com.meditation.pojo;

/**
 * @time: 2024/7/11 15:19
 * @description:
 */

public class cat {
    private String name;
    private String age;

    public cat() {
    }

    public cat(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "cat{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
