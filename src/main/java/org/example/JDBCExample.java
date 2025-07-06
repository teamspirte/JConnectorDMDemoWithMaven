package org.example;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/DMJConnectorWithMaven";
        String user = "root";
        String pass = "root";

        String SQL = "SELECT id, name, salary, created_date FROM employee";

        List<Employee> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Employee e = new Employee();
                e.id = rs.getLong("id");
                e.name = rs.getString("name");
                e.salary = rs.getBigDecimal("salary");
                e.createdDate = rs.getTimestamp("created_date").toLocalDateTime();
                list.add(e);
            }

            list.forEach(e -> System.out.println(e));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    static class Employee {
        long id;
        String name;
        BigDecimal salary;
        LocalDateTime createdDate;

        public String toString() {
            return String.format("Employee[%d, %s, salary=%s, created=%s]",
                    id, name, salary, createdDate);
        }
    }
}
