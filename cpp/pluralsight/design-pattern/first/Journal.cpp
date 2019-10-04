#include "Journal.h"

using namespace std;

Journal::Journal(string t_title) : m_title{t_title} {}

void Journal::addEntries(string t_title)
{
    m_entries.push_back(t_title);
}

vector<string> Journal::getEntries()
{
    return m_entries;
}
