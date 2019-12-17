package org.coffeemine.app.spring.statistics;

import org.coffeemine.app.spring.data.Fragment;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.DBProvider;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.temporal.ChronoUnit;

public class StatisticsCalculation {

    private DBProvider dbProvider() {
        return NitriteDBProvider.getInstance();
    }

    public StatisticsCalculation() {
    }

    private float getSalary(ITask task) {
        return dbProvider()
                .getUsers()
                .filter(u -> task.getAssignees().contains(u.getId()))
                .map(User::getHourlySalary)
                .reduce(0.0f, Float::sum);
    }

    public float actualValue(ITask task) {
        float actual_value = 0;
        if (task.isCompleted()) {
            final var sum_of_days = dbProvider()
                    .getFragments4Task(task)
                    .map(Fragment::getHours)
                    .mapToInt(Integer::intValue)
                    .sum();

            actual_value = sum_of_days * getSalary(task) * task.getHours();
        }
        return actual_value;
    }

    public float actualValueSprint(ISprint sprint) {
        return dbProvider()
                .getTasks4Sprint(sprint)
                .reduce(0.0f, (f, t) -> f + actualValue(t), Float::sum);
    }

    public float plannedValue(ISprint sprint, ITask task) {
        int totalDays = (int) ChronoUnit.DAYS.between(sprint.getStart(), sprint.getEnd());

        return totalDays * task.getHours() * getSalary(task);
    }

    public float plannedValueSprint(ISprint sprint) {
        return dbProvider()
                .getTasks4Sprint(sprint)
                .reduce(0.0f, (f, t) -> f + plannedValue(sprint, t), Float::sum);

    }

    //Other methods that need tasks and sprints to be implemented
    public float earnedValue(ISprint sprint) {
        return dbProvider()
                .getTasks4Sprint(sprint)
                .reduce(0.0f, (f, t) -> f + plannedValue(sprint, t), Float::sum);
    }

    public float costVariance(ISprint sprint) {
        return earnedValue(sprint) - actualValueSprint(sprint);
    }

    public float scheduleVariance(ISprint sprint) {
        return earnedValue(sprint) - plannedValueSprint(sprint);
    }

    public double costPerformanceIndex(ISprint sprint){
        return (earnedValue(sprint) / actualValueSprint(sprint)) * 100;
    }

    public double schedulePerformanceIndex(ISprint sprint){
        return (earnedValue(sprint) / plannedValueSprint(sprint)) * 100;
    }

}
