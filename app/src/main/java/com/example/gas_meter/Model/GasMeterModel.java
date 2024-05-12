package com.example.gas_meter.Model;

public class GasMeterModel {

    private String gasId;

    private String gasState;

    private String gasCity;

    private String gasAdress;

    private String quantity;

    private String date;
    private String userName;

    public GasMeterModel(){}

    public GasMeterModel(String userName,String gasId, String gasState, String gasCity, String gasAdress) {
        this.gasId = gasId;
        this.gasState = gasState;
        this.gasCity = gasCity;
        this.gasAdress = gasAdress;
        this.userName=userName;
    }
    public GasMeterModel(String quantity, String date){
        this.quantity=quantity;
        this.date=date;
    }

    public String getGasId() {
        return gasId;
    }

    public String getGasState() {
        return gasState;
    }

    public String getGasCity() {
        return gasCity;
    }

    public String getGasAdress() {
        return gasAdress;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }
}
