//
// Created by Erik Peterson on 2/9/21.
//

#include "FileWriter.h"
#include "FileReader.h"
#include "FileModifyException.h"
#include <unistd.h>
#include <string>
#include <list>
using namespace std;

// This function copies the contents from the source file to the destination file, using the output file descriptor and the updated list.
void FileWriter::writeFileContents(int fileDescriptor, list<EntryInfo> newList) {
    // Create a temporary buffer to write the new number of entries to the output file.
    int tempBuffer[1];
    tempBuffer[0] = FileReader::getNumEntries();
    // Write the new number of entries to the output file.
    long int errorCheck = write(fileDescriptor, tempBuffer, sizeof(int));
    if (errorCheck != sizeof(int)) {
        throw FileModifyException("Error: cannot write to destination file.");
    }
    // Copy data from input file (sourceFile) to output file (destFile), using an iterator.
    typedef list<EntryInfo>::iterator entryInfoIterator;
    for (entryInfoIterator i = newList.begin(); i != newList.end(); ++i) {
        errorCheck =
                write(fileDescriptor, &i->timestamp, sizeof(time_t)) + write(fileDescriptor, &i->itemID, sizeof(int))
                + write(fileDescriptor, &i->price, sizeof(float)) + write(fileDescriptor, i->itemName, 50);
        // If the size of an Entry does not equal 66 bytes, throw an FileModifyException error.
        if (errorCheck != FileReader::getSizeOfEntry()) {
            throw FileModifyException("Error: cannot write to destination file");
        }
    }
}