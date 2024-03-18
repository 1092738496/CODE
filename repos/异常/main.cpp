#include <iostream>
using namespace std;
class MyException :public exception {
private:
	int m_num;
public:
	MyException(int num) :exception("数组下标越界") {
		m_num = num;
	}
	const char* what() const override {
		cout << "数组下标越界" << m_num << endl;
		return exception::what();
	}
};

template<class T>
class myArray {
private:
	int size;
	T* lpvoid = nullptr;
public:
	myArray(int num) {
		lpvoid = new T[num];
		size = num;
	}

	T& operator[](int index) {
		if (index < 0 || index > size - 1) {
			throw MyException(index);
		}
		return lpvoid[index];
	}
	~myArray() {
		if (lpvoid != nullptr) {
			delete[] lpvoid;
		}
	}
};

void main() {
	myArray<int> arr(10);
	try {
		arr[10];
	} catch (MyException e) {
		e.what();
	}
}