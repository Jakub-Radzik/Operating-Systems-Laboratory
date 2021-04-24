<?php


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

    function FIFO(){
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
                    }
                    if($if_breaker==1){
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

    function RANDOM(){
        return 0;
    }

    function LRU(){
        return 0;
    }

    function OPT(){
        return 0;
    }

    function LRU_APX(){
        return 0;
    }

    function latest($page, $arrPages, $tab){
        return null;
    }
}