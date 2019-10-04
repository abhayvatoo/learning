#include "iostream"
#include "Journal.h"

#include "iostream"
#include <vector>

using namespace std;

int main()
{
    Journal times = Journal("times-now");
    times.addEntries("one-entry");
    times.addEntries("two-entry");

    vector<string> entries = times.getEntries();

    for (size_t i = 0; i < entries.size(); i++)
    {
        cout << "entry " << i << " : " << entries[i] << endl;
    }

    return 0;
}