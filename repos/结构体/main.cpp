#define _CRT_SECURE_NO_DEPRECATE
#include <iostream>
using namespace std;

int main() {
	struct player {
		int id;
		string name;
	};
	player user1;
	user1.id = 123;
	user1.name = "asdasd";

	player* user2;
	user2 = &user1;
	(*user2).name = "wwwwwwwwwww";
	cout << user1.name << endl;


	struct player2 {
		int id;
		char name[1];
	};
	player2* user3 = (player2*)calloc(1, sizeof(player2) + strlen("ÕÅÈý"));

	user3->id = 1;
	strcpy(user3->name, "aaaaaaaaaa");
	cout << user3->id << endl;
	cout << user3->name;
}