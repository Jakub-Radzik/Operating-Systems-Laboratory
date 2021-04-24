<?php
$k = [5,4,3,3,2,2,2,2,2,2,9999];
usort($k, function ($a,$b){return $a - $b;});
var_dump($k);