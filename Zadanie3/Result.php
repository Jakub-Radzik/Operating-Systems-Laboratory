<?php
require_once(__DIR__.'/Page.php');
require_once(__DIR__.'/Algorithms.php');
class Result
{
    function __construct($PageReferencesCount, $interval, $frames )
    {
        //Creating page references
        $PageReferences = [];
        $refs = [1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5];//TESTING VALUES
        for($a = 0; $a< $PageReferencesCount; $a++)
        {
//            $r = intval(rand(1,$interval));
            $r = $refs[$a];
            $newPage = new Page($r, true, 0);
            array_push($PageReferences, $newPage);
        }
        //=====================================================================================



        for($j = 0; $j< count($frames); $j++)
        {
            $algorithms = new Algorithms(intval($frames[$j]) ,$PageReferences);
            echo "\nResults with frame size: ". intval($frames[$j]);
            echo "\nFIFO: " . $algorithms->FIFO() . " RANDOM: " . $algorithms->RANDOM() . " LRU: " . $algorithms->LRU() . " OPT: " . $algorithms->OPT() . " LRU_APX: " . $algorithms->LRU_APX();
            echo "\n-----------------------------------------------------------";
        }

    }
}