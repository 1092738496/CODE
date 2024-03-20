#pragma once
#include <iostream>
using namespace std;
typedef string String;
class user {
private:
	String name;
	int age;
	String address;
	String pets[10];
public:	
	void setName(const String& _name) {
		name = _name;
	}
	void setAge(int _age) {
		age = _age;
	}
	void setAddress(const String& _address) {
		address = _address;
	}
	void setPets(const String& pets) {

	}
	

};
