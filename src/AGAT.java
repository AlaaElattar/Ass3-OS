import java.util.ArrayList;
import java.util.PriorityQueue;

public class AGAT {
    ArrayList<Process> readyQueue;
    ArrayList<Process> allProcesses;
    public static int time = 0;
    ArrayList<Process> finishedProcesses = new ArrayList<>();
    double v1;
    double v2;
    ArrayList<String> processExecutionOrder = new ArrayList<>();

    public AGAT(ArrayList<Process> processes) {
        allProcesses = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            allProcesses.add(processes.get(i));
        }
        v1 = allProcesses.get(0).getArrivalTime();
        for (int i = 1; i < allProcesses.size(); i++) {
            if (allProcesses.get(i).getArrivalTime() > v1)
                v1 = allProcesses.get(i).getArrivalTime();
        }
        if (v1 < 10) {
            v1 = 1;
        } else {
            v1 /= 10;
        }
        readyQueue = new ArrayList<>();

        AGAlgorithm();
    }

    //Add arriving process to ready queue
    public void refill() {
        for (int i = 0; i < allProcesses.size(); i++) {
            if (allProcesses.get(i).getArrivalTime() <= time) {
                readyQueue.add(allProcesses.get(i));
                allProcesses.remove(i);
                i--;
            }
        }
    }

    public void endExecution(Process inExecution) {
        time += inExecution.getBurstTime();
        inExecution.end.add(time);
        inExecution.setBurstTime(0);
        inExecution.quantumTime.add(0);
        inExecution.quantum = 0;
        inExecution.computeWaitingTurnaroundTime();
        finishedProcesses.add(inExecution);
    }

    public void computeV2(Process inExecution) {
        v2 = inExecution.getBurstTime();
        for (int i = 0; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).getBurstTime() > v2) {
                v2 = readyQueue.get(i).getBurstTime();
            }
        }
        for (int i = 0; i < allProcesses.size(); i++) {
            if (allProcesses.get(i).getBurstTime() > v2) {
                v2 = allProcesses.get(i).getBurstTime();
            }
        }
        if (v2 < 10) {
            v2 = 1;
        } else {
            v2 /= 10;
        }
    }

    public void computeFactor(Process inExecution) {
        computeV2(inExecution);
        for (int i = 0; i < readyQueue.size(); i++) {
            Process p = readyQueue.get(i);
            p.factor = (10 - p.getPriority()) + (int) Math.ceil(p.getArrivalTime() / v1) + (int) Math.ceil(p.getBurstTime() / v2);
            p.AGAT.add(p.factor);
        }
        for (int i = 0; i < allProcesses.size(); i++) {
            Process p = allProcesses.get(i);
            p.factor = (10 - p.getPriority()) + (int) Math.ceil(p.getArrivalTime() / v1) + (int) Math.ceil(p.getBurstTime() / v2);
            p.AGAT.add(p.factor);
        }
        inExecution.factor = (10 - inExecution.getPriority()) + (int) Math.ceil(inExecution.getArrivalTime() / v1) + (int) Math.ceil(inExecution.getBurstTime() / v2);
        inExecution.AGAT.add(inExecution.factor);
    }

    //Returns process that has minimum AGAT factor
    public Process getMinFactor() {
        if (readyQueue.size() == 0)
            return null;
        Process minimum = readyQueue.get(0);
        for (int i = 1; i < readyQueue.size(); i++) {
            if (readyQueue.get(i).factor < minimum.factor)
                minimum = readyQueue.get(i);
        }
        return minimum;
    }

    //Selects next process to execute
    public Process executeProcess() {
        refill();
        return getMinFactor();

    }

    //Replace process because it finished its quantum time
    public Process replaceProcess(Process inExecution) {
        inExecution.end.add(time);
        refill();
        readyQueue.add(inExecution);
        Process temp = readyQueue.get(0);
        inExecution.setBurstTime(inExecution.getBurstTime() - inExecution.quantum);
        inExecution.quantumTime.add(inExecution.quantum + 2);
        inExecution.quantum += 2;
        inExecution = temp;
        readyQueue.remove(inExecution);
        inExecution.start.add(time);
        return inExecution;
    }

    public void AGAlgorithm() {
        refill();
        Process inExecution = readyQueue.get(0);
        readyQueue.remove(0);
        computeFactor(inExecution);
        int i = 0;

        processExecutionOrder.add(inExecution.getName());
        int quantum40 = (int) Math.round(inExecution.quantum * 40 / 100.0);
        inExecution.start.add(time);
        while (true) {

            if (i != 0) {
                computeFactor(inExecution);
                processExecutionOrder.add(inExecution.getName());
            }
            i++;

            if (inExecution.getBurstTime() <= quantum40) {
                endExecution(inExecution);
                refill();
                if (readyQueue.size() == 0)
                    break;
                inExecution = readyQueue.get(0);
                readyQueue.remove(0);
                inExecution.start.add(time);
                quantum40 = (int) Math.round(inExecution.quantum * 40.0 / 100.0);


            } else if (quantum40 == inExecution.quantum) {
                time += quantum40;

                inExecution = replaceProcess(inExecution);
                quantum40 = (int) Math.round(inExecution.quantum * 40 / 100.0);
            } else {
                time += quantum40;
                Process temp = executeProcess();
//                readyQueue.remove(inExecution);
//                readyQueue.add(inExecution);
                if (temp == null || (temp != null && temp.factor >= inExecution.factor)) {
                    time -= quantum40;
                    quantum40++;
                    i = 0;
                } else {
                    inExecution.setBurstTime(inExecution.getBurstTime() - quantum40);
                    inExecution.quantum += (inExecution.quantum - quantum40);
                    inExecution.quantumTime.add(inExecution.quantum);
                    inExecution.end.add(time);
                    readyQueue.add(inExecution);
                    inExecution = temp;
                    readyQueue.remove(inExecution);
                    quantum40 = (int) Math.round(inExecution.quantum * 40.0 / 100.0);
                    inExecution.start.add(time);
                }
            }
        }
    }

    public double getAverageWaitingTime(){
        double waitingTime = 0;
        for (int i = 0; i < finishedProcesses.size(); i++) {
            waitingTime += finishedProcesses.get(i).waitingTime;
        }
        return waitingTime/finishedProcesses.size();
    }
    public double getAverageTurnaroundTime(){
        double turnaround = 0;
        for (int i = 0; i < finishedProcesses.size(); i++) {
            turnaround += finishedProcesses.get(i).getTurnaroundTime();
        }
        return turnaround/finishedProcesses.size();
    }
    public void printInfo() {
        System.out.println("Process execution order: ");
        for (int i = 0; i < processExecutionOrder.size(); i++)
            System.out.print(processExecutionOrder.get(i) + " ");
        System.out.println();
        System.out.println("Average Waiting Time: " + getAverageWaitingTime());
        System.out.println("Average Turnaroud Time: " + getAverageTurnaroundTime());
        for(int i =0; i<finishedProcesses.size(); i++){
            Process p = finishedProcesses.get(i);
            System.out.println(p.getName() +": Waiting Time= " + p.waitingTime+" || Turnaround Time= " + p.getTurnaroundTime());
        }
    }
}
