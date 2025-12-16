package com.seapos.webapi.models;

public class SendNotification {

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public Number getAmount() {
        return Amount;
    }

    public void setAmount(Number amount) {
        Amount = amount;
    }

    public int getCashback() {
        return Cashback;
    }

    public void setCashback(int cashback) {
        Cashback = cashback;
    }

    public String getTxnDate() {
        return TxnDate;
    }

    public void setTxnDate(String txnDate) {
        TxnDate = txnDate;
    }

    private String TerminalId;
    private String OrderId;
    private Number Amount;
    private int Cashback;
    private  String TxnDate;

}
