//
// Created by Erik Peterson on 2/10/21.
//

// NOTE: Before running the program, set the program argument to 'R'.

#include <fcntl.h>
#include <unistd.h>
#include <iostream>
#include <cstring>
#include <sys/wait.h>
#include "Part2MultiProcessModifier.h"
#include "PipeMaker.h"
#include "FileModifyException.h"
#include "FileReader.h"
#include "FileWriter.h"
#include "ModifyFile.h"
using namespace std;

// This function determines whether the program is reading from the input file & writing to the pipe, or
// reading from the pipe and writing to the output file.
void Part2MultiProcessModifier::doSetup(IOType ioType) {
    status = ioType;
}

void Part2MultiProcessModifier::modifyAndCopyFile(const char *sourceFile, const char *destFile) {
    cout << "inside original process" << endl;
    // Create a PipeMaker object.
    PipeMaker openPipe;
    // Create Child process (fork()) if the program argument is 'R' (read; before execl()).
    int pid = 0;
    if(status == IOType::READ){
        pid = fork();
    }
    // If userPid == negative number, an error in fork() has occurred.
    if(pid < 0){
        cout << "Error: unable to create child process" << endl;
        exit(1);
    }
    else if(pid == 0){ // Inside child process.
        // Run execl() if program argument is 'R'.
        if(status == IOType::READ) {
            // Set up the pipe to be read from by the child process (pipeFDs[0] == file descriptor for the pipe that can be read from).
            // This is done before execl() to copy the file descriptor for the pipe to STDIN_FILENO (so that the entry data survives the starting of a new process).
            openPipe.setUpToRead();
	    cout << "inside child process...exec()" << endl;
            execl("Part2_Template", "Part2_Template", "W", NULL);
        }
        if(status == IOType::WRITE){
	    cout << "inside child process...reading from pipe and writing to output file" << endl;
            // FileReader object for reading from the pipe.
            FileReader readFromPipe;
            // FileWriter object for writing to output file.
            FileWriter writeToFile;
            // Open the output file.
            int outputFileDescriptor = open(destFile, O_WRONLY | O_CREAT, S_IRWXU);
            // If open() returns -1, output file cannot be opened. Thus, display an error message.
            if (outputFileDescriptor == -1) {
                close(outputFileDescriptor);
                throw FileModifyException("Error: the destination file cannot be opened.");
            }
            // Read the input file contents from the pipe (written to the pipe by the parent process).
            list<EntryInfo> pipeList = readFromPipe.readFileContents(STDIN_FILENO);
            // Write the input file contents (that was read off the pipe) to the output file.
            writeToFile.writeFileContents(outputFileDescriptor, pipeList);
            // Close the output file.
            close(outputFileDescriptor);
            // Terminate the program.
            exit(0);
        }
    }
    else{ // If pid > 0, inside parent process.
	cout << "inside parent process...reading/modifying input file & writing its contents to the pipe" << endl;
        int inputFileDescriptor = open(sourceFile, O_RDONLY, S_IRWXU);
        // If open() returns -1, input file cannot be opened. Thus, display an error message.
        if (inputFileDescriptor == -1) {
            close(inputFileDescriptor);
            throw FileModifyException("Error: the source file cannot be opened.");

        }
        // FileReader object for reading from the input file.
        FileReader readFile;
        // FileWriter object for writing to pipe.
        FileWriter writeToPipe;
        // ModifyFile object for modifying the input file's contents.
        ModifyFile modifySourceFile;
        // Read contents from input file and return the list to originalList.
        list<EntryInfo> originalList = readFile.readFileContents(inputFileDescriptor);
        // Close input file.
        close(inputFileDescriptor);
        // Modify the required contents in the input file.
        list<EntryInfo> modifiedList = modifySourceFile.modifyFileContents(originalList);
        // Set up the pipe to be written to by the parent process (pipeFDs[1] == file descriptor for the pipe that can be written to).
        int writePipeFd = openPipe.setUpToWrite();
        // Write input file's contents to the pipe for the child process to read.
        writeToPipe.writeFileContents(writePipeFd, modifiedList);
        // Close the file descriptor that was used to write to the pipe.
        close(writePipeFd);
        // Wait for the child process to finish executing.
        int *childProcessID = nullptr;
        wait(childProcessID);
    }
}

console output:
inside original process
inside parent process...reading/modifying input file & writing its contents to the pipe
inside child process...exec()
inside original process
inside child process...reading from pipe and writing to output file
File modified successfully!
