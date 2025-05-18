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
            JOptionPane.showMessageDialog(null, "系统不支持托盘图标", "错误", JOptionPane.ERROR_MESSAGE);
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
        MenuItem statusItem = new MenuItem("获取状态");
        statusItem.addActionListener(e -> {
            //String response = sendRequestToServer("{\"action\": \"get_status\"}");
            String response = "statusItem action";
            showMessage("服务状态", response);
        });

        // 菜单项2：重启服务
        MenuItem restartItem = new MenuItem("重启服务");
        restartItem.addActionListener(e -> {
            //String response = sendRequestToServer("{\"action\": \"restart\"}");
            String response = "restartItem action";
            showMessage("操作结果", response);
        });

        // 菜单项3：退出
        MenuItem exitItem = new MenuItem("退出");
        exitItem.addActionListener(e -> {
            System.exit(0);
        });

        // 添加菜单项
        menu.add(statusItem);
        menu.add(restartItem);
        menu.addSeparator();
        menu.add(exitItem);

        // 创建托盘图标
        TrayIcon trayIcon = new TrayIcon(image, "服务控制面板", menu);
        trayIcon.setImageAutoSize(true);

        // 双击图标事件
        trayIcon.addActionListener(e -> {
            showMessage("服务信息", "双击托盘图标可快速查看状态");
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "无法添加托盘图标", "错误", JOptionPane.ERROR_MESSAGE);
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