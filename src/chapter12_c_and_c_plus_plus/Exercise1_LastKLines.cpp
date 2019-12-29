#include <iostream>
#include <fstream>
using namespace std;

// O(l) runtime, where l is the number of lines in the file
// O(k) space, where k is the number of lines we want to print
void printLastKLines(int k, string fileName) {
    string lines[k];
    int size = 0;

    ifstream file (fileName);

    while (file.peek() != EOF) {
        getline(file, lines[size % k]);
        size++;
    }

    // Compute start of circular array and its size
    int start = size > k ? size % k : 0;
    int circularArraySize = min(k, size);

    for (int i = 0; i < circularArraySize; i++) {
        cout << lines[(start + i) % k] << endl;
    }
}

int main() {
    int k = 0;
    string fileName = "";

    cin >> k;
    getline(cin, fileName);

    printLastKLines(k, fileName);
    return 0;
}