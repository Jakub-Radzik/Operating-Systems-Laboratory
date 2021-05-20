<?php

class Page
{
    public $nr; //int
    public $secondChanceBit; //bool
    public $ref; //int

    //casual constructor
    function __construct($nr, $secondChanceBit, $ref)
    {
        $this->nr = $nr;
        $this->secondChanceBit = $secondChanceBit;
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
    public function getSecondChanceBit()
    {
        return $this->secondChanceBit;
    }

    /**
     * @param mixed $secondChanceBit
     */
    public function setSecondChanceBit($secondChanceBit)
    {
        $this->secondChanceBit = $secondChanceBit;
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
        return $this->nr . " ";
    }


}



