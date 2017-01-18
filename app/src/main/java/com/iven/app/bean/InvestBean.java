package com.iven.app.bean;

import io.realm.RealmObject;

/**
 * @author Iven
 * @date 2017/1/18 11:44
 * @Description
 */

public class InvestBean extends RealmObject {
    private int id;
    private String name;
    private boolean option;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOption() {
        return option;
    }

    public void setOption(boolean option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "InvestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", option=" + option +
                '}';
    }
}
