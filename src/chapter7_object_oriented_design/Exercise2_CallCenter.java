package chapter7_object_oriented_design;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rene Argento on 02/08/19.
 */
public class Exercise2_CallCenter {

    private enum Rank {
        RESPONDENT(0), MANAGER(1), DIRECTOR(2);
        private int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() { return  value; }
    }

    public class Caller {
        private String callerId;

        Caller(String callerId) {
            this.callerId = callerId;
        }

        public String getCallerId() {
            return callerId;
        }
    }

    public class Call {
        private Caller caller;
        private Employee handler; // Employee who is handling call
        private Rank rank; // Minimal rank of employee who can handle this call

        Call(Caller caller) {
            this.caller = caller;
            rank = Rank.RESPONDENT;
        }

        public void reply(String message) {
            // Reply to the call
        }

        public Rank incrementRank() {
            if (rank == Rank.RESPONDENT) {
                rank = Rank.MANAGER;
            } else if (rank == Rank.MANAGER) {
                rank = Rank.DIRECTOR;
            }
            // If rank is director, it cannot be incremented any further

            return rank;
        }

        public void disconnect() {
            // Disconnect from call
        }

        public void setHandler(Employee handler) {
            this.handler = handler;
        }
        public Employee getHandler() { return handler; }

        public void setRank(Rank rank) { this.rank = rank; }
        public Rank getRank() { return  rank; }

        public Caller getCaller() { return caller; }
    }

    public abstract class Employee {
        private Call currentCall;
        protected Rank rank;

        public void receiveCall(Call call) {
            currentCall = call;
            // Start the conversation
        }

        public void callCompleted() {
            // Finish call
            currentCall = null;
        }

        public boolean isAvailable() { return currentCall == null; }
        public Rank getRank() { return rank; }
    }

    public class Respondent extends Employee {
        public Respondent() {
            rank = Rank.RESPONDENT;
        }
    }

    public class Manager extends Employee {
        public Manager() {
            rank = Rank.MANAGER;
        }
    }

    public class Director extends Employee {
        public Director() {
            rank = Rank.DIRECTOR;
        }
    }

    public class CallHandler {
        private final int INDEX_DIRECTOR = 2;

        // Initialize 10 respondents, 4 managers and 2 directors
        private final int NUMBER_OF_RESPONDENTS = 10;
        private final int NUMBER_OF_MANAGERS = 4;
        private final int NUMBER_OF_DIRECTORS = 2;

        private List<Employee> respondents;
        private List<Employee> managers;
        private List<Employee> directors;

        List<List<Employee>> employees;
        List<LinkedList<Call>> callQueues;

        public CallHandler() {
            employees = new ArrayList<>(3);

            respondents = getRespondents();
            managers = getManagers();
            directors = getDirectors();

            employees.add(respondents);
            employees.add(managers);
            employees.add(directors);

            callQueues = new LinkedList<>();
            initializeCallQueues();
        }

        // Routes the call to an available employee or saves in a queue if no employee is available
        public void dispatchCall(Caller caller) {
            Call call = new Call(caller);
            dispatchCall(call);
        }

        private void dispatchCall(Call call) {
            // Try to route the call to an employee
            Employee employee = getHandlerForCall(call);

            if (employee != null) {
                employee.receiveCall(call);
                call.setHandler(employee);
            } else {
                // Place the call into the corresponding queue according to its rank
                call.reply("Please wait for a free employee to reply");
                int callRankValue = call.getRank().getValue();
                callQueues.get(callRankValue).add(call);
            }
        }

        private Employee getHandlerForCall(Call call) {
            Employee handler = null;
            int callRankValue = call.getRank().getValue();

            while (callRankValue <= INDEX_DIRECTOR) {
                for (Employee employee : employees.get(callRankValue)) {
                    if (employee.isAvailable()) {
                        handler = employee;
                        break;
                    }
                }

                if (handler != null) {
                    break;
                }
                // Escalate call
                callRankValue++;
            }


            return handler;
        }

        // Assign a call if an employee is now free
        public boolean assignCall(Employee employee) {
            boolean assignedCall = false;
            int employeeRankValue = employee.getRank().getValue();
            LinkedList<Call> callQueue = callQueues.get(employeeRankValue);

            if (!callQueue.isEmpty() && employee.isAvailable()) {
                Call nextCall = callQueue.poll();
                employee.receiveCall(nextCall);
                assignedCall = true;
            }

            return assignedCall;
        }

        private void initializeCallQueues() {
            callQueues.add(new LinkedList<>());
            callQueues.add(new LinkedList<>());
            callQueues.add(new LinkedList<>());
        }

        // Mock data
        private List<Employee> getRespondents() {
            List<Employee> respondents = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_RESPONDENTS; i++) {
                respondents.add(new Respondent());
            }
            return respondents;
        }

        private List<Employee> getManagers() {
            List<Employee> managers = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_MANAGERS; i++) {
                managers.add(new Manager());
            }
            return managers;
        }

        private List<Employee> getDirectors() {
            List<Employee> directors = new ArrayList<>();
            for (int i = 0; i < NUMBER_OF_DIRECTORS; i++) {
                directors.add(new Director());
            }
            return directors;
        }
    }

}
