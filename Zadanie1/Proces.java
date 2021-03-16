package Zadanie1;

import java.util.Comparator;
import java.util.StringJoiner;

public class Proces
{
    private int procesNumber;
    private int duration;
    private int enterMoment;
    private int timeLeft;
    private int waitingTime;


    public Proces(int procesNumber, int enterMoment, int duration, int timeLeft, int waitingTime) {
        this.procesNumber = procesNumber;
        this.duration = duration;
        this.enterMoment = enterMoment;
        this.timeLeft = timeLeft;
        this.waitingTime = 0;
    }

    public int getProcesNumber() {
        return procesNumber;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public int getEnterMoment() {
        return enterMoment;
    }
    public int getWaitingTime()
    {
        return waitingTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setTimeLeft(int s)
    {
        this.timeLeft = s;
    }

    public void setProcesNumber(int procesNumber) {
        this.procesNumber = procesNumber;
    }

    public void setDuration(int s) {
        this.duration = s;
    }
    public static class Comparators {
        public static Comparator<Proces> ComparatorEnterMoment = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.enterMoment - o2.enterMoment;
            }
        };
        public static Comparator<Proces> ComparatorDuration = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.duration - o2.duration;
            }
        };
        public static Comparator<Proces> ComparatorTimeLeft = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.timeLeft - o2.timeLeft;
            }
        };
        public static Comparator<Proces> ComparatorProcesNumber = new Comparator<Proces>() {
            @Override
            public int compare(Proces o1, Proces o2) {
                return o1.procesNumber - o2.procesNumber;
            }
        };
        public static Comparator<Proces> ComparatorProcesNumberEnterMoment = new Comparator<Proces>()
        {
            @Override
            public int compare(Proces o1, Proces o2) {
                int result =o1.procesNumber - o2.procesNumber;
                if (result != 0) {
                    return result;
                } else
                {
                    return o1.enterMoment - o2.enterMoment;
                }
            }
        };




    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Proces.class.getSimpleName() + "[", "]")
                .add("procesNumber=" + procesNumber)
                .add("duration=" + duration)
                .add("enterMoment=" + enterMoment)
                .add("timeLeft=" + timeLeft)
                .add("waitingTime=" + waitingTime)
                .toString();
    }
}