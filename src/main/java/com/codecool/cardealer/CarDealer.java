package com.codecool.cardealer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CarDealer {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private static final int CAR_TYPE_INDEX = 0;
    private static final int DEALER_INDEX = 1;
    private static final int NUM_OF_SOLD_INDEX = 2;


    CarDealer(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private void addRowToDatabase(String carType, String dealer, int numOfSold){
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO sold_car (car_type, dealer, num_of_sold) VALUES (?,?,?)");
            statement.setString(1,carType);
            statement.setString(2,dealer);
            statement.setInt(3,numOfSold);
            statement.execute();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    void importCSV(String fileName){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datas = line.split(",");
                addRowToDatabase(datas[CAR_TYPE_INDEX],datas[DEALER_INDEX],Integer.parseInt(datas[NUM_OF_SOLD_INDEX]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<String> dealersWithMoreThanSpecifiedNumOfCarTypeSold(int numOfCarTypes) {
        List<String> dealers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT dealer, COUNT(car_type)" +
                            " FROM sold_car " +
                            " GROUP BY dealer" +
                            " HAVING COUNT(car_type) > ?" +
                            " ORDER BY dealer DESC");
            statement.setInt(1, numOfCarTypes);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                dealers.add(rs.getString("dealer"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dealers;
    }

    String dealerWithMostCarSoldFromSpecificType(String carType) {
        String dealer = "";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT MAX(dealer) AS md" +
                            " FROM sold_car " +
                            " WHERE car_type = ?");
            statement.setString(1, carType);
            ResultSet rs = statement.executeQuery();
            rs.next();
            dealer = rs.getString("md");
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
        return dealer;
    }
}
