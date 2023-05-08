package Os_project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RowRecord {

    private SimpleStringProperty processName;
    private SimpleIntegerProperty arrivalTime;
    private SimpleIntegerProperty burstTime;
    private SimpleIntegerProperty priority;
    private SimpleIntegerProperty remainingTime;

    public RowRecord(String processName, Integer arrivalTime, Integer burstTime) {
        this.processName = new SimpleStringProperty(processName);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.remainingTime = new SimpleIntegerProperty(burstTime);
    }

    public RowRecord(String processName, Integer arrivalTime, Integer burstTime,Integer priority) {
        this.processName = new SimpleStringProperty(processName);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.priority = new SimpleIntegerProperty(priority);
        this.remainingTime = new SimpleIntegerProperty(burstTime);
    }

    public String getProcessName() {
        return processName.get();
    }

    public void setProcessName(String processName) {
        this.processName.set(processName);
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public int getBurstTime() {
        return burstTime.get();
    }

    public void setBurstTime(int burstTime) {
        this.burstTime.set(burstTime);
    }

    public int getPriority() {
        return priority.get();
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime.set(remainingTime);
    }

    public SimpleIntegerProperty getRemainingTimeProperty() {
        return remainingTime;
    }

    public void setRemainingTimeProperty(int remainingTime)
    {
        this.remainingTime = new SimpleIntegerProperty(remainingTime);
    }


    public SimpleIntegerProperty getPriorityProperty() {
        return priority;
    }

    public SimpleStringProperty getProcessNameProperty() {
        return processName;
    }

    public SimpleIntegerProperty getArrivalTimeProperty() {
        return arrivalTime;
    }

    public SimpleIntegerProperty getBurstTimeProperty() {
        return burstTime;
    }

    public void setProcessNameProperty(String processName) {
        this.processName = new SimpleStringProperty(processName);
    }

    public void setArrivalTimeProperty(int arrivalTime) {
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
    }

    public void setBurstTimeProperty(int burstTime) {
        this.burstTime = new SimpleIntegerProperty(burstTime);
    }

    public void setPriorityProperty(int priority) {
        this.priority = new SimpleIntegerProperty(priority);
    }

    @Override
    public String toString() {
        return "RowRecord{" +
                "\nProcessName : " + getProcessName() +
                "\nArrivalTime : " + getArrivalTime() +
                "\nBurstTime : " + getBurstTime() +
                '}';
    }
}
