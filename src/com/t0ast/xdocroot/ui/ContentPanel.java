/* 
 * For license information please see LICENSE.txt
 */
package com.t0ast.xdocroot.ui;

import static com.t0ast.mu.Mu.µ;
import com.t0ast.swingutils.PlaceholderListener;
import com.t0ast.swingutils.ui.ExceptionHandlerUtils;
import com.t0ast.xdocroot.RootChanger;
import com.t0ast.xdocroot.config.Config;
import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author T0astBread
 */
public class ContentPanel extends javax.swing.JPanel
{
    private RootChanger rootChanger;
    private Config config;
    private DefaultListModel<String> bookmarksModel;
    private boolean ignoreBookmarkChanges;
    private PlaceholderListener txtDocRootPlaceholder;

    /**
     * Creates new form Content
     */
    public ContentPanel()
    {
        this.bookmarksModel = new DefaultListModel<>();

        initComponents();

        this.txtDocRootPlaceholder = new PlaceholderListener(this.txtDocumentRoot, "Document Root");
        
        this.btnSet.addActionListener(evt -> µ(() ->
        {
            this.rootChanger.writeDocumentRoot();
            JOptionPane.showMessageDialog(this, "Document Root set to: " + rootChanger.getDocumentRoot(), "XDocRoot", JOptionPane.INFORMATION_MESSAGE);
        }, ex -> ExceptionHandlerUtils.showError(this, ex)));
        this.btnAddToBookmarks.addActionListener(evt -> this.bookmarksModel.addElement(this.txtDocumentRoot.getText()));
        this.btnRemoveFromBookmarks.addActionListener(evt ->
        {
            if(this.lstBookmarks.getSelectedIndex() != -1) this.bookmarksModel.remove(this.lstBookmarks.getSelectedIndex());
        });
        this.bookmarksModel.addListDataListener(new ListDataListener()
        {
            @Override
            public void intervalAdded(ListDataEvent e)
            {
                if(ignoreBookmarkChanges) return;
                IntStream.rangeClosed(e.getIndex0(), e.getIndex1()).forEach(i -> config.bookmarks.add(bookmarksModel.get(i)));
            }

            @Override
            public void intervalRemoved(ListDataEvent e)
            {
                if(ignoreBookmarkChanges) return;
                IntStream.rangeClosed(e.getIndex0(), e.getIndex1()).forEach(i -> config.bookmarks.remove(i));
            }

            @Override
            public void contentsChanged(ListDataEvent e)
            {
                if(ignoreBookmarkChanges) return;
                config.bookmarks.clear();
                Arrays.stream(bookmarksModel.toArray()).map(Object::toString).forEach(config.bookmarks::add);
            }
        });
        this.lstBookmarks.getSelectionModel().addListSelectionListener(evt ->
        {
            if(this.lstBookmarks.getSelectedIndex() != -1) loadDocRootPath(this.bookmarksModel.get(this.lstBookmarks.getSelectedIndex()));
        });
        this.jFileChooser1.addActionListener(evt ->
        {
            if(JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand()) && askForEnd()) SwingUtilities.getWindowAncestor(this).dispose();
            else if(JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) loadDocRootPath(this.jFileChooser1.getSelectedFile().getAbsolutePath());
        });
        this.txtDocumentRoot.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                updateRootChanger();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                updateRootChanger();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                updateRootChanger();
            }
        });
    }
    
    private void updateRootChanger()
    {
        this.rootChanger.setDocumentRoot(this.txtDocumentRoot.getText());
    }
    
    private void updateDocRoot()
    {
        µ(() -> this.rootChanger.readDocumentRoot(), ex -> ExceptionHandlerUtils.showError(this, ex));
        this.txtDocRootPlaceholder.setText(this.rootChanger.getDocumentRoot());
    }
    
    private void loadDocRootPath(String path)
    {
        this.txtDocumentRoot.setForeground(UIManager.getColor("Textfield.foreground"));
        this.txtDocRootPlaceholder.setText(path);
        this.jFileChooser1.setCurrentDirectory(new File(path));
    }

    public ContentPanel setRootChanger(RootChanger rootChanger)
    {
        this.rootChanger = rootChanger;
        updateDocRoot();
        return this;
    }

    public ContentPanel setConfig(Config config)
    {
        this.config = config;
        return this;
    }

    public JButton getBtnSet()
    {
        return btnSet;
    }
    
    public void addBookmarks(Iterable<String> bookmarks)
    {
        this.ignoreBookmarkChanges = true;
        bookmarks.forEach(this.bookmarksModel::addElement);
        this.ignoreBookmarkChanges = false;
    }
    
    public boolean askForEnd()
    {
        return JOptionPane.showConfirmDialog(this, "Exit XDocRoot?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        txtDocumentRoot = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnAddToBookmarks = new javax.swing.JButton();
        btnRemoveFromBookmarks = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstBookmarks = new com.t0ast.swingutils.ui.ListWithPlaceholder<>();
        btnSet = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(294, 382));

        txtDocumentRoot.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("Select a Document Root...");

        jSplitPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setOneTouchExpandable(true);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel3.setText("...directly from your Hard Drive:");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 0);
        jPanel3.add(jLabel3, gridBagConstraints);

        jFileChooser1.setCurrentDirectory(new File("C:/xampp/htdocs"));
        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        jFileChooser1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        jFileChooser1.setMinimumSize(new java.awt.Dimension(200, 235));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jFileChooser1, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel3);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel2.setText("...from your Bookmarks:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 10);
        jPanel2.add(jLabel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        btnAddToBookmarks.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddToBookmarks.setText("Add Current");
        jPanel4.add(btnAddToBookmarks);

        btnRemoveFromBookmarks.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRemoveFromBookmarks.setText("Remove");
        jPanel4.add(btnRemoveFromBookmarks);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jPanel4, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("OR");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 8, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lstBookmarks.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lstBookmarks.setModel(this.bookmarksModel);
        lstBookmarks.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        jScrollPane2.setViewportView(lstBookmarks);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane2, gridBagConstraints);

        jSplitPane1.setLeftComponent(jPanel2);

        btnSet.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnSet.setText("SET");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDocumentRoot)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSet))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDocumentRoot))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToBookmarks;
    private javax.swing.JButton btnRemoveFromBookmarks;
    private javax.swing.JButton btnSet;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private com.t0ast.swingutils.ui.ListWithPlaceholder<String> lstBookmarks;
    private javax.swing.JTextField txtDocumentRoot;
    // End of variables declaration//GEN-END:variables
}
