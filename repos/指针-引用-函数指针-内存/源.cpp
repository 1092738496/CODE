#include "iostream"
#include "string"

using namespace std;

int* funt1() {
	int a = 100;
	int* b = (int*)calloc(1,4);
	*b = a;
	return b;
}

int* funt2(int& a, int& b) {
	int c =a + b;
	int* d = &c;
	return d;
}

int* funt3() {
	int a = 100;
	cout << &a << endl;
	return &a;
}

int main() {

	int* b = funt3();
	
	cout << b << endl;
	/*int *z = funt1();

	int a, b;
	a = 10;
	b = 20;
	int* c = funt2(a, b);
	int* w = funt2(a, b);
	a = 10;
	b = 20;
	cout << *c << endl;
	cout << *z << endl;
	free(z);
	cout << *z << endl;*/
}