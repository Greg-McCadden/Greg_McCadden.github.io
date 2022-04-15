//
// Created by erik on 2/2/21.
//

#include "FileReader.h"
#include "FileWriter.h"
#include "ModifyFile.h"
#include "Part1SimpleFileModifier.h"
#include "FileModifyException.h"
#include <fcntl.h>
#include <unistd.h>
#include <list>
using namespace std;

void Part1SimpleFileModifier::modifyAndCopyFile(const char *sourceFile, const char *destFile) {
    // FileReader object
    FileReader readFile;
    // FileWriter object
    FileWriter writeToFile;
    // ModifyFile object.
    ModifyFile modifySourceFile;
    // Open the input file (sourceFile) and store the file descriptor in inputFileDescriptor.
    int inputFileDescriptor = open(sourceFile, O_RDONLY, S_IRWXU);
    // Open the output file (destFile) and store the file descriptor in outputFileDescriptor.
    int outputFileDescriptor = open(destFile, O_WRONLY | O_CREAT, S_IRWXU);
    // If open() returns -1, input file cannot be opened. Thus, display an error message.
    if (inputFileDescriptor == -1) {
        throw FileModifyException("Error: the source file cannot be opened.");
    }
    // If open() returns -1, output file cannot be opened. Thus, display an error message.
    if (outputFileDescriptor == -1) {
        throw FileModifyException("Error: the destination file cannot be opened.");
    }
    // Read contents from input file and return the address of the buffer to originalBuffer.
    list<EntryInfo> originalList = readFile.readFileContents(inputFileDescriptor);
    // Modify the required contents in the input file.
    list<EntryInfo> newList = modifySourceFile.modifyFileContents(originalList);
    // Copy the input files contents to the output file.
    writeToFile.writeFileContents(outputFileDescriptor, newList);
    // Close input and output files.
    close(inputFileDescriptor);
    close(outputFileDescriptor);
}