package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class ClientDemo {

    private static SessionFactory sessionFactory;

    static {
        // Set up Hibernate configuration and session factory
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    // Method to add a new department
    public static void addDepartment() {
        try (Session session = sessionFactory.openSession()) {
            // Start transaction
            Transaction transaction = session.beginTransaction();

            // Create a new department
            Department dept = new Department();
            dept.setName("HR");
            dept.setLocation("Mumbai");
            dept.setHodName("Alice");

            // Persist the department object
            session.save(dept);

            // Commit the transaction
            transaction.commit();

            System.out.println("Department added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to delete a department by ID
    public static void deleteDepartment() {
        try (Session session = sessionFactory.openSession(); Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter Department ID to delete:");
            int deleteId = scanner.nextInt();

            // Start transaction
            Transaction transaction = session.beginTransaction();

            // Prepare the HQL delete query
            String hql = "DELETE FROM Department WHERE id = :deptId";
            int deleted = session.createQuery(hql)
                                 .setParameter("deptId", deleteId)
                                 .executeUpdate();

            // Commit the transaction
            transaction.commit();

            // Output the result
            if (deleted > 0) {
                System.out.println("Department deleted successfully!");
            } else {
                System.out.println("No department found with ID: " + deleteId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method (entry point)
    public static void main(String[] args) {
        // Provide a simple menu for adding or deleting departments
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an operation:");
        System.out.println("1. Add Department");
        System.out.println("2. Delete Department");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Add a department
            addDepartment();
        } else if (choice == 2) {
            // Delete a department record
            deleteDepartment();
        } else {
            System.out.println("Invalid choice");
        }

        // Close the session factory after use
        sessionFactory.close();
        scanner.close();
    }
}
