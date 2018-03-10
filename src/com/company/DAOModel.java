package com.company;

import java.util.List;

interface DAOModel {
    void connect();
    void disconnect();
    List<Student> getAllStudents();
    void insertStudent(String name, String group);
    void deleteStudentById(int id);
    void updateStudentById(int id, String name, String group);
}
