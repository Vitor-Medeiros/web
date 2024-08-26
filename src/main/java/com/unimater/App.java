package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.HelloWorldHandler;
import com.unimater.controller.ProductTypeHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.*;

public class App {
    public static void main(String[] args) {

        try {
            // Configura o servidor HTTP
            HttpServer servidor = HttpServer.create(
                    new InetSocketAddress(8080), 0
            );

            // Conecta ao banco de dados
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3305/unimater_db", 
                    "root", 
                    "root"
            );

            // Testa as tabelas
            testSaleTable(connection);
            testSaleItemTable(connection);
            testProductTypeTable(connection);
            testProductTable(connection);

            servidor.createContext("/helloWorld", new HelloWorldHandler());
            servidor.createContext("/productType", new ProductTypeHandler(connection));

            servidor.setExecutor(null);
            servidor.start();
            System.out.println("Servidor rodando na porta 8080");

        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Testa os dados da tabela Sale
    public static void testSaleTable(Connection connection) {
        System.out.println("Consultando a tabela Sale...");

        String query = "SELECT * FROM Sale";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int saleId = resultSet.getInt("id");
                Timestamp insertAt = resultSet.getTimestamp("insert_at");

                System.out.println("ID: " + saleId);
                System.out.println("Insert At: " + insertAt);
                System.out.println("---------------------------");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar a tabela Sale: " + e.getMessage());
        }
    }

    // Testa os dados da tabela SaleItem
    public static void testSaleItemTable(Connection connection) {
        System.out.println("Consultando a tabela SaleItem...");

        String query = "SELECT * FROM SaleItem";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int saleItemId = resultSet.getInt("id");
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");
                double discount = resultSet.getDouble("percentual_discount");

                System.out.println("Sale Item ID: " + saleItemId);
                System.out.println("Product ID: " + productId);
                System.out.println("Quantity: " + quantity);
                System.out.println("Discount: " + discount);
                System.out.println("---------------------------");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar a tabela SaleItem: " + e.getMessage());
        }
    }

    // Testa os dados da tabela ProductType
    public static void testProductTypeTable(Connection connection) {
        System.out.println("Consultando a tabela ProductType...");

        String query = "SELECT * FROM ProductType";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productTypeId = resultSet.getInt("id");
                String productTypeDescription = resultSet.getString("description");

                System.out.println("Product Type ID: " + productTypeId);
                System.out.println("Product Type description: " + productTypeDescription);
                System.out.println("---------------------------");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar a tabela ProductType: " + e.getMessage());
        }
    }

    // Testa os dados da tabela Product
    public static void testProductTable(Connection connection) {
        System.out.println("Consultando a tabela Product...");

        String query = "SELECT * FROM Product";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("id");
                String productdescription = resultSet.getString("description");
                double value = resultSet.getDouble("value");
                int productTypeId = resultSet.getInt("product_type_id");

                System.out.println("Product ID: " + productId);
                System.out.println("Product description: " + productdescription);
                System.out.println("value: " + value);
                System.out.println("Product Type ID: " + productTypeId);
                System.out.println("---------------------------");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Erro ao consultar a tabela Product: " + e.getMessage());
        }
    }
}
