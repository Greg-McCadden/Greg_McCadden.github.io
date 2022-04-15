//
// Created by Erik Peterson on 2/10/21.
//

#ifndef INC_21S_CS3377_PROJECT_PART2MULTIPROCESSMODIFIER_H
#define INC_21S_CS3377_PROJECT_PART2MULTIPROCESSMODIFIER_H


#include "Modifier.h"
#include "Util.h"
#include "list"

class Part2MultiProcessModifier : public Modifier {
public:
    void doSetup(IOType ioType) override;
    void modifyAndCopyFile(const char* sourceFile,
                           const char* destFile) override;

private:
    // This function determines whether the program is reading from the input file & writing to the pipe, or
    // reading from the pipe and writing to the output file.
    IOType status;
};


#endif //INC_21S_CS3377_PROJECT_PART2MULTIPROCESSMODIFIER_H