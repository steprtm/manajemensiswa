package ManajemenSiswaGradle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB {
    private static final String URL = "jdbc:mysql://localhost:3306/db_sekolah";
    private static final String USER = "root"; // Ganti dengan username MySQL Anda
    private static final String PASSWORD = ""; // Ganti dengan password MySQL Anda

    public static void main(String[] args) {
        System.out.println("Menguji koneksi ke database...");
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (connection != null) {
                System.out.println("Koneksi ke database berhasil!");
            } else {
                System.out.println("Koneksi ke database gagal!");
            }
        } catch (SQLException e) {
            System.out.println("Kesalahan koneksi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
