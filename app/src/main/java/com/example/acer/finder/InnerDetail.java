package com.example.acer.finder;

/**
 * Created by acer on 28.08.2017.
 */

public class InnerDetail {

String formatted_address ;
    String international_phone_number;

    public InnerDetail(String formatted_address, String international_phone_number) {
        this.formatted_address = formatted_address;
        this.international_phone_number = international_phone_number;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
    }
}
