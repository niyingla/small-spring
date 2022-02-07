package cn.bugstack.springframework.test.bean;

import java.time.LocalDate;

public class Husband {

    private String wifiName;

    private LocalDate marriageDate;

    private Wife wife;

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return "Husband{" +
            "wifiName='" + wifiName + '\'' +
            ", marriageDate=" + marriageDate +
            ", wife=" + wife +
            '}';
    }
}
