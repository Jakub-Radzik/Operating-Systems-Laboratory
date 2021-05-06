<?php
require_once(__DIR__ . '/Page.php');

class Algorithms
{
    public $FRAME_SIZE;
    public $PageReferences = [];
    public $Frames = [];

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
            array_push($Pages1, new Page($page->nr, $page->secondChanceBit, $page->ref));
        }

        //as lon as we have references
        for ($i = 0; $i < count($Pages1); $i++) {
            $n_page = $Pages1[$i];
            $if_breaker = 0;


            foreach ($this->Frames as $page) {
                if ($page->nr == $n_page->nr) {
                    $if_breaker = 1;
                    break;
                }
            }

            //FIFO OPERATIONS

            //IF WE HAVE TO PUSH ELEM TO NON FULL QUEUE
            if (count($this->Frames) < $this->FRAME_SIZE) {
                if ($if_breaker == 0) {
                    array_push($this->Frames, $n_page);
                    $PF++;
                }
            } else {
                //ELSE IF WE HAVE FULL QUEUE, DELETE FIRST PUSH LAST
                if ($if_breaker == 0) {
                    array_splice($this->Frames, 0, 1);
                    array_push($this->Frames, $n_page);
                    $PF++;
                }
            }

        }

        $this->Frames = array();
        return $PF;
    }

    function RANDOM(): int
    {
        $PF = 0;
        $Pages2 = [];
        //copying array of pages references
        foreach ($this->PageReferences as $page) {
            array_push($Pages2, new Page($page->nr, $page->secondChanceBit, $page->ref));
        }

        $n = 0;
        //For each reference to page
        for ($i = 0; $i < count($Pages2); $i++) {
            $n = $Pages2[$i];
            $if_breaker = 0;

            //looking for our page in frames
            foreach ($this->Frames as $p) {
                if ($p->nr == $n->nr) { //if page is in frames
                    $if_breaker = 1;    //we can skip iteration
                    break;
                }
            }

            if (count($this->Frames) < $this->FRAME_SIZE) {
                //IF QUEUE IS NOT FULL -> JUST PUSH
                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frames, $n);
                }
            } else {
                //ELSE RANDOMIZE PLACE TO PUSH
                if ($if_breaker == 0) {
                    $r = rand(0, $this->FRAME_SIZE - 1);
                    $this->Frames[$r] = $n;
                    $PF++;
                }
            }
        }

        $this->Frames = array();
        return $PF;
    }

    function LRU(): int
    {
        $PF = 0;
        $Pages3 = [];

        //copying array of pages references
        foreach ($this->PageReferences as $page) {
            array_push($Pages3, new Page($page->nr, $page->secondChanceBit, $page->ref));
        }

        //For each reference to page
        for ($i = 0; $i < count($Pages3); $i++) {
            $n = $Pages3[$i];
            $if_breaker = 0;

            //if there is empty frames
            if (count($this->Frames) < $this->FRAME_SIZE) {
                foreach ($this->Frames as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setRef($p->ref + 1);    //if we need page we set page ref = ref + 1
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frames, $n);
                }
            } else {
                foreach ($this->Frames as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }

                //sorting by refs
                if ($if_breaker == 0) {
                    usort($this->Frames, function ($a, $b) {
                        return $a->ref - $b->ref;
                    });
                    //like in FIFO
                    array_splice($this->Frames, 0, 1);
                    array_push($this->Frames, $n);
                    $PF++;
                }
            }
        }
        $this->Frames = array();
        return $PF;
    }

    function OPT(): int
    {
        $PF = 0;
        $Pages4 = [];
        foreach ($this->PageReferences as $page) {
            array_push($Pages4, new Page($page->nr, $page->secondChanceBit, $page->ref));
        }

        for ($i = 0; $i < count($Pages4); $i++) {
            $n = $Pages4[$i];
            $if_breaker = 0;

            if (count($this->Frames) < $this->FRAME_SIZE) {
                //non full frames, just push
                foreach ($this->Frames as $p) {
                    if ($p->nr == $n->nr) {
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frames, $n);
                }
            } else {
                foreach ($this->Frames as $p) {
                    if ($p->nr == $n->nr) {
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    //find the page we dont use for a long time
                    $m = latest($n, $Pages4, $this->Frames);
                    if ($m != null) {
                        array_splice($this->Frames, array_search($m, $this->Frames), 1);
                    } else {
                        array_splice($this->Frames, 0, 1);
                    }

                    array_push($this->Frames, $n);
                    $PF++;
                }
            }
        }

        $this->Frames = array();
        return $PF;
    }

    function LRU_APX(): int
    {
        $PF = 0;
        $Pages5 = [];
        foreach ($this->PageReferences as $page) {
            array_push($Pages5, new Page($page->nr, $page->secondChanceBit, $page->ref));
        }


        for ($i = 0; $i < count($Pages5); $i++) {
            $n = $Pages5[$i];
            $if_breaker = 0;

            if (count($this->Frames) < $this->FRAME_SIZE) {
                //NON full queue, put elem, set ref=ref+1 and parity
                foreach ($this->Frames as $p) {
                    if ($p->nr == $n->nr) {
                        $p->setSecondChanceBit(true);
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }

                if ($if_breaker == 0) {
                    $PF++;
                    array_push($this->Frames, $n);
                }

            } else { //full queue
                //pages in frames
                foreach ($this->Frames as $p) {
                    //we found our page in frames
                    if ($p->nr == $n->nr) {
                        $p->setSecondChanceBit(true);
                        $p->setRef($p->ref + 1);
                        $if_breaker = 1;
                        break;
                    }
                }
                //lets start rollercoaster
                //our page is not in frames
                if ($if_breaker == 0) {
                    //fifo - sort by refs
                    usort($this->Frames, function ($a, $b) {
                        return $a->ref - $b->ref;
                    });

                    $second_chance_end = 0;
                    while (true) {
                        //around pages in frames
                        foreach ($this->Frames as $p) {
                            //check bit
                            if ($p->secondChanceBit == false) {
                                array_splice($this->Frames, array_search($p, $this->Frames), 1);
                                array_push($this->Frames, $n);
                                $second_chance_end = 1;
                                break 2;
                            } else {
                                $p->setsecondChanceBit(false);
                            }
                        }

                        if ($second_chance_end == 1) {
                            break;
                        }
                    }
                    $PF++;
                }
            }
        }
        $this->Frames = array();
        return $PF;
    }

}

function latest($page, $pages, $framesWithPages)
{
    $temp = [];
    //copy of frames filled by sites
    foreach ($framesWithPages as $k) {
        array_push($temp, new Page($k->nr, $k->secondChanceBit, $k->ref));
    }
    //from index of page in pages (pages are numbers of pages we refer to)
    //series of references, that's why OPT is impossible to implement in real life
    //we do not know the future
    for ($d = array_search($page, $pages); $d < count($pages); ++$d) {

        //for every frame
        for ($j = 0; $j < count($temp); $j++) {

            //if number of site in frame is equal to number of page we refer to
            if ($temp[$j]->nr == $pages[$d]->nr) {

                //delete this frame
                array_splice($temp, $j, 1);
            }
            //if we have only one frame
            if (count($temp) == 1) {
                //for every frame with pages
                for ($y = 0; $y < count($framesWithPages); $y++) {

                    //we found site that we do not use for a long time
                    if ($temp[0]->nr == $framesWithPages[$y]->nr)
                        return $framesWithPages[$y];
                }
            }
        }
    }
    return null;
}