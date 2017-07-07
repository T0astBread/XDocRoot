/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t0ast.xdocroot.config;

import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;

/**
 *
 * @author T0astBread
 */
public class Config
{
    public String configFilePath;
    public List<String> bookmarks;
    public String laf;

    public Config()
    {
        this.configFilePath = "C:/xampp/apache/conf/httpd.conf";
        this.bookmarks = new ArrayList<>();
        this.laf = UIManager.getLookAndFeel().getName();
    }
}
