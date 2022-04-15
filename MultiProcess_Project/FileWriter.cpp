//
// Created by Erik Peterson on 2/9/21.
//

#include <fcntl.h>
#include <cerrno>
#include <unistd.h>
#include <cstring>
#include "FileWriter.h"
#include "FileModifyException.h"
#include "FileReader.h"

std::string FileWriter::getErrnoString() {
    return strerror(errno);
}

void FileWriter::addEntry(const EntryInfo& entryInfo) {
    entries.push_back(entryInfo);
}

void FileWriter::writeFile(const char* fileToWrite) {
    int fileDescriptor = open(fileToWrite, O_WRONLY | O_CREAT | O_TRUNC, S_IRWXU);
    if ( fileDescriptor < 0) {
        throw FileModifyException("Unable to open output file: " + getErrnoString());
    }

    writeFile(fileDescriptor);
    close(fileDescriptor);
}

void FileWriter::writeFile(int fileDescriptor) {
    int numItemsToWrite = entries.size();
    write(fileDescriptor, &numItemsToWrite, sizeof(int));

    for ( EntryInfo info : entries) {
        write(fileDescriptor, &info.timestamp, sizeof(time_t));
        write(fileDescriptor, &info.itemID, sizeof(int));
        write(fileDescriptor, &info.price, sizeof(float));
        write(fileDescriptor, info.itemName, 50);
    }
}

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