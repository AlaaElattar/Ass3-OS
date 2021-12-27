import java.util.ArrayList;

public class PriorityScheduling {
    ArrayList<Process> Processes = new ArrayList<Process>();
    ArrayList<Process> WaitingQueue = new ArrayList<Process>();
    ArrayList<Process> executedProcesses = new ArrayList<Process>();
    ArrayList<Process> copy ;
    private int contextSwitching;

    int curTime=0;
    int agingValue =0;  //to solve starvation problem

    PriorityScheduling(ArrayList<Process> temp, int contextSwitching){
        this.contextSwitching = contextSwitching;
        for(Process i : temp)
        {
            Processes.add(new Process(i));
        }

        copy = new ArrayList<Process>(Processes);
        Processes.sort(null);
        curTime = Processes.get(0).getArrivalTime();
        ConstructWaitingQueue(curTime);
    }

    private void ConstructWaitingQueue(int currentTime) {
        WaitingQueue = new ArrayList<Process>();
        for(int i = 0 ; i < Processes.size() ; i++ )
            if(Processes.get(i).getArrivalTime() <= currentTime) {
                WaitingQueue.add(Processes.get(i));
            }
            else break;
    }


    public void start() {
        Process currentProcess = new Process();

        while(Processes.size() > 0 ) {
            if(FindMaxPriorityInWaiting()==null) {
                curTime++;
                ConstructWaitingQueue(curTime);
            }
            else {
                currentProcess = FindMaxPriorityInWaiting();

                currentProcess.setStartTime(curTime);
                curTime += currentProcess.getBurstTime();

                currentProcess.setWaitingTime( currentProcess.getStartTime() - currentProcess.getArrivalTime());
                currentProcess.setTurnaroundTime(currentProcess.getWaitingTime() + currentProcess.getBurstTime() );

                executedProcesses.add(currentProcess);
                Processes.remove(currentProcess);

                ConstructWaitingQueue(curTime);
                AgingProcess(agingValue);
                currentProcess.Execute();

            }
        }
    }

    private void AgingProcess(int time) {
        Process temp = new Process();
        for(int i = 0 ; i < WaitingQueue.size() ; i++ ) {
            temp = WaitingQueue.get(i);
            if(temp.getPriority() > 0 && temp!= FindMaxPriorityInWaiting()) {
                int IncreasesPrioroty = (curTime - temp.getLastTimeAged()) / time;

                if (IncreasesPrioroty <0){
                    IncreasesPrioroty =0;
                }

                temp.setPriority(temp.getPriority() - IncreasesPrioroty);
                temp.setLastTimeAged(curTime + ((curTime - temp.getLastTimeAged()) % time));
            }
        }
    }

    private Process FindMaxPriorityInWaiting() {
        Process maxPriority = null;
        if(WaitingQueue.size()>0) {
            maxPriority = WaitingQueue.get(0);
            for(int i = 1 ; i< WaitingQueue.size() ; i++) {
                if(maxPriority.getPriority() >= WaitingQueue.get(i).getPriority()) {
                    if(maxPriority.getPriority() == WaitingQueue.get(i).getPriority())
                    {
                        if (maxPriority.getArrivalTime() > WaitingQueue.get(i).getArrivalTime()){
                            maxPriority = WaitingQueue.get ( i );
                        }
                    }
                    else
                        maxPriority = WaitingQueue.get(i);
                }
            }
        }
        return maxPriority ;
    }

    public int getAgingValue ( ) {
        return agingValue;
    }
    public void setAgingValue ( int agingValue ) {
        this.agingValue = agingValue;
    }

    public double getAverageWaiting() {
        double sumOfWaiting = 0.0;
        for(Process p : executedProcesses) {
            sumOfWaiting+=p.getWaitingTime();
        }
        return sumOfWaiting / executedProcesses.size();
    }
    public double getAverageTurnAround() {
        double sumOfTurnAround = 0.0;
        for(Process p : executedProcesses) {
            sumOfTurnAround+=p.getTurnaroundTime();
        }
        return sumOfTurnAround / executedProcesses.size();
    }


}

