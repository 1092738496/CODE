#pragma once
#include <string>
#include <iostream>
using namespace std;
class user {
private:
	int id;
	string name;
	int age;
	char remark[100];
public:
    user() : id(0), name(""), age(0) {
        remark[0] = '\0'; // 初始化为空字符串
    }
    // 构造函数
    user(int _id, const string& _name, int _age, const char* _remark = ""): id(_id), name(_name), age(_age) {
        strncpy_s(remark, _remark, sizeof(remark) - 1);
        remark[sizeof(remark) - 1] = '\0'; // 确保字符串以空字符结尾
    }

    // 析构函数
    ~user() {}

    // 拷贝构造函数
    user(const user& other) {
        id = other.id;
        name = other.name;
        age = other.age;
        strncpy_s(remark, other.remark, sizeof(remark) - 1);
        remark[sizeof(remark) - 1] = '\0';
    }
    // Getter methods
    int getId() const {
        return id;
    }

    string getName() const {
        return name;
    }

    int getAge() const {
        return age;
    }

    const char* getRemark() const {
        return remark;
    }

    // Setter methods
    void setId(int _id) {
        id = _id;
    }

    void setName(const string& _name) {
        name = _name;
    }

    void setAge(int _age) {
        age = _age;
    }

    void setRemark(const char* _remark) {
        strncpy_s(remark, _remark, sizeof(remark) - 1);
        remark[sizeof(remark) - 1] = '\0';
    }
};