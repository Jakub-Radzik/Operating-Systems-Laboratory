class Process{
    constructor(enterMoment, duration, cylinder, isRealTime,deadline) {
        this.enterMoment = enterMoment;
        this.duration = duration;
        this.cylinder = cylinder;
        this.isRealTime = isRealTime;
        this.deadline = deadline;
        this.distanceFromHead = 0; //USE IN FD-SCAN
    }

    print(){
        console.log(`Moment wejścia: ${this.enterMoment} długość: ${this.duration} cylinder: ${this.cylinder}
        czy proces czasu rzeczywistego: ${this.isRealTime} deadline: ${this.deadline}`);
    }
}

export {Process};