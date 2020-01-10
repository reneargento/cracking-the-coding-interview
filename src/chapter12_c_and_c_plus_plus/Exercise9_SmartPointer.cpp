#include <iostream>

using namespace std;

template <class T> class SmartPointer {
    T *object;
    unsigned *references_count;
public:
    SmartPointer(T *object) {
        this->object = object;
        references_count = (unsigned *)malloc(sizeof(unsigned));
        *references_count = 1;
    }

    SmartPointer(SmartPointer<T> &smartPointer) {
        this->object = smartPointer.object;
        this->references_count = smartPointer.references_count;
        (*references_count)++;
    }

    /*
     * Override the equal operator, so that when one smart pointer is set equal to another, the old smart pointer has
     * its reference count decremented and the new smart pointer has its reference count incremented.
     */
    SmartPointer<T> & operator=(SmartPointer<T> &smartPointer) {
        if (this == &smartPointer) return *this;

        // If already assigned to an object, remove one reference
        if (*references_count > 0) {
            remove();
        }

        this->object = smartPointer.object;
        this->references_count = smartPointer.references_count;
        (*references_count)++;
        return *this;
    }

    ~SmartPointer() {
        remove();
    }

    T getValue() {
        return *object;
    }

    unsigned referencesCount() {
        return *references_count;
    }

protected:
    void remove() {
        (*references_count)--;
        if (references_count == 0) {
            delete object;
            free(references_count);
            object = NULL;
            references_count = NULL;
        }
    }
};

int main() {
    string object = "Rene";
    SmartPointer<string> *smartPointer = new SmartPointer<string>(&object);
    cout << "References: " << smartPointer->referencesCount() << " Expected: 1" << endl;

    SmartPointer<string> *smartPointer2 = new SmartPointer<string>(*smartPointer);
    cout << "References: " << smartPointer->referencesCount() << " Expected: 2" << endl;

    delete smartPointer2;
    cout << "References: " << smartPointer->referencesCount() << " Expected: 1" << endl;
    return 0;
}