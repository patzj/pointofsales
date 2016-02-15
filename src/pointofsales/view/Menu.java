/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pointofsales.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.InputMismatchException;
import pointofsales.model.Product;
import pointofsales.io.ProductIO;
import static pointofsales.util.PrintWriter.*;

/**
 *
 * @author patzj
 */
public class Menu {
    /**
     * Home menu
     */
    public static void displayHomeMenu() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        
        drawHorizontalBrokenLines(28);
        println("|      Point-Of-Sales      |");
        drawHorizontalBrokenLines(28);
        println("| 1 - Add                  |");
        println("| 2 - Update               |");
        println("| 3 - Delete               |");
        println("| 4 - Purchase             |");
        println("| 5 - Exit                 |");
        drawHorizontalBrokenLines(28);
        print("Enter choice: ");
        
        try {
            choice = input.nextInt();
            
            if(choice < 1 || choice > 5) {
                throw new InputMismatchException();
            }
        } catch(InputMismatchException e) {
            // redisplay home menu
            println("Invalid input.");
            displayHomeMenu();
        }
        
        switch (choice) {
            case 1:
                displayAddMenu();
                break;
            case 2:
                displayUpdateMenu();
                break;

            case 3:

                break;

            case 4:

                break;

            case 5:
                println("Exit.");
                System.exit(0);
                break;
            default:
        }
    }
    
    /**
     * Add menu
     */
    public static void displayAddMenu() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        
        drawHorizontalBrokenLines(28);
        println("|         Add Menu         |");
        drawHorizontalBrokenLines(28);
        println("|       Product List       |");
        drawHorizontalBrokenLines(28);
        viewProductList();
        println("| 1 - Add Product          |");
        println("| 2 - Back                 |");
        drawHorizontalBrokenLines(28);
        print("Enter Choice: ");
        
        try {
            choice = input.nextInt();
            
            if(choice < 1 || choice > 2) {
                throw new InputMismatchException();
            }
        } catch(InputMismatchException e) {
            // redisplay add menu
            println("Invalid input.");
            displayAddMenu();
        }
        
        switch(choice) {
            case 1:
                doAddProduct();
                break;
            case 2:
                displayHomeMenu();
                break;
            default:
        }
    }
    
    /**
     * View product list
     */
    public static void viewProductList() {
        ProductIO io = new ProductIO();
        Product product;
        ArrayList<Product> productList;
        Iterator iterator;
        
        io.readProductList();
        productList = io.getProductList();
        iterator = productList.iterator();
        
        while(iterator.hasNext()) {
            product = (Product) iterator.next();
            drawHorizontalBrokenLines(28);
            println("ID:\t" + product.getId());
            println("Name:\t" + product.getName());
            println("Qty:\t" + product.getQuantity());
            println("Price:\t" + product.getPrice());
            drawHorizontalBrokenLines(28);
        }
    }
    
    /**
     * Add product to product list
     */
    public static void doAddProduct() {
        Scanner input = new Scanner(System.in);
        ProductIO io;
        Product product;
        int id = 0;
        String name = "";
        int quantity = 0;
        double price = 0.0;
        String onErrChoice;
        
        try {
            drawHorizontalBrokenLines(28);
            print("Enter ID:\t");
            id = input.nextInt();
            input.nextLine();
            print("Enter Name:\t");
            name = input.nextLine();
            print("Enter Qty:\t");
            quantity = input.nextInt();
            print("Enter Price:\t");
            price = input.nextDouble();
        } catch(InputMismatchException e) {
            input.nextLine();
            println("Invalid input. Product not added.");
            print("Continue adding product? y/n:\t");
            onErrChoice = input.nextLine();
            
            if(onErrChoice.equalsIgnoreCase("y")) {
                doAddProduct();
            } else {
                displayAddMenu();
            }
        }
        
        product = new Product();
        product.setId(id);
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        
        io = new ProductIO();
        io.readProductList();
        io.addProduct(product);
        io.writeProductList();
        
        drawHorizontalBrokenLines(28);
        println("Successfully added new product.");
        drawHorizontalBrokenLines(28);
        displayAddMenu();
    }
    
    public static void displayUpdateMenu() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        
        drawHorizontalBrokenLines(28);
        println("|       Update Menu        |");
        drawHorizontalBrokenLines(28);
        println("|       Product List       |");
        drawHorizontalBrokenLines(28);
        viewProductList();
        println("| 0 - Back                 |");
        drawHorizontalBrokenLines(28);
        print("Enter Product ID: ");
        
        try {
            choice = input.nextInt();

            if(choice < 0) {
                throw new InputMismatchException();
            }
        } catch(InputMismatchException e) {
            // redisplay add menu
            println("Invalid input.");
            displayUpdateMenu();
        }
        
        if(choice == 0) {
            displayHomeMenu();
        } else {
            doUpdateProduct(choice);
        }        
    }
    
    public static void doUpdateProduct(int id) {
        ProductIO io = new ProductIO();
        Product temp;
        Product product = null;
        ArrayList<Product> productList;
        Iterator iterator;
        int index;
        
        io.readProductList();
        productList = io.getProductList();
        iterator = productList.iterator();
        
        // get product to be edited
        while(iterator.hasNext()) {
            temp = (Product) iterator.next();
            if(temp.getId() == id) {
                product = temp;
            }
        }
        
        if(product != null) {
            // get index of product
            index = productList.indexOf(product);
            product = updateProductForm(product);
            productList.set(index, product);
            io.writeProductList();
            drawHorizontalBrokenLines(28);
            println("Successfully updated product.");
            displayUpdateMenu();
        } else {
            println("Invalid id.");
            displayUpdateMenu();
        }
    }
    
    public static Product updateProductForm(Product product) {
        Scanner input = new Scanner(System.in);
        String name;
        int quantity;
        double price;     

        drawHorizontalBrokenLines(28);
        println("Leave the fields you \n"
                + "don't want to update.");
        drawHorizontalBrokenLines(28);
        try {
            print("Enter New Name: ");
            name = input.nextLine();
            
            if(name.equals("") || name.isEmpty()) {
                throw new InputMismatchException();
            }
        } catch(InputMismatchException e) {
            name = product.getName();
        }
        
        try {
            print("Enter New Qty: ");
            quantity = Integer.parseInt(input.nextLine());
            
            if(quantity < 0) {
                throw new InputMismatchException();
            }
        } catch(NumberFormatException e) {
            quantity = product.getQuantity();
        }
        
        try {
            print("Enter New Price: ");
            price = Double.parseDouble(input.nextLine());
            
            if(price < 0) {
                throw new InputMismatchException();
            }
        } catch(NumberFormatException e) {
            price = product.getPrice();
        }
        
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        
        return product;
    }
}
