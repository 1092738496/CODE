#include "user.h"

int main() {
	String pet[10] = { "1","2","3","4" ,"5" ,"6" ,"7" ,"8" ,"9" ,"10" };

	vector<int> ss;
	ss.push_back(1);
	ss.push_back(2);
	ss.push_back(3);
	ss.push_back(4);
	ss.push_back(5);
	ss.push_back(6);
	ss.push_back(7);
	ss.push_back(8);
	int a = ss.at(0);


	user user;
	user.setPet(pet);
	const String* pet2 = user.getPet();
	cout << *pet2;
	user.setSS(ss);

	vector<int> ssx = user.getSS();
	ssx.erase(ssx.begin() + 5);
	
	/*for (auto it = ssx.begin(); it < ssx.end(); ++it) {
		cout << *it << endl;
	}*/

	/*for (size_t i = 0; i < ssx.size(); i++) {
		cout << ssx.at(i) << endl;
	}*/
	/*auto it = ssx.begin();
	while (it != ssx.end()) {
		cout << *it << endl;
		++it;
	}*/



}