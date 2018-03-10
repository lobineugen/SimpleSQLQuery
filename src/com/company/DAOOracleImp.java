package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOOracleImp implements DAOModel {
    private static final DAOOracleImp instance = new DAOOracleImp();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private DAOOracleImp() {
    }

    public static DAOOracleImp getInstance() {
        return instance;
    }

    @Override
    public void connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                    "EUGENE", "43g6ds5rs4");
            if (!connection.isClosed()) {
                System.out.println("Connection successful");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null)
                connection.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudents() {
        connect();
        List<Student> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM STUDENTS");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(parseStudent(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return list;
    }

    @Override
    public void insertStudent(String name, String group) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO STUDENTS (STUDENT_NAME,STUDENT_GROUP) " +
                    "VALUES ('" + name + "','" + group + "')");
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Insert is successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }


    @Override
    public void deleteStudentById(int id) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM STUDENTS WHERE STUDENT_ID=" + id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Delete is successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    @Override
    public void updateStudentById(int id, String name, String group) {
        connect();
        try {
            preparedStatement = connection.prepareStatement("UPDATE STUDENTS SET STUDENT_NAME='" + name +
                    "',STUDENT_GROUP='" + group + "' WHERE STUDENT_ID=" + id);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Update is successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    private Student parseStudent(ResultSet resultSet) throws SQLException {
        return new Student(resultSet.getInt("STUDENT_ID"),
                resultSet.getString("STUDENT_NAME"),
                resultSet.getString("STUDENT_GROUP"));
    }

}
