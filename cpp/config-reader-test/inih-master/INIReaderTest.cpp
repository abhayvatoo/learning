// Example that shows simple usage of the INIReader class

#include <iostream>
#include <sstream>
#include "INIReader.h"

std::string sections(INIReader &reader)
{
    std::stringstream ss;
    std::set<std::string> sections = reader.Sections();
    for (std::set<std::string>::iterator it = sections.begin(); it != sections.end(); ++it)
        ss << *it << ",";
    return ss.str();
}

int main()
{
    INIReader reader("test.ini");

    if (reader.ParseError() < 0)
    {
        std::cout << "Can't load 'test.ini'\n";
        return 1;
    }
    std::cout << "Config loaded from 'test.ini': found sections=" << sections(reader) << std::endl
              << "version = " << reader.GetInteger("protocol", "version", -1) << std::endl
              << "name = " << reader.Get("user", "name", "UNKNOWN") << std::endl
              << "email = " << reader.Get("user", "email", "UNKNOWN") << std::endl
              << "multi = " << reader.Get("user", "multi", "UNKNOWN") << std::endl
              << "pi = " << reader.GetReal("user", "pi", -1) << std::endl
              << "active = " << reader.GetBoolean("user", "active", true) << "\n";
    return 0;
}
