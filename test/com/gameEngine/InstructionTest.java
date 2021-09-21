package com.gameEngine;

import org.junit.Test;

import static org.junit.Assert.*;

public class InstructionTest {
    @Test
    public void testIsParseable()
    {
        Instruction instruct=new Instruction();
        assertTrue(instruct.isParseable());
    }

}