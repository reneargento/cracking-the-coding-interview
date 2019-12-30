#include <iostream>
using namespace std;

// O(s) runtime, where s is the string length
// O(1) space
void reverse(char* str) {
    char* end = str;

    if (str) {
        while (*end) {
            end++;
        }
        end--; // Decrement 1 due to null terminated char

        while (str < end) {
            char aux = *str;
            *str++ = *end;
            *end-- = aux;
        }
    }
}

int main() {
    char str[] = "Rene";
    reverse(str);

    cout << "Reversed: " << str << endl;
    cout << "Expected: eneR";
    return 0;
}