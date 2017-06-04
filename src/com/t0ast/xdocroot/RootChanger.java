/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t0ast.xdocroot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 *
 * @author T0astBread
 */
public class RootChanger
{
    private String configFilePath, documentRoot;

    public String getConfigFilePath()
    {
        return configFilePath;
    }

    /**
     * <a href="http://t0astbread.github.io">kill me</a>
     * @param configFilePath The path of the Apache config file (<code>httpd.conf</code>)
     */
    public void setConfigFilePath(String configFilePath)
    {
        this.configFilePath = configFilePath;
    }

    /**
     * Returns the last set or read value of the document root. Notice that, in
     * order to read the current document root, you have to call
     * {@link #readDocumentRoot()} first.
     *
     * @return The current value of the document root
     * @see #readDocumentRoot()
     */
    public String getDocumentRoot()
    {
        return documentRoot;
    }

    /**
     * Reads the current document root from the XAMPP Apache config file. The
     * document root can then be accessed using {@link #getDocumentRoot() }.
     *
     * @throws IOException
     * @see #getDocumentRoot()
     */
    public void readDocumentRoot() throws IOException
    {
        this.documentRoot = extractDocRootFromConfigFile();
    }

    private String extractDocRootFromConfigFile() throws IOException
    {
        final String docRoot = "";
        processConfigFileLinesAndDocRoot(l ->
        {
        }, docRoot::concat);
        return docRoot;
    }

    /**
     * Sets the variable for the document root <b>in this application</b> (not
     * the actual config file in XAMPP).
     *
     * <p>
     * Usage:
     * <ol>
     * <li>Set the new document root using
     * {@link #setDocumentRoot(java.lang.String)}</li>
     * <li>Write the changes to the config file using
     * {@link #writeDocumentRoot()}
     * </ol>
     * </p>
     *
     * @param documentRoot
     * @see #writeDocumentRoot()
     */
    public void setDocumentRoot(String documentRoot)
    {
        this.documentRoot = documentRoot;
    }

    /**
     * Writes changes in the document root variable to the XAMPP Apache config
     * file.
     *
     * <p>
     * Usage:
     * <ol>
     * <li>Set the new document root using
     * {@link #setDocumentRoot(java.lang.String)}</li>
     * <li>Write the changes to the config file using
     * {@link #writeDocumentRoot()}
     * </ol>
     * </p>
     *
     * @throws IOException
     * @see #setDocumentRoot(java.lang.String)
     */
    public void writeDocumentRoot() throws IOException
    {
        final StringBuilder configFileContent = new StringBuilder();
        processAllConfigFileLinesAndModifyDocRoot(line -> configFileContent.append(line).append("\n"), this::replaceDocRootInLine);
        try(PrintWriter configFilePrinter = new PrintWriter(this.configFilePath))
        {
            configFilePrinter.println(configFileContent.toString());
            configFilePrinter.flush();
        }
    }

    private String replaceDocRootInLine(String docRootLine)
    {
        String[] parts = docRootLine.split("\"");
        parts[1] = this.documentRoot;
        return String.join("\"", parts) + "\"";
    }

    /**
     *
     * @param anyLineConsumer Recieves any line except those containing the
     * document root
     * @param docRootLineConsumer Recieves the lines containing the document
     * root
     */
    private void processConfigFileLinesAndDocRoot(Consumer<String> anyLineConsumer, Consumer<String> docRootLineConsumer) throws IOException
    {
        try(BufferedReader configFileReader = new BufferedReader(new FileReader(this.configFilePath)))
        {
            configFileReader.lines().forEach(line ->
            (line.startsWith("DocumentRoot \"") || line.startsWith("<Directory \"") ? docRootLineConsumer : anyLineConsumer).accept(line));
        }
    }

    private void processAllConfigFileLinesAndModifyDocRoot(Consumer<String> anyLineConsumer, UnaryOperator<String> docRootModifier) throws IOException
    {
        processConfigFileLinesAndDocRoot(anyLineConsumer, line -> anyLineConsumer.accept(docRootModifier.apply(line)));
    }
}
