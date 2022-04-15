//
// Created by Erik Peterson on 2/9/21.
//

#ifndef INC_21S_CS3377_PROJECT_FILEWRITER_H
#define INC_21S_CS3377_PROJECT_FILEWRITER_H

#include "Util.h"
#include <list>
#include <string>
using namespace std;

class FileWriter {
public:
    // Default constructor.
    FileWriter() = default;

    // Function declaration (implemented in FileWriter.cpp).
    void writeFileContents(int fileDescriptor, list<EntryInfo> originalList);
};


#endif //INC_21S_CS3377_PROJECT_FILEWRITER_H