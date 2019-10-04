#include "Person.h"

using std::string;

Person::Person(string firstName, string lastName, int id) : firstName(firstName), lastName(lastName), id(id) {}

string Person::getFullName()
{
    return firstName + " " + lastName;
}