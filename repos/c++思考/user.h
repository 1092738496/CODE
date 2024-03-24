#pragma once
#include<iostream>
#include<string>
#include<vector>
#include<list>
using namespace std;
typedef string String;
///创建一个class类
class user {
private:
	String name;
	int age;
	String pet[10];
	vector<int> ss;
	list<String> list;


public:
	user() {
		age = 0;
	}

	String getName() const { return name; }
	void setName(const String& name) { this->name = name; }

	int getAge() const { return age; }
	void setAge(int age) { this->age = age; }

	const String (&getPet())[10]{
		return pet;
	}
	void setPet(const String(&_pet)[10]) {
		copy_n(_pet, sizeof(_pet) / sizeof(String), pet);
	}

	void setSS(vector<int>& _ss) {
		ss = move(_ss);
	}
	const vector<int>& getSS() {
		return ss;
	}
	

};