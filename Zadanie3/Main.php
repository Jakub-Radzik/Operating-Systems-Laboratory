<?php
require_once(__DIR__ . '/Page.php');
require_once(__DIR__ . '/Algorithms.php');
/**
 * @param $frames - it is an Array that defines number of available frames in following simulations
 *
 * @param $PageReferencesCount - number of attempts to refer to pages
 *
 * @param $Interval - number of last page, we will draw a number of page from 1 to $Interval in all attempts
 *
 * @author Jakub Radzik
 */

//$frames = [5, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5];
$frames = [100,110,120,130,140,150,160];
$PageReferencesCount = 5000;
$Interval = 200;


//Creating page references
$PageReferences = [];

//    $refs = [1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5];//TESTING VALUES

for ($a = 0; $a < $PageReferencesCount; $a++) {
    $r = intval(rand(1, $Interval));
    //        $r = $refs[$a];
    $newPage = new Page($r, true, 0);
    array_push($PageReferences, $newPage);
}


for ($j = 0; $j < count($frames); $j++) {
    $algorithms = new Algorithms(intval($frames[$j]), $PageReferences);
    echo "\nResults with frame size: " . intval($frames[$j]);
    echo "\nFIFO: " . $algorithms->FIFO() . " RANDOM: " . $algorithms->RANDOM() . " LRU: " . $algorithms->LRU() . " OPT: " . $algorithms->OPT() . " LRU_APX: " . $algorithms->LRU_APX();
    echo "\n-----------------------------------------------------------";
}

?>