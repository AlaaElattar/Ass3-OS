import java.util.ArrayList;

public class SRTF {
    private final ArrayList<Process> processes;
    int contextSwitch;

    SRTF(ArrayList<Process> processes, int contextSwitch) {
        this.processes = processes;
        this.contextSwitch = contextSwitch;
    }

    void start() {
        int currTime = 0;
        int complete = 0;
        int shortest = Integer.MAX_VALUE;
        int length = processes.size();
        int shortestIndex = 0;
        int finish_time;
        boolean check = false;
        ArrayList<Integer> bt = new ArrayList<>();
        int contextSwitchTime = 0;
        Process prevProcess = new Process();
        Process currProcess = new Process();
        for (int i = 0; i < length; i++) {
            bt.add(processes.get(i).getBurstTime());
        }
        while (complete != length) {
            for (int i = 0; i < length; i++) {
                if (processes.get(i).getArrivalTime() <= currTime && processes.get(i).getBurstTime() <= shortest
                        && processes.get(i).getBurstTime() != 0) {
                    if (processes.get(i).getBurstTime() == shortest
                            && processes.get(i).getArrivalTime() > currProcess.getArrivalTime()) {
                        continue;
                    } else {
                        shortest = processes.get(i).getBurstTime();
                        currProcess = processes.get(i);
                        shortestIndex = i;
                        check = true;
                    }
                }
            }
            if (check == false) {
                currTime++;
                continue;
            }
            if (currTime == 0) {
                currProcess.Execute();
            }
            if (!(currProcess.equals(prevProcess)) && currTime != 0) {
                currProcess.Execute();
                contextSwitchTime += contextSwitch;
            }

            int updatedBt = currProcess.getBurstTime() - 1;
            currProcess.setBurstTime(updatedBt);

            shortest--;
            if (shortest == 0) shortest = Integer.MAX_VALUE;

            if (currProcess.getBurstTime() == 0) {
                complete++;
                finish_time = currTime + contextSwitchTime + 1;
                check = false;
                currProcess.setWaitingTime(finish_time - currProcess.getArrivalTime() - bt.get(shortestIndex));
                currProcess.setTurnaroundTime(finish_time - currProcess.getArrivalTime());
            }
            currTime++;
            prevProcess = currProcess;
        }
    }

    public double getAverageWaitingTime() {
        double sumOfWaiting = 0.0;
        for (Process p : processes) {
            sumOfWaiting += p.getWaitingTime();
        }
        return sumOfWaiting / processes.size();
    }

    public double getAverageTurnaroundTime() {
        double sumOfTurnAround = 0.0;
        for (Process p : processes) {
            sumOfTurnAround += p.getTurnaroundTime();
        }

        return sumOfTurnAround / processes.size();
    }
}



