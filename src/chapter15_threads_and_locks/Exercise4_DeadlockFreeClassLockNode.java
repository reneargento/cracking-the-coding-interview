package chapter15_threads_and_locks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Rene Argento on 02/03/20.
 */
public class Exercise4_DeadlockFreeClassLockNode {
    public enum VisitState { FRESH, VISITING, VISITED }

    private List<Exercise4_DeadlockFreeClassLockNode> children;
    private int lockId;
    private Lock lock;
    private int maxLocks;

    public Exercise4_DeadlockFreeClassLockNode(int lockId, int maxLocks) {
        this.lockId = lockId;
        this.maxLocks = maxLocks;
        children = new ArrayList<>();
    }

    public void joinTo(Exercise4_DeadlockFreeClassLockNode node) {
        children.add(node);
    }

    public void remove(Exercise4_DeadlockFreeClassLockNode node) {
        children.remove(node);
    }

    // Check for a cycle by doing a depth-first search.
    // O(v + e) runtime, where v is the number of nodes in the graph and e is the number of edges
    // O(v) space
    public boolean hasCycle(Map<Integer, Boolean> touchedNodes) {
        VisitState[] visited = new VisitState[maxLocks];
        for (int i = 0; i < maxLocks; i++) {
            visited[i] = VisitState.FRESH;
        }
        return hasCycle(touchedNodes, visited);
    }

    private boolean hasCycle(Map<Integer, Boolean> touchedNodes, VisitState[] visited) {
        if (touchedNodes.containsKey(lockId)) {
            touchedNodes.put(lockId, true);
        }

        if (visited[lockId].equals(VisitState.VISITING)) {
            return true;
        } else if (visited[lockId].equals(VisitState.FRESH)) {
            visited[lockId] = VisitState.VISITING;
            for (Exercise4_DeadlockFreeClassLockNode lockNode : children) {
                if (lockNode.hasCycle(touchedNodes, visited)) {
                    return true;
                }
            }
            visited[lockId] = VisitState.VISITED;
        }
        return false;
    }

    public Lock getLock() {
        if (lock == null) {
            lock = new ReentrantLock();
        }
        return lock;
    }

    public int getLockId() {
        return lockId;
    }

}
