<?php
    require_once(__DIR__.'/Result.php');

    $frames = [1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5];
    $PageReferencesCount = 12;
    $Interval = 5;
//    $SizesOfFrames = 5;

//    for($i = 0; $i < $SizesOfFrames; $i++)
//    {
//        $b = $i + 1;
//        $p  = rand(1,10);
//        array_push($frames, $p);
//    }

    $result = new Result($PageReferencesCount, $Interval, $frames);

?>