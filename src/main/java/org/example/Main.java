package org.example;

import javax.swing.*;
import java.awt.*;

/**
 * created by czw on ${DATE}
 */
public class Main {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 22345;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // 检查是否支持系统托盘
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null,
                    "System tray is not supported", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        createAndShowTray();

        System.out.println("app end!");
    }

    private static void createAndShowTray() {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/tray_icon.png"));

        // 创建右键菜单
        PopupMenu menu = new PopupMenu();

        // 菜单项1：获取服务状态
        MenuItem statusItem = new MenuItem("get the server status");
        statusItem.addActionListener(e -> {
            //String response = sendRequestToServer("{\"action\": \"get_status\"}");
            String response = "statusItem action";
            showMessage("server status", response);
        });

        // 菜单项2：重启服务
        MenuItem restartItem = new MenuItem("second item service");
        restartItem.addActionListener(e -> {
            //String response = sendRequestToServer("{\"action\": \"restart\"}");
            String response = "secondItem action";
            showMessage("operating", response);
        });

        // 菜单项3：退出
        MenuItem exitItem = new MenuItem("exit");
        exitItem.addActionListener(e -> {
            System.exit(0);
        });

        // 添加菜单项
        menu.add(statusItem);
        menu.add(restartItem);
        menu.addSeparator();
        menu.add(exitItem);

        // 创建托盘图标
        TrayIcon trayIcon = new TrayIcon(image, "Service Panel", menu);
        trayIcon.setImageAutoSize(true);

        // 双击图标事件
        trayIcon.addActionListener(e -> {
            showMessage("Service info", "Double click the tray icon to quickly view the status");
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "Unable to add tray icon", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * 显示通知消息
     */
    private static void showMessage(String title, String message) {
        TrayIcon[] trayIcons = SystemTray.getSystemTray().getTrayIcons();
        if (trayIcons.length > 0) {
            trayIcons[0].displayMessage(title, message, TrayIcon.MessageType.INFO);
        }
    }
}