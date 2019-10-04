#pragma once

#include <string>

class Person
{
private:
    std::string firstName;
    std::string lastName;
    int id;

public:
    Person(std::string firstName, std::string lastName, int id);
    std::string getFullName();
};