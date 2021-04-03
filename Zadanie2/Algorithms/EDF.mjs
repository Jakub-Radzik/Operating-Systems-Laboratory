export const EDF = (tab, currentPosition) => {
    let sumOfMoves = 0;
    let time = 0;

    let queue = [];
    let queueRealTime = [];
    let newTab = tab.slice(0); //slice of array works as a copy

    newTab.sort(function (a, b) {return a.enterMoment-b.enterMoment}); //sort by enterMoment ASC

    do{
        if(newTab.length>0){ // for nonempty tab we append elements to queue
            for (let i = 0; i < newTab.length; i++) {
                if(newTab[i].enterMoment<=time){
                    if(newTab[i].isRealTime){
                        queueRealTime.push(newTab[i]);
                    }else{
                        queue.push(newTab[i]);
                    }
                    newTab.splice(i,1);
                    i--;
                }
            }

            //sort queue for sure - useful in situation of appending more than 1 elem
            queue.sort(function (a, b) {return a.enterMoment-b.enterMoment}); //sort by enterMoment ASC
            queueRealTime.sort(function (a, b) {return a.deadline-b.deadline}); //sort by deadline ASC

        }

        // console.log(`TIME: ${time}`)
        // console.log("newTab-------------------------------------------------------")
        // newTab.forEach(elem => console.log(`C:${elem.cylinder} EM:${elem.enterMoment}-- RT:${elem.isRealTime} D:${elem.deadline},`))
        // console.log("queue-------------------------------------------------------")
        // queue.forEach(elem => console.log(`C:${elem.cylinder} EM:${elem.enterMoment}-- RT:${elem.isRealTime} D:${elem.deadline},`))
        // console.log("queueRealTime-------------------------------------------------------")
        // queueRealTime.forEach(elem => console.log(`C:${elem.cylinder} EM:${elem.enterMoment}-- RT:${elem.isRealTime} D:${elem.deadline},`))

        if(queueRealTime.length>0){
            sumOfMoves += Math.abs(currentPosition-queueRealTime[0].cylinder);
            time+= (Math.abs(currentPosition-queueRealTime[0].cylinder)-1) //1 position is 1 unit of time
            time += queueRealTime[0].duration;
            currentPosition = queueRealTime[0].cylinder;
            queueRealTime.splice(0,1);
        }
        else{
            if(queue.length>0){
                sumOfMoves += Math.abs(currentPosition-queue[0].cylinder);
                time+= (Math.abs(currentPosition-queue[0].cylinder)-1) //1 position is 1 unit of time
                time += queue[0].duration;
                currentPosition = queue[0].cylinder;
                queue.splice(0,1);
            }
        }
        time++;
    }while(newTab.length>0 || queue.length>0 || queueRealTime.length>0)

    return sumOfMoves;
}