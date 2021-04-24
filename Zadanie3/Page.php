<?php

class Page
{
    public $nr; //int
    public $parityBit; //bool
    public $ref; //int

    //casual constructor
    function __construct($nr, $parityBit, $ref) {
        $this->nr = $nr;
        $this->parityBit = $parityBit;
        $this->ref = $ref;
    }

    /**
     * @return mixed
     */
    public function getNr()
    {
        return $this->nr;
    }

    /**
     * @param mixed $nr
     */
    public function setNr($nr)
    {
        $this->nr = $nr;
    }

    /**
     * @return mixed
     */
    public function getParityBit()
    {
        return $this->parityBit;
    }

    /**
     * @param mixed $parityBit
     */
    public function setParityBit($parityBit)
    {
        $this->parityBit = $parityBit;
    }

    /**
     * @return mixed
     */
    public function getRef()
    {
        return $this->ref;
    }

    /**
     * @param mixed $ref
     */
    public function setRef($ref)
    {
        $this->ref = $ref;
    }

    public function __toString()
    {
        return $this->nr." ";
    }


}

function compareRefs($page1, $page2){
    if($page1->ref > $page2->ref){
        return 1;
    }elseif ($page1->ref < $page2->ref){
        return -1;
    }
    return 0;
}

