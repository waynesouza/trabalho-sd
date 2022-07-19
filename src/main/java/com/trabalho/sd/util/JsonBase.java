package com.trabalho.sd.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public interface JsonBase {

    default  <T> boolean writeJson(T p, String dir) {
        try {
            FileWriter fw = new FileWriter(dir);
            PrintWriter pw = new PrintWriter(fw);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String JSONPAC = gson.toJson(p);
            pw.println(JSONPAC);

            fw.close();
            pw.close();
            return true;

        } catch (IOException e) {
            e.getMessage();
        }
        return false;
    }

    default  <T> T readJson(String dir, Class<T> type) {

        T dao;
        FileReader fr;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            fr = new FileReader(dir);

            dao = gson.fromJson(fr, type);

            fr.close();
            return dao;

        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

}
