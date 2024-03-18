#include <iostream>
#include <string>
using namespace std;
class user {

private:
	int id;
	string name;


public:
	user() {
		cout << "构造函数执行" << endl;
	}

	~user() {
		cout << "析构造函数执行" << endl;
	}
	// Getter 方法
	int getId() const {
		return id;
	}

	const string& getName() const {
		return name;
	}

	// Setter 方法
	void setId(int id) {
		this->id = id;
	}

	void setName(const string& newName) {
		name = newName;
	}
};
