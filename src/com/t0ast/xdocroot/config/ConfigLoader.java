/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t0ast.xdocroot.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author T0astBread
 */
public class ConfigLoader
{

    public void loadIntoConfig(Config config, String configPath) throws IOException
    {
        try(BufferedReader configReader = new BufferedReader(new FileReader(configPath)))
        {
            String line;
            while((line = configReader.readLine()) != null)
            {
                if(line.isEmpty())
                {
                    continue;
                }
                processLine(line, config, configReader);
            }
        }
    }

    private void readBookmarks(BufferedReader reader, Config config) throws IOException
    {
        String line;
        while((line = reader.readLine()) != null)
        {
            if(line.isEmpty())
            {
                return;
            }
            if(line.startsWith("# "))
            {
                processLine(line, config, reader);
                return;
            }
            else config.bookmarks.add(line);
        }
    }

    private void processLine(String line, Config config, BufferedReader configReader) throws IOException
    {
        if(line.startsWith("# APACHE-CONF"))
        {
            config.configFilePath = line.split(" ")[2];
        }
        else if(line.startsWith("# BOOKMARKS"))
        {
            readBookmarks(configReader, config);
        }
    }
}
