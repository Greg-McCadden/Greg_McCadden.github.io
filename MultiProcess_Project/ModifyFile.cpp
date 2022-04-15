//
// Created by Greg McCadden on 3/22/22.
//

#include "ModifyFile.h"
#include "FileReader.h"
#include <string>
#include <list>
using namespace std;

// This function modifies the last existing entry in the input file and adds a new entry, then returns the updated list of EntryInfo structs.
list<EntryInfo> ModifyFile::modifyFileContents(list<EntryInfo> originalList) {
    // Modify the price and name of the last entry in the list of EntryInfo structs.
    float modifyPrice = 1000000;
    char *modifyNamePtr = new char[50];
    string modifyName = "CS 3377";
    for (int i = 0; i < modifyName.size() + 1; i++) {
        modifyNamePtr[i] = modifyName[i];
    }
    originalList.back().price = modifyPrice;
    originalList.back().itemName = modifyNamePtr;
    // Update numEntries (by 1) before adding a new entry to the list.
    FileReader::setNumEntries(FileReader::getNumEntries() + 1);
    // Create a FileReader object that copies the existing list to the private entries variable in FileReader.h.
    FileReader newList(originalList);
    // Add contents of the new entry into newBuffer (time, ID, Price, Name).
    // timestamp field of entry: 1613412000 = 2/15/2020 6 PM GMT
    time_t newTime = 1613412000;
    int newID = 6530927;
    float newPrice = 89.99;
    char *newNamePtr = new char[50];
    string newName = "Advanced Programming in the UNIX Environment by S";
    for (int i = 0; i < newName.size() + 1; i++) {
        newNamePtr[i] = newName[i];
    }
    // Add the new entry to the list.
    newList.createEntry(newTime, newID, newPrice, newNamePtr);

    return newList.getEntryList();
}