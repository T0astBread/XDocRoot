/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t0ast.xdocroot.config;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author T0astBread
 */
public class ConfigWriter
{
    public void writeConfig(Config config, String confingFilePath) throws FileNotFoundException
    {
        try(PrintWriter configWriter = new PrintWriter(confingFilePath))
        {
            configWriter.print("# APACHE-CONF ");
            configWriter.println(config.configFilePath);
            
            configWriter.println("# BOOKMARKS");
            config.bookmarks.forEach(configWriter::println);
            
            configWriter.flush();
        }
    }
}
