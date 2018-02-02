package com.shenhua.idea.plugin.quoit.ext;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.util.ui.UIUtil;
import com.shenhua.idea.plugin.quoit.tabs.ITabs;
import com.shenhua.idea.plugin.quoit.tabs.QuoitContent;
import com.shenhua.idea.plugin.quoit.ui.ContentWidget;
import org.apache.http.util.TextUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

/**
 * Created by shenhua on 2018-02-02-0002.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */
public class Utils {

    public static void setRightMenu(JComponent jComponent, JPopupMenu menu) {
        jComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMouse(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouse(e);
            }

            private void handleMouse(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public static ContentWidget getCurrentContent(QuoitContent quoitContent) {
        ITabs tabs = quoitContent.getTabs();
        ContentWidget contentWidget;
        if (tabs == null) {
            contentWidget = (ContentWidget) quoitContent.getComponent();
        } else {
            contentWidget = (ContentWidget) tabs.getCurrentComponent();
        }
        return contentWidget;
    }

    public static boolean saveIcon(Icon icon, File file) {
        try {
            BufferedImage bufferedImage = getBufferedImage((ImageIcon) icon);
            ImageIO.write(bufferedImage, "png", file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void warning(String title, String msg) {
        if (TextUtils.isEmpty(title)) {
            title = Constant.TOOL_WINDOW_ID;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Notifications.Bus.notify(new Notification("1", title, msg, NotificationType.WARNING));
    }

    public static void message(String title, String msg) {
        if (TextUtils.isEmpty(title)) {
            title = Constant.TOOL_WINDOW_ID;
        }
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Notifications.Bus.notify(new Notification("1", title, msg, NotificationType.INFORMATION));
    }

    private static BufferedImage getBufferedImage(ImageIcon icon) {
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        ImageObserver observer = icon.getImageObserver();
        BufferedImage bufferedImage = UIUtil.createImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gc = bufferedImage.createGraphics();
        gc.drawImage(icon.getImage(), 0, 0, observer);
        return bufferedImage;
    }


    public static String getSuggestFileName() {
        return "Quoit_QR_CODE_" + System.currentTimeMillis();
    }

}
