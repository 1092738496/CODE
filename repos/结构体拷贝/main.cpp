#include <iostream>
using namespace std;
int main() {
	typedef struct _porson {
		int id;
		char name[50];
	}porson, * pporson;

	pporson user1 = (pporson)calloc(1, sizeof(pporson) + strlen("asdadasdasdasd"));
	user1->id = 1;
	strcpy_s(user1->name, sizeof(porson) + strlen("asdadasdasdasd"), "asdadasdasdasd");
	cout << user1->name << endl;;

	porson user2;
	user2.id = 1;
	strcpy_s(user2.name, sizeof(porson) + strlen("111111111111111111111111"), "111111111111111111111111");
	cout << user2.name << endl;

	porson user3 = user2;
	cout << user3.name;
}