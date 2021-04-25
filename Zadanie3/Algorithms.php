<?php
require_once(__DIR__ . '/Page.php');

class Algorithms
{
    public $FRAME_SIZE;
    public $PageReferences = [];
    public $Frame = [];

    function __construct($FRAME_SIZE, $PageReferences)
    {
        $this->FRAME_SIZE = $FRAME_SIZE;
        $this->PageReferences = $PageReferences;
    }

    function FIFO(): int
    {
        $PF = 0; //pages failures
        $Pages1 = []; //copy

        //copying array of pages references
        foreach ($this->PageReferences as $page) {
            array_push($Pages1, new Page($page->nr, $page->parityBit, $page->ref));
        }

        //as lon as we have references
        for ($i = 0; $i < count($Pages1); $i++) {
            $n_page = $Pages1[$i];
            $if_breaker = 0;


            foreach ($this->Frame as $page) {
                if ($page->nr == $n_page->nr) {
                    $if_breaker = 1;
                    break;
                }
            }

            if (count($this->Frame) < $this->FRAME_SIZE) {
                if ($if_breaker == 0) {
                    array_push($this->Frame, $n_page);
                    $PF++;
                }
            } else {
                if ($if_breaker == 0) {
                    array_splice($this->Frame, 0, 1);
                    array_push($this->Frame, $n_page);
                    $PF++;
                }
            }

        }

        $this->Frame = array();
        return $PF;
    }

    function RANDOM(): int
    {
        $PF = 0;
        $Pages2 = [];
        foreach ($this->PageReferences as $page) {
            array_push($Pages2, new Page($page->nr, $page->parityBit, $page->ref));
        }

        $n = 0;
        for ($i = 0; $i < count($Pages2); $i++) {
            $n = $Pages2[$i];
            $if_breaker = 0;

            if (count($this->Frame) < $this->FRAME_SIZE) {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $if_breaker = 1;
                        break;
                    }
                }
                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frame, $n);
                }

            } else {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $if_breaker = 1;
                        break;
                    }
                }
                if ($if_breaker == 0) {
                    $r = rand(0, $this->FRAME_SIZE - 1);
                    $this->Frame[$r] = $n;
                    $PF++;
                }
            }
        }

        $this->Frame = array();
        return $PF;
    }

    function LRU(): int
    {
        $PF = 0;
        $Pages3 = [];

        foreach ($this->PageReferences as $page) {
            array_push($Pages3, new Page($page->nr, $page->parityBit, $page->ref));
        }

        for ($i = 0; $i < count($Pages3); $i++) {
            $n = $Pages3[$i];
            $if_breaker = 0;

            if (count($this->Frame) < $this->FRAME_SIZE) {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frame, $n);
                }
            } else {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }
                //sorting by refs
                if ($if_breaker == 0) {
                    usort($this->Frame, function ($a, $b) {
                        return $a->ref - $b->ref;
                    });
                    array_splice($this->Frame, 0, 1);
                    array_push($this->Frame, $n);
                    $PF++;
                }
            }
        }
        $this->Frame = array();
        return $PF;
    }

    function OPT(): int
    {
        $PF = 0;
        $Pages4 = [];
        foreach ($this->PageReferences as $page) {
            array_push($Pages4, new Page($page->nr, $page->parityBit, $page->ref));
        }

        for ($i = 0; $i < count($Pages4); $i++) {
            $n = $Pages4[$i];
            $if_breaker = 0;

            if (count($this->Frame) < $this->FRAME_SIZE) {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frame, $n);
                }
            } else {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $m = latest($n, $Pages4, $this->Frame);
                    if ($m != null) {
                        array_splice($this->Frame, array_search($m, $this->Frame), 1);
                    } else {
                        array_splice($this->Frame, 0, 1);
                    }

                    array_push($this->Frame, $n);
                    $PF++;
                }
            }
        }

        $this->Frame = array();
        return $PF;
    }

    function LRU_APX(): int
    {
        $PF = 0;
        $Pages5 = [];
        foreach ($this->PageReferences as $page) {
            array_push($Pages5, new Page($page->nr, $page->parityBit, $page->ref));
        }


        for ($i = 0; $i < count($Pages5); $i++) {
            $n = $Pages5[$i];
            $if_breaker = 0;

            if (count($this->Frame) < $this->FRAME_SIZE) {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setParityBit(true);
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frame, $n);
                }

            } else {
                foreach ($this->Frame as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setParityBit(true);
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }
                if ($if_breaker == 0) {

                    usort($this->Frame, function ($a, $b) {
                        return $a->ref - $b->ref;
                    });

                    $parity_breaker = 0;
                    while (true) {
                        foreach ($this->Frame as $p) {
                            if ($p->parityBit == false) {
                                array_splice($this->Frame, array_search($p, $this->Frame), 1);
                                array_push($this->Frame, $n);
                                $parity_breaker = 1;
                                break 2;
                            } else {
                                $p->setParityBit(false);
                            }
                        }

                        if ($parity_breaker == 1) {
                            break;
                        }
                    }
                    $PF++;
                }
            }
        }
        $this->Frame = array();
        return $PF;
    }

}

function latest($p, $a, $f)
{
    $temp = [];
    foreach ($f as $k) {
        array_push($temp, new Page($k->nr, $k->parityBit, $k->ref));
    }
    for ($d = array_search($p, $a); $d < count($a); ++$d) {
        for ($j = 0; $j < count($temp); $j++) {
            if ($temp[$j]->nr == $a[$d]->nr) {
                array_splice($temp, $j, 1);
            }
            if (count($temp) == 1) {
                for ($y = 0; $y < count($f); $y++) {
                    if ($temp[0]->nr == $f[$y]->nr)
                        return $f[$y];
                }
            }
        }
    }
    return null;
}