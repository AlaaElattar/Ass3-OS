import java.util.ArrayList;

public class Process {
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int turnaroundTime;
    public int waitingTime;
    ArrayList<Integer> quantumTime;
    ArrayList<Integer> AGAT;
    public int factor;
    public ArrayList<Integer> start;
    public ArrayList<Integer> end;
    public int pID;
    public static int id = 0;
    public int quantum;
    private int processingTime;

    Process(){}

    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = quantum;
        quantumTime = new ArrayList<Integer>();
        AGAT = new ArrayList<Integer>();
        start = new ArrayList<Integer>();
        end = new ArrayList<Integer>();
        waitingTime = 0;
        turnaroundTime = 0;
        pID = ++id;
        quantumTime.add(quantum);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }


    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public ArrayList<Integer> getAGAT() {
        return AGAT;
    }

    public void setAGAT(ArrayList<Integer> AGAT) {
        this.AGAT = AGAT;
    }

    public ArrayList<Integer> getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(ArrayList<Integer> quantumTime) {
        this.quantumTime = quantumTime;
    }

    public void computeWaitingTurnaroundTime(){
        waitingTime = start.get(0) - arrivalTime;
        for (int i =1; i<start.size(); i++){
            waitingTime+=start.get(i) - end.get(i-1);
        }
        turnaroundTime = end.get(end.size()-1) - arrivalTime;
    }

    void Execute(){
        System.out.println( "Process " + name );
        processingTime--;
    }


}
