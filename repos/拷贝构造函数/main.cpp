#include "user.h"
int main() {
    user u1;
    // 使用setter方法修改用户信息
    u1.setId(2);
    u1.setName("Bob");
    u1.setAge(30);
    u1.setRemark("Updated remark");
    // 使用getter方法获取并打印用户信息
    //cout << "User ID: " << u1.getId() << endl;
    //cout << "User Name: " << u1.getName() << endl;
    //cout << "User Age: " << u1.getAge() << endl;
    //cout << "User Remark: " << u1.getRemark() << endl;

    // 现在remarkPointerU1指向u1.remark数组的首地址
    user* u2 = new user(u1);
    const char* remarkPointerU1 = u1.getRemark();
    const char* remarkPointerU2 = u2->getRemark();
    cout << &remarkPointerU1 << endl;
    cout << &remarkPointerU2 << endl;

    return 0;
}