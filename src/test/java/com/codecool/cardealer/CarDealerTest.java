package com.codecool.cardealer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarDealerTest {
    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";


    private CarDealer carDealer;

    @BeforeEach
    void init() throws SQLException {
        carDealer = new CarDealer(DB_URL,DB_USER,DB_PASSWORD);
        dropTable();
        createTable();
    }

    private void dropTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            String dropTable = "DROP TABLE IF EXISTS sold_car;";
            Statement statement = connection.createStatement();
            statement.execute(dropTable);
        }
    }

    private void createTable() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
            String createTable = "CREATE TABLE IF NOT EXISTS sold_car (" +
                    "id bigint auto_increment, " +
                    "car_type VARCHAR(255), " +
                    "dealer VARCHAR(255), " +
                    "num_of_sold INT);";
            Statement statement = connection.createStatement();
            statement.execute(createTable);
        }
    }

    @Test
    void test_importCSV_numberOfRowsCorrect() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            Statement statement = connection.createStatement();
            carDealer.importCSV("src/test/testfiles/test1.csv");
            ResultSet rs = statement.executeQuery("select count(*) AS rowcount from sold_car");
            rs.next();
            assertEquals(22, rs.getInt("rowcount"));
        }
    }

    @Test
    void test_dealersWithMoreThanSpecifiedNumOfCarTypeSold_correctDealersReturned(){
        carDealer.importCSV("src/test/testfiles/test1.csv");
        List<String> expected = new ArrayList<>(){{
            add("London");
            add("Budapest");
        }};
        List<String> result = carDealer.dealersWithMoreThanSpecifiedNumOfCarTypeSold(5);
        assertEquals(expected,result);
    }

    @Test
    void test_dealerWithMostCarSoldFromSpecificType(){
        carDealer.importCSV("src/test/testfiles/test1.csv");
        String expected = "Oslo";
        String result = carDealer.dealerWithMostCarSoldFromSpecificType("Tesla Model X");
        assertEquals(expected,result);
    }
}

