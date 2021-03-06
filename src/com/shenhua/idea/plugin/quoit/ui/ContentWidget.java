package com.shenhua.idea.plugin.quoit.ui;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.fileChooser.FileSaverDescriptor;
import com.intellij.openapi.fileChooser.FileSaverDialog;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.util.ui.JBUI;
import com.shenhua.idea.plugin.quoit.callback.OnExecuteListener;
import com.shenhua.idea.plugin.quoit.core.ApiImpl;
import com.shenhua.idea.plugin.quoit.core.tasks.CodeGenerateTask;
import com.shenhua.idea.plugin.quoit.core.tasks.SavingImageTask;
import com.shenhua.idea.plugin.quoit.ext.Utils;
import org.apache.http.util.TextUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

/**
 * Created by shenhua on 2018-02-01-0001.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class ContentWidget extends JPanel implements ActionListener {

    private Project mProject;
    private Disposable mDisposable;
    private JPanel mContent;
    private JTextArea textArea;
    private JComboBox comboBox1;
    private JLabel mQRlabel;
    private JLabel labelInfo;
    private JPanel modulePanle;
    private JBRadioButton radioDefault;
    private JBRadioButton radioColorful;
    private JBRadioButton radioRound;
    private int currentMode = 0;

    public ContentWidget(Project mProject, Disposable mDisposable) {
        super(new BorderLayout());
        this.mProject = mProject;
        this.mDisposable = mDisposable;
        this.add(mContent, BorderLayout.CENTER);

        setupTextArea();
        setupImage();
        setupImageTools();
    }

    private void setupImageTools() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioDefault);
        buttonGroup.add(radioColorful);
        buttonGroup.add(radioRound);
        radioDefault.addActionListener(e -> changeMode(0));
        radioColorful.addActionListener(e -> changeMode(1));
        radioRound.addActionListener(e -> changeMode(2));
    }

    /**
     * 更换模型
     */
    private void changeMode(int mode) {
        if (mode != currentMode) {
            if (TextUtils.isEmpty(getText())) {
                return;
            }
            currentMode = mode;
            new CodeGenerateTask(mProject, getText(), getMode(), new OnExecuteListener() {
                @Override
                public void onStart() {
                    setInfo("Starting...");
                }

                @Override
                public void onSuccess(Icon icon) {
                    setQRcode(icon);
                    setInfo("Success...");
                }

                @Override
                public void onError(String msg) {
                    setInfo("Error...");
                }

                @Override
                public void onComplete() {
                }
            }).start();
        }
    }

    private void setupImage() {
        mQRlabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                    JPopupMenu jPopupMenu = new JPopupMenu();
                    JMenuItem saveLine = new JMenuItem("Save", AllIcons.Actions.Menu_saveall);
                    saveLine.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_MASK, false));
                    saveLine.addActionListener(e1 -> {
                        saveQRcode();

                    });
                    jPopupMenu.add(saveLine);
                    if (mQRlabel.getIcon() == null) {
                        saveLine.setEnabled(false);
                    }
                    jPopupMenu.show(mQRlabel, e.getX(), e.getY());
                }
            }
        });
    }

    private void saveQRcode() {
        final Icon icon = getQRcode();
        if (mProject == null || icon == null) {
            return;
        }
        FileSaverDialog dialog = FileChooserFactory.getInstance()
                .createSaveFileDialog(new FileSaverDescriptor("Save the QR Code",
                        "Select file where to save the QR code.", "png"), this);
        VirtualFileWrapper wrapper = dialog.save(mProject.getBaseDir(), Utils.getSuggestFileName());
        if (wrapper == null) {
            return;
        }
        new SavingImageTask(mProject, icon, wrapper).start();
    }

    private void setupTextArea() {
        textArea.setMargin(JBUI.insets(2));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Utils.setRightMenu(textArea, createPopupMenu());
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu jPopupMenu = new JPopupMenu();
        // copy
        JMenuItem copyLine = new JMenuItem("Copy", AllIcons.Actions.Copy);
        copyLine.setMnemonic('C');
        copyLine.setAccelerator(KeyStroke.getKeyStroke('C', KeyEvent.CTRL_MASK, false));
        // paste
        JMenuItem pasteLine = new JMenuItem("Paste", AllIcons.Actions.Menu_paste);
        pasteLine.setMnemonic('P');
        pasteLine.setAccelerator(KeyStroke.getKeyStroke('V', KeyEvent.CTRL_MASK, false));
        // delete
        JMenuItem deleteLine = new JMenuItem("Delete", AllIcons.Actions.Delete);
        deleteLine.setMnemonic('D');
        deleteLine.setAccelerator(KeyStroke.getKeyStroke('D', KeyEvent.CTRL_MASK, false));
        // listener
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        copyLine.addActionListener(e -> {
            String str = textArea.getSelectedText();
            if (!TextUtils.isEmpty(str)) {
                clipboard.setContents(new StringSelection(str), null);
            } else {
                if (!TextUtils.isEmpty(getText())) {
                    clipboard.setContents(new StringSelection(getText()), null);
                }
            }
        });
        pasteLine.addActionListener(e -> {
            final Transferable transferable = clipboard.getContents(this);
            final DataFlavor dataFlavor = DataFlavor.stringFlavor;
            if (transferable.isDataFlavorSupported(dataFlavor)) {
                try {
                    String str = (String) transferable.getTransferData(dataFlavor);
                    if (!TextUtils.isEmpty(str)) {
                        textArea.insert(str, textArea.getCaretPosition());
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        deleteLine.addActionListener(e -> {
            String str = textArea.getSelectedText();
            if (!TextUtils.isEmpty(str)) {
                textArea.replaceSelection("");
            } else {
                setText("");
            }
        });
        // add
        jPopupMenu.add(copyLine);
        jPopupMenu.add(pasteLine);
        jPopupMenu.addSeparator();
        jPopupMenu.add(deleteLine);

        return jPopupMenu;
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void setQRcode(Icon icon) {
        mQRlabel.setIcon(icon);
    }

    public Icon getQRcode() {
        return mQRlabel.getIcon();
    }

    public void setInfo(String text) {
        labelInfo.setText(text);
    }

    public String getMode() {
        String result = "";
        switch (currentMode) {
            case 0:
                result = ApiImpl.MODE_0;
                break;
            case 1:
                result = ApiImpl.MODE_1;
                break;
            case 2:
                result = ApiImpl.MODE_2;
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
