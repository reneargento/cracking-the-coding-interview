package chapter15_threads_and_locks;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * Created by Rene Argento on 02/03/20.
 */
public class Exercise4_DeadlockFreeClassLockFactory {
    private static Exercise4_DeadlockFreeClassLockFactory instance;
    private Exercise4_DeadlockFreeClassLockNode[] locks;
    // Maps from a process or owner to the order that the owner claimed it would call the locks in
    private Map<Integer, LinkedList<Exercise4_DeadlockFreeClassLockNode>> lockOrder;

    private Exercise4_DeadlockFreeClassLockFactory(int numberOfLocks) {
        locks = new Exercise4_DeadlockFreeClassLockNode[numberOfLocks];
        lockOrder = new HashMap<>();
        for (int i = 0; i < numberOfLocks; i++) {
            locks[i] = new Exercise4_DeadlockFreeClassLockNode(i, numberOfLocks);
        }
    }

    public static synchronized Exercise4_DeadlockFreeClassLockFactory initialize(int numberOfLocks) {
        if (instance == null) {
            instance = new Exercise4_DeadlockFreeClassLockFactory(numberOfLocks);
        }
        return instance;
    }

    public static Exercise4_DeadlockFreeClassLockFactory getInstance() {
        return instance;
    }

    // O(v + e) runtime, where v is the number of nodes in the graph and e is the number of edges
    // O(v) space
    private boolean hasCycle(Map<Integer, Boolean> touchedNodes, int[] resourcesInOrder) {
        for (int resource : resourcesInOrder) {
            if (!touchedNodes.get(resource)) {
                Exercise4_DeadlockFreeClassLockNode lockNode = locks[resource];
                if (lockNode.hasCycle(touchedNodes)) {
                    return true;
                }
            }
        }
        return false;
    }

    // To prevent deadlocks, force the processes to declare upfront what order they will need the locks in.
    // Verify that this order does not create a deadlock (a cycle in a directed graph)
    public boolean declare(int ownerId, int[] resourcesInOrder) {
        Map<Integer, Boolean> touchedNodes = new HashMap<>();

        // Add edges to the graph
        touchedNodes.put(resourcesInOrder[0], false);
        for (int i = 1; i < resourcesInOrder.length; i++) {
            Exercise4_DeadlockFreeClassLockNode previousLockNode = locks[resourcesInOrder[i - 1]];
            Exercise4_DeadlockFreeClassLockNode currentLockNode = locks[resourcesInOrder[i]];
            previousLockNode.joinTo(currentLockNode);
            touchedNodes.put(resourcesInOrder[i], false);
        }

        // If a cycle was created, remove edges and return false
        if (hasCycle(touchedNodes, resourcesInOrder)) {
            for (int i = 1; i < resourcesInOrder.length; i++) {
                Exercise4_DeadlockFreeClassLockNode previousLockNode = locks[resourcesInOrder[i - 1]];
                Exercise4_DeadlockFreeClassLockNode currentLockNode = locks[resourcesInOrder[i]];
                previousLockNode.remove(currentLockNode);
            }
            return false;
        }

        // No cycles detected. Save the order that was declared, so that it can be verified that the process is really
        // calling the locks in the order it said it would.
        LinkedList<Exercise4_DeadlockFreeClassLockNode> resourcesInOrderList = new LinkedList<>();
        for (int resource : resourcesInOrder) {
            resourcesInOrderList.add(locks[resource]);
        }
        lockOrder.put(ownerId, resourcesInOrderList);
        return true;
    }

    // Get the lock, verifying first that the process is really calling the locks in the order it said it would.
    public Lock getLock(int ownerId, int resourceId) {
        LinkedList<Exercise4_DeadlockFreeClassLockNode> resourcesList = lockOrder.get(ownerId);
        if (resourcesList == null || resourcesList.isEmpty()) {
            return null;
        }

        Exercise4_DeadlockFreeClassLockNode headNode = resourcesList.getFirst();
        if (headNode.getLockId() == resourceId) {
            resourcesList.removeFirst();
            return headNode.getLock();
        }
        return null;
    }

}
