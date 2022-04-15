//
// Created by Erik Peterson on 2/9/21.
//

#include <fcntl.h>
#include <cerrno>
#include <unistd.h>
#include <cstring>
#include "FileReader.h"
#include "FileModifyException.h"
#include "Util.h"
using namespace std;

// Definition of static variable numEntries.
int FileReader::numEntries = 0;

char nameToChange[50] = "CS 3377";
char nameFirst[50] = "Advanced Programming in the UNIX Environment by S";

std::string FileReader::getErrnoString() {
    return strerror(errno);
}

void FileReader::verifyNumBytes(ssize_t expected, ssize_t actual) {
    if ( expected != actual) {
        throw FileModifyException("Bytes read/written (" + std::to_string(expected) + ") don't match actual (" + std::to_string(actual) + ")");
    }
}

void FileReader::readEntries(std::list<EntryInfo>& entryList, const char* fileName) {
    int fileDescriptor = open(fileName, O_RDONLY);
    if ( fileDescriptor < 0) {
        close(fileDescriptor);
        throw FileModifyException("Unable to open output file: " + getErrnoString());
    }
    readEntries(entryList, fileDescriptor);
    close(fileDescriptor);
}

void FileReader::readEntries(std::list<EntryInfo> &entryList, int fileDescriptor) {
    int numItems;
    size_t amountRead = read(fileDescriptor, &numItems, sizeof(int));
    if ( amountRead <= 0) {
        close(fileDescriptor);
        throw FileModifyException("File is empty/error reading");
    }

    for (int i=0; i < numItems; i++) {
        EntryInfo incoming;
        verifyNumBytes(sizeof(time_t), read(fileDescriptor, &incoming.timestamp, sizeof(time_t)));
        verifyNumBytes(sizeof(int), read(fileDescriptor, &incoming.itemID, sizeof(int)));
        char* inputName = new char[50];
        verifyNumBytes(sizeof(float), read(fileDescriptor, &incoming.price, sizeof(float)));
        verifyNumBytes(50, read(fileDescriptor, inputName, 50 * sizeof(char)));
        incoming.itemName = inputName;
        entryList.push_back(incoming);
    }
}

void FileReader::addMissingEntries(std::list<EntryInfo> & entries) {
    EntryInfo infoToModify = entries.back();
    entries.pop_back();
    infoToModify.price = 1'000'000;
    infoToModify.itemName = nameToChange;
    entries.push_back(infoToModify);

    EntryInfo additionalFirst;
    additionalFirst.timestamp = 1613412000; // (2/1/2020 4 PM GMT)
    additionalFirst.itemID = 6530927;
    additionalFirst.price = 89.99;
    additionalFirst.itemName = nameFirst;
    entries.push_back(additionalFirst);
}

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