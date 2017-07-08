/* 
 * For license information please see LICENSE.txt
 */
package com.t0ast.xdocroot.ui;

import com.alee.laf.WebLookAndFeel;
import static com.t0ast.mu.Mu.µ;
import com.t0ast.swingutils.DialogUtils;
import com.t0ast.swingutils.ui.ExceptionHandlerUtils;
import com.t0ast.utilsstandards.UtilFrame;
import com.t0ast.xdocroot.RootChanger;
import com.t0ast.xdocroot.config.Config;
import com.t0ast.xdocroot.config.ConfigLoader;
import com.t0ast.xdocroot.config.ConfigWriter;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
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
        settings.addActionListener(evt -> showSettingsDialog(this.config));
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
                if(!Arrays.stream(Main.commandLineArgs).anyMatch(arg -> arg.contains("noconf")))
                    µ(() -> new ConfigWriter().writeConfig(config, getConfigFilePath()), ex -> ExceptionHandlerUtils.showError(XDocRootFrame.this, ex));
                dispose();
            }
        });
        
        getLafToggler().addListener(evt -> config.laf = UIManager.getLookAndFeel().getName());
    }
    
    private void showSettingsDialog(Config config)
    {
        JDialog dialog = new JDialog(this, "Settings");
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        SettingsPanel content = new SettingsPanel().setConfig(config);
        DialogUtils.setContent(dialog, content);
        dialog.getRootPane().setDefaultButton(content.getBtnSave());
        dialog.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                rootChanger.setConfigFilePath(config.configFilePath);
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
