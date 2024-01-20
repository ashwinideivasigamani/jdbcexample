package bluescope;

import java.awt.DisplayMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Example1 {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bluescope";
	private static final String USER = "root";
	private static final String PASSWORD = "12345";

	// Method to read records from the database and convert them into an Array of
	// Map
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
	         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee(id, name) VALUES (?, ?)")) {

	        Scanner scanner = new Scanner(System.in);
	        System.out.println("How many records do you want to enter?");
	        int x = scanner.nextInt();

	        int count = 0;
	        for (Map<String, Object> record : records) {

	            System.out.println("Enter id: ");
	            int id = scanner.nextInt();
	            System.out.println("Enter name: ");
	            String name = scanner.next();


	            preparedStatement.setObject(1, id);
	            preparedStatement.setObject(2, name);
	            preparedStatement.executeUpdate();

	            count++;

	            // Check if the count equals the desired number of records
	            if (count == x) {
	                break;
	               
	                
	            }
	           // printRecords(records);
	            

	            // Consume the newline character after nextInt() to prevent issues
	            scanner.nextLine();
	            
	            
	        }

	        // Close the scanner after the loop
	        scanner.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Method to print values present in the Array of Map
	public static void printRecords(List<Map<String, Object>> records) {
		for (Map<String, Object> record : records) {
			System.out.println(record);
		}
	}

	// Method to update records based on some condition (Update Operation)
	public static void updateRecord( int recordId,String newValue) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection
						.prepareStatement("UPDATE employee SET name = ? WHERE id = ?")) {

			preparedStatement.setObject(1, newValue);
			preparedStatement.setInt(2, recordId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to delete records based on some condition (Delete Operation)
	public static void deleteRecord(int recordId) {
		try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection
						.prepareStatement("DELETE FROM employee WHERE id = ?")) {

			preparedStatement.setInt(1, recordId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void menu() throws ClassNotFoundException, SQLException {
	    String choice;
	    

	    System.out.println("*****WELCOME******");
	    System.out.println("Enter the choice");
	    System.out.println("1:Add data");
	    System.out.println("2:Display data");
	    System.out.println("3:Update data");
	    System.out.println("4:Delete data");
	    System.out.println("5:To Exit");

	    // Consume the newline character after nextInt()
//	    sc.nextLine();
//	    System.out.println("enter the choice");
	    Scanner sc = new Scanner(System.in);
	   int ch = sc.nextInt();
	    operation(ch);
	    
//	    sc.close();
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
			System.out.println("****************");
			printRecords(records);
			break;
		case 3:
			System.out.println("enter id");
			int num=sc.nextInt();
			System.out.println("enter name");
			String name=sc.next();
			updateRecord(num, name);
			break;
		case 4:
			System.out.println("enter id");
			
			int nu=sc.nextInt();
			deleteRecord(nu);
			System.out.println("deleted succesfully");
			break;
			default:
				System.out.println("enter the choice");

		}
		}
	

//    public static void main(String[] args) {
//    	System.out.println("..............");
//        // Call the first method to read records and convert to Array of Map
//        List<Map<String, Object>> records = readRecordsAndConvertToArrayOfMap();
//
//        // Call the second method to insert records using the values from the first method's result
//        insertRecords(records);
//
//        // Call the third method to print values present in the Array of Map
//        printRecords(records);
//
//        // Example of an update operation
//        updateRecord("NewValue", 1);
//
//        // Example of a delete operation
//        deleteRecord(2);
//        
//        
//        
//        
//    }
//}

//________________________________________________________________________
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

//	while(true)
//	{
		Scanner sc = new Scanner(System.in);
		int ch;
		
		String userId = "root";
		int password = 12345;
		System.out.println("Enter User Id");
		String uId = sc.nextLine();
		if (uId.equals(userId)) {
			System.out.println("Enter The Password");
			int pass = sc.nextInt();
			if (pass == password) {
				System.out.println("\nLogin Successfully.....");
				do {
					menu();
					System.out.println("Enter Your Choice");
					ch=sc.nextInt() ;
					
					if (ch == 5) {
						System.out.println("Thank You!...");
						break;
					}
//					operation(ch);
//					sc.close();

				} while (true);
			} else
				System.out.println("Invalid Password! Try Again");
		} else
			System.out.println("Invalid user Id! Try Again");

	}

//		System.out.println("welcome");
////		System.out.println("enter the choice");
//		menu();
//		
	}


