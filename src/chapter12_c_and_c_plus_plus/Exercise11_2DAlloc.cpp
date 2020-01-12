#include <iostream>

using namespace std;

int** my2DAlloc(int rows, int columns) {
    size_t header = rows * sizeof(int*);
    size_t data = rows * columns * sizeof(int);
    int** array = (int**)malloc(header + data);
    if (array == NULL) {
        return NULL;
    }

    int* buffer = (int*) (array + rows);
    for (int i = 0; i < rows; i++) {
        array[i] = buffer + i * columns;
    }
    return array;
}

int main() {
    int **array = my2DAlloc(5, 6);
    if (array != NULL) {
        cout << "Successfully allocated memory for 2D array";
    }
    free(array);
    return 0;
}