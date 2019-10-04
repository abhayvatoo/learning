#ifndef JOURNAL_H
#define JOURNAL_H

#include "iostream"
#include <vector>

class Journal
{
private:
    std::string m_title;
    std::vector<std::string> m_entries;

public:
    explicit Journal(std::string t_title);
    void addEntries(std::string t_title);
    std::vector<std::string> getEntries();
};

#endif