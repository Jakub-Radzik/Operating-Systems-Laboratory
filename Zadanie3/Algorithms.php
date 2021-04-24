<?php
require_once(__DIR__.'/Page.php');

class Algorithms
{
    public $FRAME_SIZE;
    public $PagesNr;

    public $PageReferences = [];
    public $Frame = [];
    public $PF = 0;

    function __construct($FRAME_SIZE, $PageReferences) {
        $this->FRAME_SIZE = $FRAME_SIZE;
        $this->PageReferences = $PageReferences;
    }

    function FIFO(): int
    {
        $PF = 0; //pages failures
        $Pages1 = []; //copy

        //copying array of pages references
        foreach($this->PageReferences as $page)
        {
            array_push($Pages1, new Page($page->nr, $page->parityBit, $page->ref));
        }

        //as lon as we have references
        for ($i = 0; $i < count($Pages1); $i++) {
                $n_page = $Pages1[$i];
                $if_breaker = 0;


                foreach ($this->Frame as $page)
                {
                    if ($page->nr == $n_page->nr)
                    {
                        $if_breaker = 1;
                        break;
                    }
                }

                if(count($this->Frame)< $this->FRAME_SIZE) {
                    if($if_breaker==0){
                        array_push($this->Frame, $n_page);
                        $PF++;
                    }
                }
                else{
                    if($if_breaker==0){
                        array_splice($this->Frame, 0,1);
                        array_push($this->Frame, $n_page);
                        $PF++;
                    }
                }

            }

        $this->Frame = array();
        return  $PF;
    }

    function RANDOM(): int
    {
        $PF = 0;
        $Pages2 = [];
        foreach($this->PageReferences as $page)
        {
            array_push($Pages2, new Page($page->nr, $page->parityBit, $page->ref));
        }

        $n = 0;
        for($i = 0; $i < count($Pages2); $i++) {
            $n = $Pages2[$i];
            $if_breaker = 0;

            if (count($this->Frame) < $this->FRAME_SIZE) {
                foreach($this->Frame as $p)
                {
                    if($p->nr == $n->nr)
                    {
                        $if_breaker = 1;
                        break;
                    }
                }
            if($if_breaker==0){
                $PF++;
                array_push($this->Frame, $n);
            }

            }
            else
            {
                foreach($this->Frame as $p)
                {
                    if($p->nr == $n->nr)
                    {
                        $if_breaker = 1;
                        break;
                    }
                }
                if($if_breaker==0){
                    $r = rand(0,$this->FRAME_SIZE-1);
                    $this->Frame[$r] = $n;
                    $PF++;
                }
            }
        }

        $this->Frame = array();
        return  $PF;
    }

    function LRU(): int
    {
        $PF = 0;
        $Pages3 = [];

        foreach($this->PageReferences as $page)
        {
            array_push($Pages3, new Page($page->nr, $page->parityBit, $page->ref));
        }

        for($i = 0; $i < count($Pages3); $i++) {
                $n = $Pages3[$i];
                $if_breaker = 0;

                if (count($this->Frame) < $this->FRAME_SIZE)
                {
                    foreach($this->Frame as $p)
                    {
                        if ($p->nr == $n->nr)
                        {
                            $p->setRef($p->ref+1);
                            $if_breaker = 1;
                            break;
                        }
                    }

                    if($if_breaker==0)
                    {
                        $PF++;
                        array_push($this->Frame, $n);
                    }
                }
                else
                {
                    foreach($this->Frame as $p)
                    {
                        if ($p->nr == $n->nr)
                        {
                            $p->setRef($p->ref+1);
                            $if_breaker = 1;
                            break;
                        }
                    }
                    //sorting by refs
                    if($if_breaker==0)
                    {
                        usort($this->Frame, function ($a,$b){return $a->ref - $b->ref;});
                        array_splice($this->Frame, 0,1);
                        array_push($this->Frame, $n);
                        $PF++;
                    }
                }
            }
        $this->Frame = array();
        return  $PF;
    }

    function OPT(): int
    {
        return 0;
    }

    function LRU_APX(): int
    {
        return 0;
    }

    function latest($page, $arrPages, $tab){
        return null;
    }
}