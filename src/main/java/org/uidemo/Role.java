package org.uidemo;

public class Role {
    private String name;
    private double hp;
    private double atk;;
    private double def;

    public Role(String name, double hp, double atk, double def) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    };
    static public Role createRole(String name,double hp,double atk,double def){
     return new Role(name,hp,atk,def);
    }

    double getHp() {
        return this.hp;
    }
    public void setHp(double hp) {
        this.hp = hp;
    }
    public double getAtk() {
        return this.atk;
    }
    public double getDef() {
        return this.def;
    }
    public String getName() {
        return this.name;
    }

}
