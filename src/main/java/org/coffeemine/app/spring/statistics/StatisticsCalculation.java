package org.coffeemine.app.spring.statistics;

import org.coffeemine.app.spring.data.*;
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
        final var sum_of_days = dbProvider()
                .getFragments4Task(task)
                .map(Fragment::getHours)
                .mapToInt(Integer::intValue)
                .sum();

        return sum_of_days * getSalary(task) * task.getHours();
    }

    public float actualValueSprint(ISprint sprint) {
        return dbProvider()
                .getTasks4Sprint(sprint)
                .filter(ITask::isCompleted)
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

    public float earnedValue(ISprint sprint) {
        return dbProvider()
                .getTasks4Sprint(sprint)
                .filter(ITask::isCompleted)
                .reduce(0.0f, (f, t) -> f + plannedValue(sprint, t), Float::sum);
    }

    public float costVariance(ISprint sprint) {
        return earnedValue(sprint) - actualValueSprint(sprint);
    }

    public float scheduleVariance(ISprint sprint) {
        return earnedValue(sprint) - plannedValueSprint(sprint);
    }

    public double costPerformanceIndex(ISprint sprint){
        if (actualValueSprint(sprint) != 0) {
            return (earnedValue(sprint) / actualValueSprint(sprint)) * 100;
        } else {
            return 0;
        }
    }

    public double schedulePerformanceIndex(ISprint sprint){
        if (plannedValueSprint(sprint) != 0) {
            return (earnedValue(sprint) / plannedValueSprint(sprint)) * 100;
        } else {
            return 0;
        }
    }

}
