package org.uidemo;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        Role role1=Role.createRole("Paladin",200,15,30);
        Role role2=Role.createRole("Berserker",100,50,0);
        try{
            System.out.println(Battle.battle(role1,role2));
        }
        catch(IllegalStateException e) {
            e.printStackTrace();
        }
    }
}