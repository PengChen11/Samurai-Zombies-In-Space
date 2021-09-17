package com.gameEngine;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Instruction {
    private Map<String, Map<String, List<String>>> instruct;

    /**
     * method to generate the map
     */
    public Instruction() {
        isParseable();
         }

    public Map<String, Map<String, List<String>>> getInstruction() {
        return instruct;
    }

     boolean isParseable()
    {
        boolean isSuccess = false;
        JSONParser parser = new JSONParser();
        instruct = new LinkedTreeMap<>();
        try {
            JSONObject instructionSet = (JSONObject) parser.parse(new FileReader("cfg/extendedFile.json"));
            //JSONObject instructionSet = (JSONObject) parser.parse(new FileReader("cfg/extendedParse.json"));
            instruct = new Gson().fromJson(instructionSet.toString(), LinkedTreeMap.class);
            isSuccess = true;
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     return isSuccess;
    }
}
