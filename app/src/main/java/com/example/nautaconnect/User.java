package com.example.nautaconnect;

public class User {
    private String username;
    private String password;
    private String ATTRIBUTE_UUID;
    private String CSRFHW;
    private String leftTime;
    private String saldoCuenta;
    private String estadoCuenta;
    private static User my_user;

    public User() {
        this.username = "prp";
    }

    public static User getUser(){
        if (my_user==null){
            my_user = new User();
        }
            return my_user;
    }

    public void setUser(User user){
        my_user = user;
    }

    public User(String username, String password, String ATTRIBUTE_UUID, String CSRFHW, String leftTime, String saldoCuenta, String estadoCuenta) {
        this.username = username;
        this.password = password;
        this.ATTRIBUTE_UUID = ATTRIBUTE_UUID;
        this.CSRFHW = CSRFHW;
        this.leftTime = leftTime;
        this.saldoCuenta = saldoCuenta;
        this.estadoCuenta = estadoCuenta;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ATTRIBUTE_UUID='" + ATTRIBUTE_UUID + '\'' +
                ", CSRFHW='" + CSRFHW + '\'' +
                ", leftTime='" + leftTime + '\'' +
                ", saldoCuenta='" + saldoCuenta + '\'' +
                ", estadoCuenta='" + estadoCuenta + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getATTRIBUTE_UUID() {
        return ATTRIBUTE_UUID;
    }

    public void setATTRIBUTE_UUID(String ATTRIBUTE_UUID) {
        this.ATTRIBUTE_UUID = ATTRIBUTE_UUID;
    }

    public String getCSRFHW() {
        return CSRFHW;
    }

    public void setCSRFHW(String CSRFHW) {
        this.CSRFHW = CSRFHW;
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    public String getSaldoCuenta() {
        return saldoCuenta;
    }

    public void setSaldoCuenta(String saldoCuenta) {
        this.saldoCuenta = saldoCuenta;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
}
