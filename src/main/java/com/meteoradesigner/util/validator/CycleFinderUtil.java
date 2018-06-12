package com.meteoradesigner.util.validator;

import com.meteoradesigner.model.Task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

//TODO documentation.
/**
 * This @code{CycleFinderUtil} class declares data structures cycle finder utility.
 */
public class CycleFinderUtil {

    public List<Task> findCycle(List<Task> toFindIn)
            throws IllegalStateException {

        Queue<Task> cycle = new ArrayDeque<>();
        Map<Task, Object> marked = new HashMap<>();
        Map<Task, Object> stacked = new HashMap<>();
        Map<Task, Task> edgeTo = new HashMap<>();

        for (Task task : toFindIn) {
            if (cycle.size() != 0) {
                return new ArrayList<>(cycle);
            }
            if (marked.get(task) == null) {
                checkCycleByDepthFirstSearch(task, marked, stacked, edgeTo, cycle);
            }
        }
        return new ArrayList<>(cycle);
    }

    private void checkCycleByDepthFirstSearch(Task previousEdge,
                                              Map<Task, Object> marked,
                                              Map<Task, Object> stacked,
                                              Map<Task, Task> edgeTo,
                                              Queue<Task> cycle
    ) {
        marked.put(previousEdge, new Object());
        stacked.put(previousEdge, new Object());
        for (Task nextEdge : previousEdge.getInternalTasks()) {

            if (cycle.size() != 0) {
                return;
            }

            if (marked.get(nextEdge) == null) {
                edgeTo.put(nextEdge, previousEdge);
                checkCycleByDepthFirstSearch(nextEdge, marked, stacked, edgeTo, cycle);
            }

            if (stacked.get(nextEdge) != null) {
                cycle = new ArrayDeque<>();

                Task temp = previousEdge;

                while (temp != null && !temp.equals(nextEdge)) {
                    cycle.offer(temp);
                    temp = edgeTo.get(temp);
                }

                cycle.offer(nextEdge);
                cycle.offer(previousEdge);
            }
        }
        stacked.put(previousEdge, null);
    }
}