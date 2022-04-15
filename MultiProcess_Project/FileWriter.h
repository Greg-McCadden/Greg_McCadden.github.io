//
// Created by Erik Peterson on 2/9/21.
//

#ifndef INC_21S_CS3377_PROJECT_FILEWRITER_H
#define INC_21S_CS3377_PROJECT_FILEWRITER_H


#include <list>
#include <string>
#include "Util.h"
#include <utility>
using namespace std;

class FileWriter {
public:
    void addEntry(const EntryInfo& entryInfo);
    void writeFile(const char* fileToWrite);
    void writeFile(int fileDescriptor);

    // Default constructor.
    FileWriter() = default;

    // Function declaration (implemented in FileWriter.cpp).
    void writeFileContents(int fileDescriptor, list<EntryInfo> originalList);
private:
    std::string getErrnoString();

    std::list<EntryInfo> entries;
};


#endif //INC_21S_CS3377_PROJECT_FILEWRITER_H
