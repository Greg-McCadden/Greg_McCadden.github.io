#ifndef INC_21S_CS3377_PROJECT_TEMPLATE_MODIFYFILE_H
#define INC_21S_CS3377_PROJECT_TEMPLATE_MODIFYFILE_H

#include "Util.h"
#include <list>
#include <string>
using namespace std;

class ModifyFile {
public:
    // Default constructor.
    ModifyFile() = default;

    // Function declaration (implemented in ModifyFile.cpp).
    list<EntryInfo> modifyFileContents(list<EntryInfo> originalList);
};

#endif //INC_21S_CS3377_PROJECT_TEMPLATE_MODIFYFILE_H