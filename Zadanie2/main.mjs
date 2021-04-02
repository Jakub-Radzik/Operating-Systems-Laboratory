import {Process} from "./process.mjs";
import {FCFS} from "./Algorithms/FCFS.mjs";
import {SSTF} from "./Algorithms/SSTF.mjs";
import {SCAN} from "./Algorithms/SCAN.mjs";
import {CSCAN} from "./Algorithms/CSCAN.mjs";
import {EDF} from "./Algorithms/EDF.mjs";
import {FDSCAN} from "./Algorithms/FDSCAN.mjs";

//VARIABLES
const startCylinder = 0;
const endCylinder = 200;
const currentPosition = 53;

//empty tab for processes to handle
let tab = [];

let testValues = [98,183,37,122,14,124,65,67]; // values from files FCFS:640 SSTF:236 SCAN:236 C-SCAN:183
// let testValues = [1,2,3,4,5,6,7,8,9,10,11,12]; //for currentPosition: 0 all the same

//next 2 examples: SSTF=SCAN=CSCAN=n  =>  FCFS=2n-1
// let testValues = [12,11,10,9,8,7,6,5,4,3,2,1]; // FCSF bad and rest good
// let testValues = [200,11,10,9,8,7,6,5,4,3,2,1]; // Same as uppper

//processes generator
for (let i = 0; i < testValues.length; i++) {
    tab.push(new Process(1,1, testValues[i],false,0));
}

console.log(`FCFS: ${FCFS(tab, currentPosition)}`);
console.log(`SSTF: ${SSTF(tab, currentPosition)}`);
console.log(`SCAN: ${SCAN(tab, startCylinder, endCylinder, currentPosition)}`);
console.log(`C-SCAN: ${CSCAN(tab, startCylinder, endCylinder, currentPosition)}`);


tab.length = 0;
for (let i = 0; i < testValues.length; i++) {
    tab.push(new Process(i,1, testValues[i],false,0));
}
let realTimeTestValues = [50,80,130,10,2]
for (let i = 0; i < realTimeTestValues.length; i++) {
    let x = Math.random() * (endCylinder - startCylinder) + startCylinder;
    tab.push(new Process(i+1,1,realTimeTestValues[i],true, parseInt(x)+30))
}
tab.push(new Process(22,1,99,true, 52))

// tab.forEach(elem => console.log(`${elem.cylinder} ${elem.isRealTime} ${elem.deadline},`))
console.log(`--------`)
console.log(`EDF: ${EDF(tab, currentPosition)}`);
console.log(`FDSCAN: ${FDSCAN(tab, startCylinder, endCylinder, currentPosition)}`);

// queue.splice(0,1); //usuwanie pierwszego elementu kolejki