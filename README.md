# Student Management System - PTIT

A comprehensive Java Swing application for managing students, courses, grades, and registrations at PTIT (Posts and Telecommunications Institute of Technology).

## Features

### 🎓 Student Management
- Add, update, delete, and view student information
- Manage student details including personal information, contact details, and class assignments
- Vietnamese language interface

### 📚 Course Management
- Manage subjects (MonHoc) with credit hours and department assignments
- Manage course sections (LopHocPhan) with instructor assignments and semester information
- Full CRUD operations for both entities

### 📊 Grade Management
- Record and manage student grades
- Support for attendance, midterm, final, and total grades
- Automatic grade calculation with customizable weights
- Grade tracking per registration

### 📝 Registration Management
- Handle student course registrations
- Track registration dates and course assignments
- Link students to specific course sections

## Technical Stack

- **Java 17** - Programming language
- **Swing** - GUI framework
- **MySQL** - Database
- **Maven** - Build tool
- **JDBC** - Database connectivity

## Database Schema

The application uses a comprehensive database schema with the following main entities:

- **Khoa** (Department)
- **Lop** (Class)
- **SinhVien** (Student)
- **GiangVien** (Lecturer)
- **MonHoc** (Subject)
- **LopHocPhan** (Course Section)
- **DangKy** (Registration)
- **BangDiem** (Grade)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL Server
- Maven

### Database Setup
1. Create a MySQL database named `quanlysinhvien`
2. Run the `database_schema.sql` script to create tables
3. Update database connection settings in `src/main/java/db/DatabaseConnector.java`

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the following commands:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="training.minhdoswe.Main"
```

Or simply run the `Main.java` file from your IDE.

## Application Structure

```
src/main/java/
├── dao/                    # Data Access Objects
├── db/                     # Database connection utilities
├── gui/                    # Swing GUI components
├── model/                  # Data models/entities
└── training/minhdoswe/     # Main application entry point
```

## GUI Features

- **Tabbed Interface**: Organized management sections
- **Data Tables**: View and select records
- **Form Inputs**: Add and edit data
- **Validation**: Input validation and error handling
- **Vietnamese Localization**: Full Vietnamese language support

## Usage

1. **Student Management**: Use the first tab to manage student records
2. **Course Management**: Use the second tab to manage subjects and course sections
3. **Grade Management**: Use the third tab to record and manage grades
4. **Registration Management**: Use the fourth tab to handle course registrations

## Bug Fixes Applied

- Fixed Java version compatibility (changed from Java 24 to Java 17)
- Fixed date type mismatch in SinhVien model
- Fixed method name inconsistencies in DAO classes
- Added proper error handling and validation

## Future Enhancements

- Add search and filtering capabilities
- Implement data export/import functionality
- Add user authentication and role-based access
- Enhance reporting features
- Add data backup and restore functionality

## Contributing

Feel free to contribute to this project by:
- Reporting bugs
- Suggesting new features
- Submitting pull requests
- Improving documentation

## License

This project is developed for educational purposes at PTIT.
