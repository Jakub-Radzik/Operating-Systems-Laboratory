export const CSCAN = (tab, start, end, currentPosition) => {
    let sumOfMoves = 0;
    let time = 0;
    let newTab = tab.slice(0); //slice of array works as a copy

    while(newTab.length>0){
        for (let j = 0; j < newTab.length; j++) {
            if(newTab[j].cylinder===currentPosition && newTab[j].enterMoment<=time){
                time+=newTab[j].duration;
                newTab.splice(j,1);
            }

            if(newTab.length===0){
                return sumOfMoves;
            }
        }

        if(currentPosition===end){
            currentPosition=start-1;
        }

        currentPosition++;
        sumOfMoves++;
        time++;
    }


    return sumOfMoves;
}