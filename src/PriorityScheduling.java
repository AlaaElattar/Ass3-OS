import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

//Non-Preemptive Priority Scheduling with context switching
public class PriorityScheduling {
    private Comparator<Process> sortProcesses;  //sort processes first, then put them in ready queue
    private PriorityQueue < Process > readyQueue;
    private ArrayList<Process> arrivalQueue;
    private ArrayList<String>order;
    private ArrayList<Process> executedProcesses=new ArrayList<> ();
    int time;

    PriorityScheduling(ArrayList<Process> arrivalQueue)
    {
        sortProcesses = Comparator.comparing ( Process::getPriority );
        readyQueue = new PriorityQueue<Process> (sortProcesses);
        order = new ArrayList <String> ();
        time =0;
        this.arrivalQueue = arrivalQueue;
    }

    public boolean isDead(Process temp){
        if (temp.getBurstTime ()==0){
            return true;
        }
        return false;
    }

    public void resetPriority(Process temp, int val){
        int priority = temp.getPriority ();
        temp.setPriority (priority-val);
    }

    public void setWaitingTime(Process temp){
        int arrivalTime = temp.getArrivalTime ();
        int burstTime = temp.getBurstTime ( );
        temp.setWaitingTime (time-(arrivalTime+burstTime));
    }

    private void afterSecond(Process currentProcess, int val){
        if (!isDead(currentProcess)) {
            int burst = currentProcess.getBurstTime ();
            currentProcess.setBurstTime(burst - 1);
            order.add(currentProcess.getName());
        } else {
            order.add(null);
        }
        time++;
    }

    public List<String>start( ArrayList<Process> arrivalQueue){

        Process curProcess = new Process ();
        int i=0;
        int value=0;

        while (true){
            if (!arrivalQueue.isEmpty () && time< arrivalQueue.get ( 0 ).getArrivalTime ()){
                order.add ( null );
                time++;
            }

            while (i != arrivalQueue.size() && time >= arrivalQueue.get(i).getArrivalTime()) {
                curProcess = arrivalQueue.get(i);   //from arrival queue to ready queue
                readyQueue.add(curProcess);
                i++;
            }
            break;
        }

        curProcess = readyQueue.poll ();   //curProcess = first process in queue
        executedProcesses.add ( curProcess );
        while ( true ){

            if (readyQueue.isEmpty() && isDead(curProcess) && i == arrivalQueue.size())
                break;

            while (i < arrivalQueue.size() && time >= arrivalQueue.get(i).getArrivalTime()) {
                resetPriority(arrivalQueue.get(i), value);
                readyQueue.add(arrivalQueue.get(i));
                i++;
            }

            if(isDead ( curProcess )){
                setWaitingTime ( curProcess );
                if(!readyQueue.isEmpty ()){
                    curProcess=readyQueue.poll ();
                    value--;
                }
            }
            afterSecond ( curProcess,value++);

        }
        return order;
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
