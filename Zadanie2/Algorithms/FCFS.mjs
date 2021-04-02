export const FCFS = (tab, currentPosition) => {
    let sumOfMoves = 0;
    let time = 0;

    let queue = [];
    let newTab = tab.slice(0); //slice of array works as a copy

    newTab.sort(function (a, b) {return a.enterMoment-b.enterMoment}); //sort by enterMoment ASC

    do{
        if(newTab.length>0){ // for nonempty tab we append elements to queue

            if(!newTab.some(x=> x.enterMoment!==newTab[0].enterMoment)){
                //enterMoment is the same in every element
                queue = newTab.slice(0); //just copy newTab to queue
                newTab.length = 0; //clear tab
            }else{
                for (let i = 0; i < newTab.length; i++) { //insert elem to queue if enterMoment is now
                    if(newTab[i].enterMoment<=time){
                        queue.push(newTab[i]);
                        newTab.splice(i, 1); //delete elem from tab if you push it to queue
                        i=0;
                    }
                }

                //loop is bugged and leaves last elem and ignore it so we can do that just in case
                if(newTab.length===1 && newTab[0].enterMoment<=time){
                    queue.push(newTab[0]);
                    newTab.splice(0, 1); //delete elem from tab if you push it to queue
                }
                //sort queue for sure - useful in situation of appending more than 1 elem
                queue.sort(function (a, b) {return a.enterMoment-b.enterMoment}); //sort by enterMoment ASC
            }
        }

        if(queue.length>0){
            sumOfMoves += Math.abs(currentPosition-queue[0].cylinder);
            time += queue[0].duration;
            currentPosition = queue[0].cylinder;
            queue.splice(0,1);
        }

        time++;
    }while(newTab.length>0 || queue.length>0)

    return sumOfMoves;
}