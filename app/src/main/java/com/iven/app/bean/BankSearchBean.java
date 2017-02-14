package com.iven.app.bean;

import java.util.List;

/**
 * @author Iven
 * @date 2017/2/14 13:59
 * @Description
 */

public class BankSearchBean {

    /**
     * bank : ICBC    银行代码
     * validated : true
     * cardType : DC
     * key : 6222021001116245702   银行卡号
     * messages : []
     * stat : ok
     */

    private String bank;
    private boolean validated;
    private String cardType;
    private String key;
    private String stat;
    private List<?> messages;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<?> getMessages() {
        return messages;
    }

    public void setMessages(List<?> messages) {
        this.messages = messages;
    }
}
