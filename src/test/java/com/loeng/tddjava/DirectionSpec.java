package com.loeng.tddjava;



import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

@Test
public class DirectionSpec {

    public void whenGetFromShortNameNThenReturnDirectionN(){

        Direction direction = Direction.getFromShortName('N');
        assertEquals(direction,Direction.NORTH);

    }

}

