#include <iostream>
using namespace std;
class A {
public:
	int valueA;
	A() {
		valueA = 0X11111111;
	}
};

class B :virtual public A {
public:
	int valueB;
	B() {
		valueB = 0X22222222;
	}
};

class C :virtual public A {
public:
	int valueC;
	C() {
		valueC = 0X33333333;
	}
};

class D :virtual public B, virtual public C {
public:
	int valueD;
	D() {
		valueD = 0X44444444;
	}
};




class Z {
public:
 	virtual void show() {
		cout << "込込込込" << endl;
	}
};

class X : public Z{
public:
	 void show() {
		cout << "細細細細" << endl;
	}
};
int main() {
	Z* z;
	X x;

	z = &x;

	z->show();
}