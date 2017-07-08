/* 
 * For license information please see LICENSE.txt
 */
package com.t0ast.xdocroot.ui;

/**
 *
 * @author T0astBread
 */
public class Main
{
    public static String[] commandLineArgs;
    
    public static void main(String[] args)
    {
        commandLineArgs = args;
        XDocRootFrame frame = new XDocRootFrame();
        frame.setVisible(true);
    }
}
