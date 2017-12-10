package com.university.schedule.Utils;

import com.university.schedule.model.Cycle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CycleStats {
    private Cycle cycle;
    private Integer count;
    private Double percent;


    public CycleStats(Cycle cycle, Integer count, Double percent) {
        this.cycle = cycle;
        this.count = count;
        this.percent = percent;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public StringProperty cycleProperty(){
        return new SimpleStringProperty(cycle.getName());
    }

    public StringProperty countProperty(){
        return new SimpleStringProperty(count.toString());
    }
}
