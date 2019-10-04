#pragma once

#include <string>
#include "Person.h"

class Tweeter : public Person
{
private:
    std::string account;

public:
    Tweeter(std::string firstName, std::string lastName, int id, std::string account);
    std::string getAccount();
};
