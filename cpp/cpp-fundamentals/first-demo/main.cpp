#include "iostream"
#include "Person.h"
#include "Tweeter.h"

using std::cout;
using std::endl;
using std::string;

int main()
{
    Person p1("abhay", "vatoo", 1);
    cout << p1.getFullName() << endl;

    Tweeter t1("some", "else", 1, "@account");
    cout << t1.getFullName() << " " << t1.getAccount() << endl;
    return 0;
}