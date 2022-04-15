//
// Created by Erik Peterson on 2/9/21.
//

#include "FileReader.h"
#include "FileModifyException.h"
#include <unistd.h>
#include <cmath>
using namespace std;

// Definition of static variable numEntries.
int FileReader::numEntries = 0;

// This function reads the contents from a binary file, given a file descriptor.
// It returns a list of EntryInfo structs, which contain the contents of the input file.
list<EntryInfo> FileReader::readFileContents(int fileDescriptor) {
    // Buffer that holds the number of Entries int value (possibly int tempBuff[4]).
    int tempBuff[1];
    // Read the number of entries in the file (numEntries = 4 bytes). errorCheck is used to ensure the file is opened successfully.
    long int errorCheck = read(fileDescriptor, tempBuff, sizeof(int));
    // If read() a number not equal to the size of an int, display error message.
    if (errorCheck != sizeof(int)) {
        throw FileModifyException("Error: input file cannot be read.");
    }
    // Convert the number of entries in binary to decimal and set this number as the number of entries in the file.
    numEntries = tempBuff[0];
    // Create a FileReader object.
    FileReader readFile;
    // Create local variables for all the EntryInfo attributes.
    time_t timestamp;
    int itemID;
    float itemPrice;
    char *itemName;
    // Read in contents from the beginning of the input file.
    for (int i = 1; i <= numEntries; i++) {
        itemName = new char[50];
        errorCheck = read(fileDescriptor, &timestamp, sizeof(time_t)) + read(fileDescriptor, &itemID, sizeof(int))
                     + read(fileDescriptor, &itemPrice, sizeof(float)) + read(fileDescriptor, itemName, 50);
        // If the size of an Entry does not equal 66 bytes, throw an FileModifyException error.
        if (errorCheck != sizeOfEntry) {
            throw FileModifyException("Error: cannot read from source file");
        }
        // Add a new Entry to the list of EntryInfo structs.
        readFile.createEntry(timestamp, itemID, itemPrice, itemName);
    }
    return readFile.getEntryList();
}