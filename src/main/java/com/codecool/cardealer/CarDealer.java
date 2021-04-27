package com.codecool.cardealer;

import java.util.List;

class CarDealer {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    CarDealer(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    void importCSV(String fileName){

    }

    List<String> dealersWithMoreThanSpecifiedNumOfCarTypeSold(int numOfCarTypes) {

    }

    String dealerWithMostCarSoldFromSpecificType(String carType) {

    }
}
