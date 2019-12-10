package org.coffeemine.app.spring.stastics;

import java.util.Date;

public class StatisticsCalculation {

    /*private Date plannedStart;
    private Date plannedEnd;
    private Date actualEnd;
    private int salary;
    private int hoursPerDay;
    private int numberOfPeople;
    private boolean completed;*/

    public StatisticsCalculation() {

    }

    public static int plannedValue(Date plannedStart, Date plannedEnd, int hours, int salary, int numberOfPeople) {
        int totalDays = (int) Math.round((plannedStart.getTime() - plannedEnd.getTime()) / (1000 * 60 * 60 * 24));
        int plannedValue = totalDays * hours * salary * numberOfPeople;
        return plannedValue;
    }

    public static int actualValue(Date plannedStart, Date actualEnd, int hours, int salary, int numberOfPeople) {
        int totalDays = (int) Math.round((plannedStart.getTime() - actualEnd.getTime()) / (1000 * 60 * 60 * 24));
        int actualValue = totalDays * hours * salary * numberOfPeople;
        return actualValue;
    }

    //Other methods that need tasks and sprints to be implemented
    /*public static int earnedValue (boolean completed){
        int sum = 0;
        if (completed){
            sum = sum + task.plannedValue;
        }
        return sum;
    }*/

    /*public static int costVariance(){
        return this.earnedValue - this.actualValue;
    }*/

    /*public static int scheduleVariance(){
        return this.earnedValue - this.plannedValue;
    }*/

    /*public static double costPerformanceIndex(){
        return (this.earnedValue / this.actualValue) * 100;
    }*/

    /*public static double schedulePerformanceIndex(){
        return (this.earnedValue / this.plannedValue) * 100;
    }*/

}
