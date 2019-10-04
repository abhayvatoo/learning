#include <string>
#include "Tweeter.h"

using std::string;

Tweeter::Tweeter(string firstName, string lastName, int id, string account) : Person(firstName, lastName, id), account(account) {}

string Tweeter::getAccount()
{
    return account;
}
