package org.coffeemine.app.spring.statistics;

import org.coffeemine.app.spring.data.*;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsCalculation {

    private NitriteDBProvider dbProvider;

    public StatisticsCalculation(){

    }

    private int getSalary (ITask task){
        List<Integer> users = task.getAssignees();
        ArrayList<Integer> salary = new ArrayList<Integer>();

        for (int i = 0; i < users.size(); i++){
            int userSalary = (int) dbProvider.getUser(users.get(i)).getHourlySalary();
            salary.add(userSalary);
        }

        return salary.stream().mapToInt(Integer::intValue).sum();
    }

    public int actualValue(ITask task) {
        int actualValue = 0;
        if (task.getCompleted()) {
            List<Integer> days = dbProvider.getFragments4Task(task).map(Fragment::getDays).collect(Collectors.toList());
            int sumOfDays = days.stream().mapToInt(Integer::intValue).sum();

            actualValue = sumOfDays * getSalary(task) * task.getHours();
        }
        return actualValue;
    }

    public int actualValueSprint (ISprint sprint){
        List<ITask> tasks = dbProvider.getTasks4Sprint(sprint).collect(Collectors.toList());
        int actualValueSprint = 0;

        for (int i = 0; i< tasks.size(); i++){
            actualValueSprint = actualValueSprint + actualValue(tasks.get(i));
        }

        return actualValueSprint;
    }

    public int plannedValue(ISprint sprint, ITask task) {

        int totalDays = (int) ChronoUnit.DAYS.between(sprint.getStart(), sprint.getEnd());

        return totalDays * task.getHours() * getSalary(task);
    }

    public int plannedValueSprint(ISprint sprint){

        List<ITask> tasks = dbProvider.getTasks4Sprint(sprint).collect(Collectors.toList());
        int plannedValueSprint = 0;

        for (int i = 0; i < tasks.size(); i++){
            plannedValueSprint = plannedValueSprint + plannedValue(sprint, tasks.get(i));
        }
        return plannedValueSprint;
    }

    //Other methods that need tasks and sprints to be implemented
    public int earnedValue (ISprint sprint){

        List<ITask> tasks = dbProvider.getTasks4Sprint(sprint).collect(Collectors.toList());
        int earnedValue = 0;

        for (int i = 0; i < tasks.size(); i++){
            if (tasks.get(i).getCompleted()){
                earnedValue = earnedValue + plannedValue(sprint, tasks.get(i));
            }
        }
       return earnedValue;
    }

    public int costVariance(ISprint sprint){
        return earnedValue(sprint) - actualValueSprint(sprint);
    }

    public int scheduleVariance(ISprint sprint){
        //return this.earnedValue - this.plannedValue;
        return earnedValue(sprint) - plannedValueSprint(sprint);
    }

    public double costPerformanceIndex(ISprint sprint){
        return (earnedValue(sprint) / actualValueSprint(sprint)) * 100;
    }

    public double schedulePerformanceIndex(ISprint sprint){
        return (earnedValue(sprint) / plannedValueSprint(sprint)) * 100;
    }

}
