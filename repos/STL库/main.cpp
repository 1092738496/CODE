#include <iostream>
#include<vector>
#include<list>
using namespace std;

void testvector() {
	vector<int> vet;
	cout << sizeof(vet) << endl;
	vet.push_back(111);

	cout << vet.at(0) << endl;
}

void testlist() {
	list<int> list;
	list.push_back(11);
	list.push_back(22);


	/*auto it = list.begin();
		it++;
		cout << *it << endl;*/

	for (auto it = list.begin(); it != list.end(); ++it) {
		cout << *it <<endl;
	}
}


int main() {
	//	testvector();
	testlist();

	return 1;
}