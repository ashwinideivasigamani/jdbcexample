package bluescope;
import java.sql.*;
import java.util.*;

public class Example1 {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bluescope";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    private static final String INSERT_SQL = "INSERT INTO employee(name, id) VALUES (?, ?)";

    public static List<Map<String, Object>> readRecordsAndConvertToArrayOfMap() {
        List<Map<String, Object>> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String sql = "SELECT * FROM employee";
            ResultSet resultSet = statement.executeQuery(sql);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue);
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void insertRecords(List<Map<String, Object>> records) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Enter id: ");
                int id = scanner.nextInt();
                System.out.println("Enter name: ");
                String name = scanner.next();

                Map<String, Object> recordMap = new HashMap<>();
                recordMap.put("id", id);
                recordMap.put("name", name);
                records.add(recordMap);

                preparedStatement.setObject(1, name);
                preparedStatement.setObject(2, id);
                preparedStatement.executeUpdate();

                System.out.println("Do you want to add more data? (Y/N)");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("N")) {
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printRecords(List<Map<String, Object>> records) {
        for (Map<String, Object> record : records) {
            System.out.println(record);
        }
    }

    public static void updateRecord(String name, int Id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee SET name = ? WHERE id = ?")) {

            preparedStatement.setObject(1, name);
            preparedStatement.setInt(2, Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecord(int recordId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE id = ?")) {

            preparedStatement.setInt(1, recordId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws ClassNotFoundException, SQLException {
    	String choice;
		System.out.println("*****WELCOME TO STUDENT MANAGEMENT SYSTEM*****");
		System.out.println("Enter ");
		System.out.println("1:Add Student");
		System.out.println("2:Display Student's details");
		
		System.out.println("3:Update Student");
		System.out.println("4:Delete Student");
		System.out.println("5:To Exit");
		Scanner sc = new Scanner(System.in);

		choice = sc.nextLine();
    }

    public static void operation(int choice) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        List<Map<String, Object>> records = readRecordsAndConvertToArrayOfMap();

        switch (choice) {
            case 1:
                System.out.println("Enter the data to insert");
                insertRecords(records);
                break;
            case 2:
                System.out.println("All records");
                printRecords(records);
                break;
            case 3:
            	System.out.println("enter name to update");
                String na=sc.next();
                System.out.println("Enter id");
                int id=sc.nextInt();
                
                updateRecord(na, id); 
                break;
            case 4:
                System.out.println("Enter id");
                int idToDelete = sc.nextInt();
                deleteRecord(idToDelete);
                System.out.println("Deleted successfully");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException 
	{


		Scanner sc = new Scanner(System.in);
		String userId = "root";
		int password = 12345;
		System.out.println("Enter User Id");
		String uId = sc.nextLine();
		if (uId.equals(userId)) 
		{
			System.out.println("Enter The Password");
			int pass = sc.nextInt();
			if (pass == password)
			{
				System.out.println("\nLogin Successfully.....");
				do 
				{
					menu();
					System.out.println("Enter Your Choice");
					int ch = sc.nextInt();
					if (ch == 5) 
					{
						System.out.println("Thank You!...");
						break;
					}
					operation(ch);

				} 
				while (true);
			}
			else
				System.out.println("Invalid Password! Try Again");
		} 
		else
			System.out.println("Invalid user Id! Try Again");

	}


}

