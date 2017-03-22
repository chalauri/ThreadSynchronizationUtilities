package main.synchronizing_tasks_in_common_point;

/**
 * Created by G.Chalauri on 03/22/17.
 */
public class Results {

    private int data[];

    public Results(int size){
        data=new int[size];
    }

    public void setData(int position, int value){
        data[position]=value;
    }

    public int[] getData(){
        return data;
    }
}
