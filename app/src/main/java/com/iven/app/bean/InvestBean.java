package com.iven.app.bean;

import java.util.List;

import io.realm.RealmObject;

/**
 * @author Iven
 * @date 2017/1/18 11:44
 * @Description
 */

public class InvestBean extends RealmObject {

    /**
     * status_code : 200
     * message : success
     * data : [{"id":63,"name":"传媒","option":true},{"id":32,"name":"餐饮旅游","option":true},{"id":20,"name":"电力及公用事业","option":false},{"id":27,"name":"电力设备","option":true},{"id":60,"name":"电子元器件","option":false},{"id":42,"name":"房地产","option":false},{"id":41,"name":"非银行金融","option":true},{"id":34,"name":"纺织服装","option":true},{"id":28,"name":"国防军工","option":false},{"id":21,"name":"钢铁","option":true},{"id":22,"name":"基础化工","option":true},{"id":24,"name":"建材","option":true},{"id":33,"name":"家电","option":true},{"id":62,"name":"计算机","option":true},{"id":50,"name":"交通运输","option":true},{"id":26,"name":"机械","option":true},{"id":23,"name":"建筑","option":true},{"id":11,"name":"煤炭","option":true},{"id":37,"name":"农林牧渔","option":true},{"id":30,"name":"汽车","option":true},{"id":25,"name":"轻工制造","option":true},{"id":31,"name":"商贸零售","option":true},{"id":36,"name":"食品饮料","option":true},{"id":10,"name":"石油化工","option":true},{"id":61,"name":"通信","option":true},{"id":40,"name":"银行","option":true},{"id":12,"name":"有色金属","option":true},{"id":35,"name":"医药","option":true},{"id":70,"name":"综合","option":true}]
     */

    private int status_code;
    private String message;
    private List<DataBean> data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 63
         * name : 传媒
         * option : true
         */

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
    }
}
