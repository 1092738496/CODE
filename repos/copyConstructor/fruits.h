#pragma once
#include <iostream>
#include <vector>
#include <list>
#include <string>
#include <map>
using namespace std;
typedef string String;

class fruits {
private:
	char name[10];
	String colour;
	map<char[20], double> variety_price;

public:
	const char* getName() const {
		return name;
	}
	void setName(const char* _name) {
		strncpy_s(name, _name, sizeof(name) - 1);
		name[sizeof(name) - 1] = '\0';
	}

	String getColour() const {
		return colour;
	}
	void setColour(const String& _colour) {
		this->colour = _colour;
	}

	const map<char[20], double>& getVarietyprice() const {
		return variety_price;
	}

	void setVarietyprice(const map<char[20], double>& varietyprice) {
		variety_price = varietyprice;
	}


    // 1. 默认构造函数（空参构造）
    fruits() : colour(""), variety_price{} {
        name[0] = '\0';
    }

    // 2. 有参构造函数
    fruits(const char* _name, const String& _colour, const map<char[20], double>& _varietyprice)
        : colour(_colour), variety_price(_varietyprice) {
        strncpy_s(name, _name, sizeof(name) - 1);
        name[sizeof(name) - 1] = '\0';
    }

    // 3. 拷贝构造函数
    fruits(const fruits& other)
        : colour(other.colour), variety_price(other.variety_price) {
        strncpy_s(name, other.name, sizeof(name) - 1);
        name[sizeof(name) - 1] = '\0';
    }

    // 4. 移动构造函数 (C++11及以上)
    fruits(fruits&& other) noexcept
        : colour(std::move(other.colour)), variety_price(std::move(other.variety_price)) {
        strncpy_s(name, other.name, sizeof(name) - 1);
        name[sizeof(name) - 1] = '\0';
        // 清理原对象的name不是必须的，因为这里只是字符数组，无法转移所有权
        // 但是在实际应用中，若能转移所有权则应该清空other的资源
    }

    // 5. 析构函数
    ~fruits() {}

};