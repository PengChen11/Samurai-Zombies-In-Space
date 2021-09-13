package com.engine;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class InstructionTest {
    @Test
    public void testIsParseable()
    {
        Instruction instruct=new Instruction();
        assertTrue(instruct.isParseable());
    }


}