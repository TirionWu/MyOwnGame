package org.uidemo;

import java.security.spec.ECField;

public class Battle {

    public  static String battle(Role role1,Role role2){
         while (role1.getHp()>0 && role2.getHp() >0) {
             double role1NewHp = role1.getHp()-role2.getAtk()/(role2.getAtk()+role1.getDef());
             double role2NewHp = role2.getHp()-role1.getAtk()/(role1.getAtk()+role2.getDef());
             System.out.println(role1.getName()+"剩余血量"+role1NewHp +"\n" +role2.getName()+"剩余血量"+role2NewHp);
             role1.setHp(role1NewHp);
             role2.setHp(role2NewHp);
         }

         if(role1.getHp()<=0&&role2.getHp()<=0){
             return "平局";
         }
         else if(role1.getHp()>0&&role2.getHp()<=0){
             return (role1.getName()+"获胜");
         }
         else if(role1.getHp()<=0&&role2.getHp()>0){
             return (role2.getName()+"获胜");
         }
        else throw new  IllegalStateException("战斗出现异常");
    }
}
