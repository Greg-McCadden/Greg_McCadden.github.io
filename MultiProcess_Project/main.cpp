#include <iostream>
#include <unistd.h>
#include <cstring>

#include "Util.h"
#include "Modifier.h"
#include "FileModifierFactory.h"
#include "FileModifyException.h"

//---------------------------------------------------------
// Arguments:
// ModifierType: 1-4, corresponds to the project parts
//      1. Simple modifier
//      2. Multiprocess modifier
//      3. Threaded modifier
//      4. IPC (socket) modifier
//
// IOType: required for modifier types 2 and 4, because
//         they use 2 processes (one to read and the other
//         to write). Not needed for modifier types 1 & 3
//
//         For parts 2 & 4, call the read version (2)
//         from CLion. Your code should call the write
//         (3) version for the 2nd process.
//--------------------------------------------------------
int main(int argc, char** argv) {
    // Delete the output file so we're starting fresh
    unlink(Util::outputFilename);


    // Get the IOType (if specified)
    IOType ioType = IOType::READ_AND_WRITE;
    if ( argc == 2) {
        try {
            ioType = Util::toIOType(argv[1][0]);
        } catch ( FileModifyException e) {
            std::cerr << "Unable to determine I/O type of " << argv[1] << ": " << e.what() << "\n" << Util::usage;
        } catch ( std::exception e) {
            std::cerr << "Is " << argv[1] << " a character? Couldn't convert it to an IOType\n" << Util::usage;
        }
    }

    // Factory-build the modifier
    Modifier* modifier;
    try {
        modifier = FileModifierFactory::createModifier(ioType);
    } catch(FileModifyException e) {
        std::cerr << "Error creating modifier object: " << e.what();
        exit(1);
    } catch ( std::exception e) {
        std::cerr << "Error creating modifier object: " << e.what();
        exit(1);
    }

    try {
        modifier->modifyAndCopyFile(Util::inputFilename, Util::outputFilename);
    } catch (FileModifyException e) {
        std::cerr << "Error modifying file: " << e.what();
        exit(1);
    } catch ( std::exception e) {
        std::cerr << "Error modifying file: " << e.what();
        exit(1);
    }

    // Exec ProjectChecker to do the checking for us
    // The #ifdef here is some trickery to launch the MacOS or Linux version
    // of the checker program based on your architecture

    int error = 0;
#ifdef __linux__
    error = execl("./ProjectCheckerLinux", "ProjectChecker", NULL);
#elif __APPLE__
    error = execl("./ProjectCheckerMac", "ProjectChecker", NULL);
#else
    cerr << "Unknown architecture!" << endl;
    exit(1);
#endif
    if ( error) {
        std::cerr << "Unable to start file checker!" << strerror(errno) << std::endl;
        exit(1);
    }
}