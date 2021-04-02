export const SCAN = (tab, start, end, currentPosition) => {
    let sumOfMoves = 0;
    let time = 0;
    let newTab = tab.slice(0); //slice of array works as a copy
    let moveRight = false;
    do{
        if(moveRight){
            for (let i = currentPosition; i < end; i++) {
                currentPosition = end;
                for (let j = 0; j < newTab.length; j++) {
                    if(newTab[j].cylinder===i){
                        time+=newTab[j].duration;
                        // console.log(newTab[j].cylinder)
                        newTab.splice(j,1);

                        if(newTab.length===0){
                            return sumOfMoves;
                        }

                    }
                }
                sumOfMoves++;
            }
            moveRight = false;
        }else{
            for (let i = currentPosition; i > start; i--) {
                currentPosition = start;
                for (let j = 0; j < newTab.length; j++) {
                    if(newTab[j].cylinder===i){
                        time+=newTab[j].duration;
                        // console.log(newTab[j].cylinder)
                        newTab.splice(j,1);

                        if(newTab.length===0){
                            return sumOfMoves;
                        }
                    }
                }
                sumOfMoves++;
            }
            moveRight = true;
        }
    }while(newTab.length>0)

    return sumOfMoves;
}