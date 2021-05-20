export const FDSCAN = (tab,start,end, currentPosition) => {
    let sumOfMoves = 0;
    let time = 0;
    let destination = null;
    let rightMove = true;

    let queue = [];
    let queueRealTime = [];
    let newTab = tab.slice(0); //slice of array works as a copy

    newTab.sort(function (a, b) {return a.enterMoment-b.enterMoment}); //sort by enterMoment ASC

    do{
        //GENERATE LISTS
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

        if(queueRealTime.length>0){
            //SET DISTANCE FROM HEAD FOR EVERY PRIORITY PROCESS
            for (let i = 0; i < queueRealTime.length; i++) {
                queueRealTime[i].distance = Math.abs(queueRealTime[i].cylinder-currentPosition);
            }
            //FIND PROCESS FDSCAN NEED
            if(queueRealTime.length===1){
                destination = queueRealTime[0].cylinder;
                if(destination>=currentPosition || destination===null){
                    rightMove = true
                }else{
                    rightMove = false;
                }
            }else if(queueRealTime.length>=1){
                destination = null;
                let temporaryDistance = end*2;

                for (let i = 0; i < queueRealTime.length; i++) {
                    let elemDistance = queueRealTime[i].distance
                    if(elemDistance<queueRealTime[i].deadline && elemDistance<temporaryDistance){
                        temporaryDistance = elemDistance;
                        destination = queueRealTime[i].cylinder;
                    }
                }

            }

        }

        for (let i = 0; i < queueRealTime.length; i++) {
            if(queueRealTime[i].cylinder===currentPosition){
                time+=queueRealTime[i].duration;
                queueRealTime.splice(i,1);
                i--;
            }
        }

        for (let i = 0; i < queue.length; i++) {
            if(queue[i].cylinder===currentPosition){
                time+=queue[i].duration;
                queue.splice(i,1);
                i--;
            }
        }


        // console.log(`TIME: ${time}`)
        // console.log("newTab-------------------------------------------------------")
        // newTab.forEach(elem => console.log(`C:${elem.cylinder} EM:${elem.enterMoment}-- RT:${elem.isRealTime} D:${elem.deadline},`))
        // console.log("queue-------------------------------------------------------")
        // queue.forEach(elem => console.log(`C:${elem.cylinder} EM:${elem.enterMoment}-- RT:${elem.isRealTime} D:${elem.deadline},`))
        // console.log("queueRealTime-------------------------------------------------------")
        // queueRealTime.forEach(elem => console.log(`C:${elem.cylinder} EM:${elem.enterMoment}-- RT:${elem.isRealTime} D:${elem.deadline},`))


        if(rightMove){
            currentPosition++;
        }else{
            currentPosition--;
        }

        if(currentPosition===end){
            rightMove = false;
        }

        if(currentPosition===start){
            rightMove = true;
        }

        sumOfMoves++;
        time++;
    }while(newTab.length>0 || queue.length>0 || queueRealTime.length>0)

    return sumOfMoves;
}