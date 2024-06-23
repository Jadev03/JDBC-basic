import java.sql.*;

public class Main {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student";

    public static void main(String[] args) {
        String user_Name = "root";
        String password = "1234";

        try {
            System.out.println("Connecting to Database");
            Connection con = DriverManager.getConnection(DB_URL, user_Name, password);
            System.out.println("Connected to Database");

            Statement statement = con.createStatement();

            // Insert query into basic table
            String prepare = "INSERT INTO basic (StudentID, Name, Age) VALUES (?, ?, ?)";
            try (PreparedStatement pre = con.prepareStatement(prepare)) {
                pre.setInt(1, 7);
                pre.setString(2, "Kumar");
                pre.setInt(3, 20);
                pre.executeUpdate();
            } catch (Exception e) {
                System.out.println("Exception in Prepared Statement");
                e.printStackTrace();
            }

            // Update query
            try {
                String updateSql = "UPDATE basic SET Name = ?, Age = ? WHERE StudentID = ?";
                try (PreparedStatement prep = con.prepareStatement(updateSql)) {
                    prep.setString(1, "Johnson");
                    prep.setInt(2, 30);
                    prep.setInt(3, 5);
                    prep.executeUpdate();
                }
            } catch (Exception e) {
                System.out.println("Update Error");
                e.printStackTrace();
            }

            // Delete query
            try {
                String deleteSql = "DELETE FROM basic WHERE StudentID = ?";
                try (PreparedStatement pr = con.prepareStatement(deleteSql)) {
                    pr.setInt(1, 7);
                    pr.executeUpdate();
                }
            } catch (Exception e) {
                System.out.println("Exception on deletion");
                e.printStackTrace();
            }

            // Create faculty table
            try {
                String createFacultyTable = "CREATE TABLE IF NOT EXISTS faculty (" +
                        "FacultyName VARCHAR(100) PRIMARY KEY, " +
                        "StudentID INT, " +
                        "FOREIGN KEY (StudentID) REFERENCES basic(StudentID))";
                try (PreparedStatement p = con.prepareStatement(createFacultyTable)) {
                    p.executeUpdate();
                }
            } catch (Exception e) {
                System.out.println("Exception in table creation");
                e.printStackTrace();
            }

            // Insert data into faculty table
            try {
                String U = "INSERT INTO faculty(FacultyName, StudentID) VALUES (?, ?)";
                try (PreparedStatement prr = con.prepareStatement(U)) {
                    prr.setString(1, "CSE");
                    prr.setInt(2, 1);
                    prr.executeUpdate();
                }
            } catch (Exception e) {
                System.out.println("Exception in insert data in faculty table");
                e.printStackTrace();
            }

            // Select query to retrieve and display data from basic table
            String selectSql = "SELECT * FROM basic";
            try (ResultSet result = statement.executeQuery(selectSql)) {
                System.out.println("Basic Table");
                while (result.next()) {
                    int id = result.getInt("StudentID");
                    String name = result.getString("Name");
                    int age = result.getInt("Age");

                    System.out.print("ID: " + id);
                    System.out.print(", Name: " + name);
                    System.out.println(", Age: " + age);
                }
            }

            // Select query to retrieve and display data from faculty table
            System.out.println("Faculty Table");
            try {
                String selectQuery = "SELECT * FROM faculty";
                try (PreparedStatement pstmt = con.prepareStatement(selectQuery);
                     ResultSet srs = pstmt.executeQuery()) {
                    while (srs.next()) {
                        String facultyName = srs.getString("FacultyName");
                        int studentID = srs.getInt("StudentID");

                        System.out.print("FacultyName: " + facultyName);
                        System.out.println(", StudentID: " + studentID);
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception in view faculty table");
                e.printStackTrace();
            }

            // Close resources
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
}
