#include <iostream>
#include <map>

using namespace std;

class Node {
public:
    Node *node1;
    Node *node2;
};

typedef map<Node*, Node*> NodeMap;

Node* copyNode(Node *current, NodeMap &nodeMap);

Node* copyNode(Node *rootNode) {
    NodeMap nodeMap;
    return copyNode(rootNode, nodeMap);
}

Node* copyNode(Node *current, NodeMap &nodeMap) {
    if (current == NULL) {
        return NULL;
    }

    NodeMap::iterator iterator = nodeMap.find(current);

    if (iterator != nodeMap.end()) {
        return (*iterator).second;
    }

    Node *copiedNode = new Node;
    nodeMap[current] = copiedNode;
    copiedNode->node1 = copyNode(current->node1, nodeMap);
    copiedNode->node2 = copyNode(current->node2, nodeMap);
    return copiedNode;
}

int main() {
    Node *node1 = new Node();
    Node *node2 = new Node();
    Node *node = new Node();
    node->node1 = node1;
    node->node2 = node2;

    Node *copiedNode = copyNode(node);
    bool isNode1Copied = copiedNode->node1 != NULL;
    bool isNode2Copied = copiedNode->node2 != NULL;
    cout << "Is node 1 copied: " << isNode1Copied << " Expected: 1" << endl;
    cout << "Is node 2 copied: " << isNode2Copied << " Expected: 1" << endl;
    return 0;
}