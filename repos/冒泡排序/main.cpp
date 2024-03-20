#include <iostream>
using namespace std;
int main() {
	int arr[10] = { 0,5,7,8,5,3,9,4,1,4 };

	cout << sizeof(arr) / sizeof(int) << endl;

	for (size_t i = 0; i < sizeof(arr) / sizeof(int); i++) {

		for (size_t j = i; j < sizeof(arr) / sizeof(int); j++) {
			if (arr[i] > arr[j]) {
				int A = arr[j];
				arr[j] = arr[i];
				arr[i] = A;
			}
		}
	}
	

	for (size_t i = 0; i < sizeof(arr) / sizeof(int); i++) {
		cout << arr[i] << endl;
	}

	return 0;
}