/* 
 * For license information please see LICENSE.txt
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
