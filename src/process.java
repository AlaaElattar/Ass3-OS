import java.util.ArrayList;

public class process {
    private String name;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int turnaroundTime;
    private int responseTime;
    ArrayList < Integer > quantumTime;
    ArrayList < Double > AGAT;

    process(String name, String color,int arrivalTime, int burstTime, int priority )
    {
        this.name=name;
        this.color=color;
        this.arrivalTime=arrivalTime;
        this.burstTime=burstTime;
        this.priority=priority;
        quantumTime = new ArrayList <Integer> ();
        AGAT = new ArrayList<Double> ();

    }

    public int getArrivalTime ( ) {
        return arrivalTime;
    }
    public int getBurstTime ( ) {
        return burstTime;
    }
    public int getPriority ( ) {
        return priority;
    }
    public int getTurnaroundTime ( ) {
        return turnaroundTime;
    }
    public int getResponseTime ( ) {
        return responseTime;
    }
    public String getColor ( ) {
        return color;
    }
    public String getName ( ) {
        return name;
    }
    public void setArrivalTime ( int arrivalTime ) {
        this.arrivalTime = arrivalTime;
    }
    public void setBurstTime ( int burstTime ) {
        this.burstTime = burstTime;
    }
    public void setColor ( String color ) {
        this.color = color;
    }
    public void setName ( String name ) {
        this.name = name;
    }
    public void setPriority ( int priority ) {
        this.priority = priority;
    }
    public void setResponseTime ( int responseTime ) {
        this.responseTime = responseTime;
    }
    public void setTurnaroundTime ( int turnaroundTime ) {
        this.turnaroundTime = turnaroundTime;
    }
    public ArrayList < Double > getAGAT ( ) {
        return AGAT;
    }
    public void setAGAT ( ArrayList < Double > AGAT ) {
        this.AGAT = AGAT;
    }
    public ArrayList < Integer > getQuantumTime ( ) {
        return quantumTime;
    }
    public void setQuantumTime ( ArrayList < Integer > quantumTime ) {
        this.quantumTime = quantumTime;
    }

}
