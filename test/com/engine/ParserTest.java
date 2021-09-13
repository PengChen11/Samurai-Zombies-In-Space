package com.engine;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testParseInput() {
        //should return an array of length 2 no matter the input
         assertEquals(2,Parser.parseInput("talk to the bartender").length);
        //passing uppercase
        assertArrayEquals(new String[]{"talk","visitor"},Parser.parseInput("TALK TO THE VISITOR"));

    }
}