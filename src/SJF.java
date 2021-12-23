import java.util.ArrayList;
import java.util.Collections;

public class SJF {
    private ArrayList<Process> output;

    SJF()
    {
        output = new ArrayList <Process> ();
    }

    private void sort(ArrayList<Process> arrivalSort){
        Collections.sort ( arrivalSort,(o1,o2)->{
            int time1 = o1.getArrivalTime ();
            int time2 = o2.getArrivalTime ();
            if (time1==time2){
                if (o2.getBurstTime ()<time1)
                    return 1;
                else if (o2.getArrivalTime ()>time1)
                    return -1;
                else return 0;
            }else {
                if (o2.getBurstTime ()<time1)
                    return 1;
                else if (o2.getArrivalTime ()>time1)
                    return -1;
                else return 0;
            }
        });
    }

    private Process getSmallestBurst(ArrayList<Process> temp) {
        int smallestBurst = temp.get(0).getBurstTime ();
        Process p = temp.get(0);
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getBurstTime() < smallestBurst) {
                smallestBurst = temp.get(i).getBurstTime();
                p = temp.get(i);
            }
        }
        return p;
    }

    public void start(ArrayList<Process> arrivalSort){
        sort ( arrivalSort );
        ArrayList<Process> out = new ArrayList <> ();
        Process temp ;
        int time=0,counter=0;
        int lastTime =0;

        while (counter<arrivalSort.size ()){
            ArrayList<Process> arr = new ArrayList <> ();
            for (int i=0;i<arrivalSort.size ();i++){
                if (arrivalSort.get ( i ).getArrivalTime ()<=lastTime ){
                    arr.add ( arrivalSort.get ( i ) );
                }
            }

            if (arr.size ()==0){
                out.add ( arrivalSort.get ( 0 ) );
                time = arrivalSort.get ( 0 ).getArrivalTime ();
                arrivalSort.get(0).setWaitingTime(0);
                arrivalSort.get(0).setTurnaroundTime (arrivalSort.get(0).getBurstTime());
                lastTime = arrivalSort.get(0).getArrivalTime() + arrivalSort.get(0).getBurstTime();
                counter++;
            }else{
                temp = getSmallestBurst ( arr );
                temp.setWaitingTime ( lastTime-temp.getArrivalTime () );
                temp.setTurnaroundTime ( temp.getWaitingTime ()+temp.getBurstTime () );
                lastTime = lastTime+temp.getBurstTime ();
                out.add ( temp );
                counter++;
            }
        }

        output = out;
        for (int i=0;i<output.size ();i++)
        {
            System.out.println (output.get ( i ).getName ());
        }
    }

    public void print (ArrayList<Process> tmp){
        for (int i=0;i<tmp.size ();i++){
            if (tmp.get ( i ) == null){
                System.out.println ("Null" );

            }
            System.out.println (tmp.get ( i ).getName () );

        }
    }

    public double averageWaiting() {
        double sum = 0;
        for (int i = 0; i < output.size(); i++) {
            sum += output.get(i).getWaitingTime();
        }
        return (sum / output.size());
    }

    public double averageTurnaround() {
        double sum = 0;
        for (int i = 0; i < output.size(); i++) {
            sum += output.get(i).getTurnaroundTime ();
        }
        return (sum / output.size());
    }

//    public static void main ( String[] args ) {
//        process p1 = new process ("p1","red",0,3,4);
//        process p2 = new process ("p2","yellow",0,5,3);
//        process p3 = new process ("p3","black",0,2,10);
//
//        ArrayList<process> arrive = new ArrayList <> ();
//        arrive.add ( p1 );
//        arrive.add ( p2 );
//        arrive.add ( p3 );
//
//        SJF s = new SJF ();
//        s.start ( arrive );
////        s.print ( arrive );
//
//    }
}
