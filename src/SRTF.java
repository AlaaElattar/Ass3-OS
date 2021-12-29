import java.awt.*;
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
        int finish_time;
        boolean check = false;
        Process prevProcess = new Process();
        Process currProcess = new Process();
        while (complete != length) {
            for (int i = 0; i < length; i++) {
                if (processes.get(i).getArrivalTime() <= currTime && processes.get(i).processingTime <= shortest
                        && processes.get(i).getBurstTime() != 0) {
                    if (processes.get(i).processingTime == shortest
                            && processes.get(i).getArrivalTime() > currProcess.getArrivalTime()) {
                        continue;
                    } else {
                        shortest = processes.get(i).processingTime;
                        currProcess = processes.get(i);
                        check = true;
                    }
                }
            }
            if (!check) {
                currTime++;
                continue;
            }
            if (currTime == 0) {
                currProcess.Execute();
                currProcess.start.add(currTime);
                aging(currProcess,currTime);
            }
            if (!(currProcess.equals(prevProcess)) && currTime != 0) {
                currProcess.Execute();
                if (prevProcess.getBurstTime() != 0) prevProcess.end.add(currTime);
                currTime += contextSwitch;
                currProcess.start.add(currTime);
                aging(currProcess,currTime);
            }
            int updatedBt = currProcess.getBurstTime() - 1;
            currProcess.setBurstTime(updatedBt);


            if (currProcess.equals(prevProcess)) {
                currProcess.processingTime -= 1;
            }

            shortest = currProcess.processingTime;
            if (shortest <= 0) shortest = Integer.MAX_VALUE;

            if (currProcess.getBurstTime() == 0) {
                complete++;
                finish_time = currTime + 1;
                currProcess.end.add(finish_time);
                currProcess.computeWaitingTurnaroundTime();
                check = false;
                shortest = Integer.MAX_VALUE;
            }
            currTime++;
            prevProcess = currProcess;
        }
    }

    public void aging(Process currProcess,int currTime) {
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).getArrivalTime() <= currTime && processes.get(i).processingTime != 0&& !(processes.get(i).equals(currProcess)))
                processes.get(i).processingTime -= 1;
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




