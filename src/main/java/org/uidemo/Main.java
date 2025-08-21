package org.uidemo;

import org.uidemo.callback.BattleResult;
import org.uidemo.callback.RoundResultHandler;

import java.util.Map;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    private static final int FRAME = 50;
    private static final int MAX_HEALTH_BAR_LEN = 20;

    /**
     * 程序入口函数，初始化两个角色并开始战斗演示。
     * 创建了两个角色role1和role2，并设置战斗结果处理器handler，
     * 然后构建Battle对象并执行战斗流程。
     * 战斗结束后清屏。
     */
    public static void main(String[] args) {
        // 从YAML文件加载角色信息
        Role role1 = YamlUtil.loadRoleFromYaml("player-info.yml", "role1");
        Role role2 = YamlUtil.loadRoleFromYaml("player-info.yml", "role2");
        
        RoundResultHandler handler = res -> {
            showHumans();
            displayBattleTexts(res);
            displayHealthBar(res);
            try {
                Thread.sleep(500);
            } catch (Throwable ignored) {
            }
        };
        Battle battle = Battle.builder().host(role1).guest(role2).roundResultHandler(handler).build();
        battle.battle();
        clearScreen();
    }

    /**
     * 显示双方角色的生命值条形图。
     * 根据当前生命值占初始生命值的比例，在固定长度的区域内绘制生命条。
     *
     * @param res 战斗回合结果对象，包含当前双方角色的生命值信息
     */
    private static void displayHealthBar(BattleResult res) {
        double host_percentage = res.getHostNewHP() / res.getInitHostHealth();
        int host_len = (int) (host_percentage * MAX_HEALTH_BAR_LEN);
        double guest_percentage = res.getGuestNewHP() / res.getInitGuestHealth();
        int guest_len = (int) (guest_percentage * MAX_HEALTH_BAR_LEN);

        // 绘制生命条框架
        for (int i = 0; i < FRAME; i++) {
            if (i == MAX_HEALTH_BAR_LEN || i + MAX_HEALTH_BAR_LEN == FRAME - 1)
                System.out.print("|");
            else
                System.out.print(" ");
        }
        System.out.println();

        // 绘制生命条内容
        for (int i = 0; i < FRAME; i++) {
            if (i < host_len || FRAME - 1 - i < guest_len)
                System.out.print("*");
            else
                System.out.print(" ");
        }
        System.out.println();
    }

    /**
     * 显示战斗文本信息，包括角色名称和当前生命值。
     *
     * @param res 战斗回合结果对象，包含当前双方角色的信息
     */
    private static void displayBattleTexts(BattleResult res) {
        showFieldsFromBothSides(res.getHost().getName(), res.getGuest().getName());
        showFieldsFromBothSides(res.getHost().getHp(), res.getGuest().getHp());
    }

    /**
     * 在屏幕两端显示两个字段的内容，中间用空格填充。
     *
     * @param host  左侧显示的内容
     * @param guest 右侧显示的内容
     */
    private static void showFieldsFromBothSides(Object host, Object guest) {
        String hostStr = String.valueOf(host);
        String guestStr = String.valueOf(guest);
        int spaces = FRAME - hostStr.length() - guestStr.length();
        spaces = Math.max(spaces, 1);
        System.out.println(hostStr + " ".repeat(spaces) + guestStr);
    }

    /**
     * 显示两个人物的简单图形表示（由#字符组成）。
     * 图形为5行高，宽度为FRAME列。
     */
    private static void showHumans() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < FRAME; col++) {
                System.out.print(isPrintNeeded(row, col) ? '#' : ' ');
            }
            System.out.println();
        }
    }

    /**
     * 判断在指定行列位置是否需要打印字符。
     *
     * @param row 行索引
     * @param col 列索引
     * @return 如果需要打印返回true，否则返回false
     */
    private static boolean isPrintNeeded(int row, int col) {
        int toFrame = FRAME - col - 1;
        switch (row) {
            case 0:
            case 2:
                return col == 1 || toFrame == 1;
            case 1:
                return (0 <= col && col <= 2) || (toFrame >= 0 && toFrame <= 2);
            case 3:
                return col == 0 || col == 2 || toFrame == 0 || toFrame == 2;
            default:
                return false;
        }
    }

    /**
     * 清除控制台内容。
     * 根据操作系统类型调用相应的清屏命令。
     */
    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 清屏方法
     */
    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}