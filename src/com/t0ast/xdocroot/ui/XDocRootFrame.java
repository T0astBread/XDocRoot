/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.t0ast.xdocroot.ui;

import static com.t0ast.mu.Mu.µ;
import com.t0ast.swingutils.ui.ExceptionHandlerUtils;
import com.t0ast.utilsstandards.UtilFrame;
import com.t0ast.xdocroot.RootChanger;
import com.t0ast.xdocroot.config.Config;
import com.t0ast.xdocroot.config.ConfigLoader;
import com.t0ast.xdocroot.config.ConfigWriter;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author T0astBread
 */
public class XDocRootFrame extends UtilFrame
{
    private RootChanger rootChanger;
    private Config config;
    
    public XDocRootFrame()
    {
        this.rootChanger = new RootChanger();
        this.config = new Config();
        try
        {
            new ConfigLoader().loadIntoConfig(this.config, getConfigFilePath());
        }
        catch(IOException ex)
        {
            Logger.getLogger(XDocRootFrame.class.getName()).log(Level.WARNING, null, ex);
        }
        this.rootChanger.setConfigFilePath(this.config.configFilePath);
        
        initUI(this.config);
    }
    
    private String getConfigFilePath()
    {
        return System.getProperty("user.dir") + "/XDocRoot_Config.txt";
    }
    
    private void initUI(Config config)
    {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setHeaderTitle("XDocRoot");
        setHeaderInsets(6, 6, 0, 6);
        
        try
        {
            getLafToggler().scrollLafs(laf -> this.config.laf.equals(laf.getName()));
        }
        catch(UnsupportedLookAndFeelException ex)
        {
            System.out.println(ex);
        }
        
        try
        {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("icon.png")));
        }
        catch(IOException ex)
        {
            System.out.println(ex);
        }
        
        final ContentPanel content = new ContentPanel().setConfig(config).setRootChanger(this.rootChanger);
        content.addBookmarks(config.bookmarks);
        setContent(content);
        
        JButton settings = new JButton();
        settings.add(new SettingsButton());
        settings.addActionListener(evt -> displaySettingsDialog(this.config));
        addToHeaderBar(settings, 0);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width/2 - getWidth()/2, screen.height/2 - getHeight()/2);
        
        getRootPane().setDefaultButton(content.getBtnSet());
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(!content.askForEnd()) return;
                µ(() -> new ConfigWriter().writeConfig(config, getConfigFilePath()), ex -> ExceptionHandlerUtils.showError(XDocRootFrame.this, ex));
                dispose();
            }
        });
        
        getLafToggler().addListener(evt -> config.laf = UIManager.getLookAndFeel().getName());
    }
    
    private void displaySettingsDialog(Config config)
    {
        JDialog dialog = new JDialog(this, "Settings");
        SettingsPanel content = new SettingsPanel().setConfig(config);
        dialog.setContentPane(content);
        dialog.setPreferredSize(new Dimension(content.getPreferredSize().width, (int) (content.getPreferredSize().height * 1.2f)));
        dialog.getRootPane().setDefaultButton(content.getBtnSave());
        dialog.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                rootChanger.setConfigFilePath(config.configFilePath);
            }
        });
        dialog.setLocation((getX() + getWidth())/2 - dialog.getWidth()/2, (int) ((getY() + getHeight())*.4f - dialog.getHeight()/2));
        dialog.pack();
        dialog.setVisible(true);
    }
}
