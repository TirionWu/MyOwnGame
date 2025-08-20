package org.uidemo;

import org.uidemo.callback.BattleResult;
import org.uidemo.callback.RoundResultHandler;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    private static final int FRAME = 50;
    private static final int MAX_HEALTH_BAR_LEN = 20;

    public static void main(String[] args) {

        Role role1 = Role.builder().name("role1").atk(15).def(30).hp(200).build();
        Role role2 = Role.builder().name("role2").atk(50).def(0).hp(200).build();
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
        try {
            System.out.println(battle.battle());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        clearConsole();
    }

    private static void displayHealthBar(BattleResult res) {
        double host_percentage = res.getHostNewHP() / res.getInitHostHealth();
        int host_len = (int) (host_percentage * MAX_HEALTH_BAR_LEN);
        double guest_percentage = res.getGuestNewHP() / res.getInitGuestHealth();
        int guest_len = (int) (guest_percentage * MAX_HEALTH_BAR_LEN);
        for (int i = 0; i < FRAME; i++) {
            if (i == MAX_HEALTH_BAR_LEN || i + MAX_HEALTH_BAR_LEN == FRAME - 1)
                System.out.print("|");
            else
                System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < FRAME; i++) {
            if (i < host_len || FRAME - 1 - i < guest_len)
                System.out.print("*");
            else
                System.out.print(" ");
        }
        System.out.println();
    }

    private static void displayBattleTexts(BattleResult res) {
        showFieldsFromBothSides(res.getHost().getName(), res.getGuest().getName());
        showFieldsFromBothSides(res.getHost().getHp(), res.getGuest().getHp());
    }

    private static void showFieldsFromBothSides(Object host, Object guest) {
        String hostStr = String.valueOf(host);
        String guestStr = String.valueOf(guest);
        int spaces = FRAME - hostStr.length() - guestStr.length();
        spaces = Math.max(spaces, 1);
        System.out.println(hostStr + " ".repeat(spaces) + guestStr);
    }

    private static void showHumans() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < FRAME; col++) {
                System.out.print(isPrintNeeded(row, col) ? '#' : ' ');
            }
            System.out.println();
        }
    }

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

}