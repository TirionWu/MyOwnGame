package org.uidemo;

import org.uidemo.callback.RoundResultHandler;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {

        Role role1 = Role.builder().name("role1").atk(15).def(30).hp(200).build();
        Role role2 = Role.builder().name("role2").atk(50).def(0).hp(200).build();
        RoundResultHandler handler = res->{
            System.out.println(res);
        };
        Battle battle = Battle.builder().host(role1).guest(role2).roundResultHandler(handler).build();
        try {
            System.out.println(battle.battle());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}