import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    public static int menu() {
        int num;

        System.out.println("1- SJF");
        System.out.println("2- SRTF");
        System.out.println("3- Priority Scheduler ");
        System.out.println("4- AG Scheduler ");

        Scanner input = new Scanner(System.in);
        num = input.nextInt();

        return num;

    }

    public static void main(String[] args) {
        ArrayList<Process> processArrayList = new ArrayList<>();
        String name;
        String color;
        int arrivalTime;
        int burstTime;
        int priorityNum;

        Scanner input = new Scanner(System.in);

        int numOfProcess;
        int rrQuantum;
        int contextProcess;
        int choiceNum;

        System.out.println("Enter number of process");
        numOfProcess = input.nextInt();


        System.out.println("Enter Context Switching ");
        contextProcess = input.nextInt();

        for (int i = 0; i < numOfProcess; i++) {
            System.out.println("Enter process name");
            name = input.next();
            System.out.println("Enter process color");
            color = input.next();
            System.out.println("Enter process arrival time");
            arrivalTime = input.nextInt();
            System.out.println("Enter process burst time");
            burstTime = input.nextInt();
            System.out.println("Enter process priority");
            priorityNum = input.nextInt();
            System.out.println("Enter process quantum");
            rrQuantum = input.nextInt();

            Process p = new Process(name, color, arrivalTime, burstTime, priorityNum, rrQuantum);
            processArrayList.add(p);
        }

        choiceNum = menu();

        if (choiceNum == 1){
            SJF  sjf = new SJF ( processArrayList );
            sjf.startScheduling ();
            System.out.println ("Average Waiting time : "+ sjf.getAverageTurnaroundTime () );
            System.out.println ("Average Turnaround time : "+sjf.getAverageTurnaroundTime () );

        }

        if (choiceNum == 2){
            SRTF srtf = new SRTF(processArrayList,contextProcess);
            srtf.start();
            System.out.println ("Average Waiting time : "+ srtf.getAverageWaitingTime() );
            System.out.println ("Average Turnaround time : "+srtf.getAverageTurnaroundTime () );
        }

        if (choiceNum == 3){
            PriorityScheduling priority = new PriorityScheduling (processArrayList);
            List<String>data = priority.start ( processArrayList );
            for (String x:data){
                System.out.println (x );
                System.out.println ( "Average Waiting time : "+ priority.getAverageWaiting ());
                System.out.println ( "Average Turnaround time : "+ priority.getAverageTurnAround ());
            }

        }

        if (choiceNum == 4) {
            AGAT a = new AGAT(processArrayList);
            a.printInfo();
        }

        for(int i = 0; i < processArrayList.size(); i++){
            System.out.print(processArrayList.get(i).getName() +" Factor- ");
            for(int j =0; j<processArrayList.get(i).AGAT.size(); j++){
                System.out.print(processArrayList.get(i).AGAT.get(j)+" ");
            }
            System.out.println();
            System.out.print(processArrayList.get(i).getName() +" Quantum- ");
            for(int j =0; j<processArrayList.get(i).quantumTime.size(); j++){
                System.out.print(processArrayList.get(i).quantumTime.get(j)+" ");
            }
            System.out.println();
        }
    }
}
