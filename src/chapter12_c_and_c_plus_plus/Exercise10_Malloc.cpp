#include <iostream>

using namespace std;

void* aligned_malloc(size_t required_bytes, size_t alignment) {
    void* p1; // initial block
    void* p2; // aligned block inside initial block
    // Extra size to ensure we can allocate the aligned address and the pointer to the initial block
    size_t offset = alignment - 1 + sizeof(void*);
    if ((p1 = malloc(required_bytes + offset)) == NULL) {
        return NULL;
    }
    p2 = (void*)(((size_t)(p1) + offset) & ~(alignment - 1)); // move pointer to the aligned address position
    ((void **) p2)[-1] = p1; // store a pointer to the initial block right before p2
    return p2;
}

void aligned_free(void *p2) {
    // For consistency, we use the same names as aligned_malloc
    void* p1 = ((void **) p2)[-1];
    free(p1);
}

int main() {
    size_t powerOfTwo = 128;
    void* memory_address = aligned_malloc(1000, powerOfTwo);
    bool isCorrectMemoryAddress = ((long) memory_address % powerOfTwo) == 0;
    cout << "Is memory address correct: " << isCorrectMemoryAddress << " Expected: 1" << endl;
    aligned_free(memory_address);
    return 0;
}