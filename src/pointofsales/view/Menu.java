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
 * This class contains all of the main functions of the application
 * @author patzj
 */
public class Menu {
    /**
     * Home menu
     */
    public static void displayHomeMenu() {
        Scanner input = new Scanner(System.in);
        ArrayList<Product> purchaseList = new ArrayList<>();
        int choice = 0;
        
        // Interface
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
            choice = Integer.parseInt(input.nextLine());
            
            if(choice < 1 || choice > 5) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            // redisplay home menu
            println("Invalid input.");
            displayHomeMenu();
        }
        
        // next menu selection
        switch (choice) {
            case 1:
                displayAddMenu();
                break;
            case 2:
                displayUpdateMenu();
                break;
            case 3:
                displayDeleteMenu();
                break;
            case 4:
                displayPurchaseMenu(purchaseList);
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
            choice = Integer.parseInt(input.nextLine());
            
            if(choice < 1 || choice > 2) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            // redisplay add menu
            println("Invalid input.");
            displayAddMenu();
        }
        
        // next menu selection
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
        
        // loop thru products
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
        
        // prompt interface
        try {
            drawHorizontalBrokenLines(28);
            print("Enter ID:\t");
            id = Integer.parseInt(input.nextLine());
            if(id < 1000) {
                throw new NumberFormatException();
            }
            
            print("Enter Name:\t");
            name = input.nextLine();
            
            print("Enter Qty:\t");
            quantity = Integer.parseInt(input.nextLine());
            if(quantity < 0) {
                throw new NumberFormatException();
            }
            
            print("Enter Price:\t");
            price = Double.parseDouble(input.nextLine());
            if(price <= 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            println("Invalid input. Product not added.");
            print("Continue adding product? y/n:\t");
            onErrChoice = input.nextLine();
            
            // prompt for invalid input
            if(onErrChoice.equalsIgnoreCase("y") || 
                    onErrChoice.equalsIgnoreCase("yes")) {
                doAddProduct();
            } else {
                displayAddMenu();
            }
        }
        
        // set Product object
        product = new Product();
        product.setId(id);
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        
        // initialize and save product list
        io = new ProductIO();
        io.readProductList();
        io.addProduct(product);
        io.writeProductList();
        
        drawHorizontalBrokenLines(28);
        println("Successfully added new product.");
        drawHorizontalBrokenLines(28);
        displayAddMenu();
    }
    
    /**
     * Update menu
     */
    public static void displayUpdateMenu() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        
        // interface
        drawHorizontalBrokenLines(28);
        println("|       Update Menu        |");
        drawHorizontalBrokenLines(28);
        println("|       Product List       |");
        drawHorizontalBrokenLines(28);
        viewProductList();
        println("| 0 - Back                 |");
        drawHorizontalBrokenLines(28);
        print("Enter product ID to update: ");
        
        try {
            choice = Integer.parseInt(input.nextLine());

            if(choice < 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            // redisplay add menu
            println("Invalid input.");
            displayUpdateMenu();
        }
        
        // next menu selection
        if(choice == 0) {
            displayHomeMenu();
        } else {
            doUpdateProduct(choice);
        }        
    }
    
    /**
     * Product validity checker and product list updater
     * @param id 
     */
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
        
        // edit product
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
    
    /**
     * Input prompt for setting new product properties
     * @param product
     * @return updated product for product list update
     */
    public static Product updateProductForm(Product product) {
        Scanner input = new Scanner(System.in);
        String name;
        int quantity;
        double price;     

        // prompt interface
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
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            quantity = product.getQuantity();
        }
        
        try {
            print("Enter New Price: ");
            price = Double.parseDouble(input.nextLine());
            
            if(price < 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            price = product.getPrice();
        }
        
        // set new product properties
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        
        return product;
    }
    
    /**
     * Delete menu
     */
    public static void displayDeleteMenu() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        
        // interface
        drawHorizontalBrokenLines(28);
        println("|       Delete Menu        |");
        drawHorizontalBrokenLines(28);
        println("|       Product List       |");
        drawHorizontalBrokenLines(28);
        viewProductList();
        println("| 0 - Back                 |");
        drawHorizontalBrokenLines(28);
        print("Enter product ID to delete: ");
        
        try {
            choice = Integer.parseInt(input.nextLine());

            if(choice < 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            // redisplay add menu
            println("Invalid input.");
            displayUpdateMenu();
        }
        
        // next menu selection
        if(choice == 0) {
            displayHomeMenu();
        } else {
            doDeleteProduct(choice);
        }         
    }
    
    /**
     * Delete product from the product list
     * @param id 
     */
    public static void doDeleteProduct(int id) {
        Scanner input = new Scanner(System.in);
        ProductIO io = new ProductIO();
        Product temp;
        Product product = null;
        ArrayList<Product> productList;
        Iterator iterator;
        String choice;
        
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
        
        // remove product
        if(product != null) {
            print("Delete product " + id + "? y/n: ");
            choice = input.nextLine();
            if(choice.equalsIgnoreCase("y") || 
                    choice.equalsIgnoreCase("yes")) {
                io.removeProduct(product);
                io.writeProductList();
                drawHorizontalBrokenLines(28);
                println("Successfully deleted product.");
                displayDeleteMenu();
            } else {
                displayDeleteMenu();
            }
        } else {
            println("Invalid id.");
            displayDeleteMenu();
        }
    }
    
    /**
     * Purchase menu
     * @param purchaseList list of items on cart
     */
    public static void displayPurchaseMenu(ArrayList<Product> purchaseList) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        int quantity = 0;
        
        // interface
        drawHorizontalBrokenLines(28);
        println("|      Purchase Menu       |");
        drawHorizontalBrokenLines(28);
        println("|       Product List       |");
        drawHorizontalBrokenLines(28);
        viewPurchaseProductList();
        println("| 1 - View Cart            |");
        println("| 2 - Cash Out             |");
        println("| 3 - Cancel               |");
        drawHorizontalBrokenLines(28);
        print("Enter product ID to purchase: ");
        
        try {
            choice = Integer.parseInt(input.nextLine());
            
            if(choice < 1) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            println("Invalid input.");
            displayPurchaseMenu(purchaseList);
        }
        
        // next menu selection
        switch(choice) {
            case 1:
                viewCart(purchaseList);
                break;
            case 2:
                cashOut(purchaseList);
                break;
            case 3:
                displayHomeMenu();
                break;
            default:
                // set id and quantity of product to be added to cart
                print("Enter quantity: ");
                
                try {
                    quantity = Integer.parseInt(input.nextLine());
                    
                    if(quantity < 1) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    println("Invalid quantity.");
                    displayPurchaseMenu(purchaseList);
                }
                
                addToCart(purchaseList, choice, quantity);
        }
    }
    
    /**
     * List of products for purchase menu
     */
    public static void viewPurchaseProductList() {
        ProductIO io = new ProductIO();
        Product product;
        ArrayList<Product> productList;
        Iterator iterator;
        
        io.readProductList();
        productList = io.getProductList();
        iterator = productList.iterator();
        
        // loop thru product list
        while(iterator.hasNext()) {
            product = (Product) iterator.next();
            println("" + product.getId());
            print(product.getName() + " : ");
            println("price: " + product.getPrice());
            drawHorizontalBrokenLines(28);
        }
    }
    
    /**
     * Add product to cart
     * @param purchaseList list of items on cart
     * @param id of item to be added to cart
     * @param quantity  of item to be added cart
     */
    public static void addToCart(ArrayList<Product> purchaseList, 
            int id, int quantity) {
        ProductIO io = new ProductIO();
        Product temp;
        Product product = null;
        ArrayList<Product> productList;
        Iterator iterator;
        
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
        
        // add product
        if(product != null) {
            if(product.getQuantity() >= quantity && 
                    quantity > 0) {
                product.setQuantity(quantity);
                purchaseList.add(product);
                displayPurchaseMenu(purchaseList);
            } else {
                println("Invalid quantity.");
                displayPurchaseMenu(purchaseList);
            }
        } else {
            println("Invalid id.");
            displayPurchaseMenu(purchaseList);
        }        
    }
    
    /**
     * View items added to the cart
     * @param purchaseList list of items on cart
     */
    public static void viewCart(ArrayList<Product> purchaseList) {
        Scanner input = new Scanner(System.in);
        Product product;
        double subTotal;
        double total = 0;
        Iterator iterator;
        
        iterator = purchaseList.iterator();
        drawHorizontalBrokenLines(28);
        println("Cart");
        drawHorizontalBrokenLines(28);
        
        // loop thru purchase list
        while(iterator.hasNext()) {
            product = (Product) iterator.next();
            subTotal = product.getPrice() * product.getQuantity();
            print(product.getName() + " : " + product.getPrice());
            println(" * " + product.getQuantity() + " = "
                    + subTotal);
            total += subTotal;
        }
        
        drawHorizontalBrokenLines(28);
        println("Total: " + total);
        drawHorizontalBrokenLines(28);
        print("Press Enter to go back: ");
        input.nextLine();
        displayPurchaseMenu(purchaseList);
    }
    
    /**
     * Cash out menu
     * @param purchaseList 
     */
    public static void cashOut(ArrayList<Product> purchaseList) {
        Scanner input = new Scanner(System.in);
        ProductIO io = new ProductIO();
        ArrayList<Product> productList = new ArrayList<>();
        Product product;
        Product purchase;
        double subTotal;
        double total = 0;
        double bill = 0;
        double change;
        String choice;
        Iterator purchaseIterator;
        Iterator productIterator;
        
        purchaseIterator = purchaseList.iterator();
        drawHorizontalBrokenLines(28);
        println("Cash Out");
        drawHorizontalBrokenLines(28);
        
        // loop thru purchase list
        while(purchaseIterator.hasNext()) {
            
            product = (Product) purchaseIterator.next();
            subTotal = product.getPrice() * product.getQuantity();
            total += subTotal;
        }
        
        // bill prompt
        println("Total: " + total);
        print("Enter bill: ");
        
        try {
            bill = Double.parseDouble(input.nextLine());
            
            if(bill < 0 || bill < total) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            println("Invalid bill.");
            cashOut(purchaseList);
        }
        
        // purchase prompt
        print("Continue purchase? y/n: ");
        choice = input.nextLine();
        
        if(choice.equalsIgnoreCase("y") || 
                choice.equalsIgnoreCase("yes")) {
            // get inventory for update
            io.readProductList();
            productList = io.getProductList();
            productIterator = productList.iterator();            
            
            // set inventory
            while(productIterator.hasNext()) {
                product = (Product) productIterator.next();
                purchaseIterator = purchaseList.iterator();
                while(purchaseIterator.hasNext()) {
                    purchase = (Product) purchaseIterator.next();
                    if(product.getId() == purchase.getId()) {
                        product.setQuantity(product.getQuantity() - 
                                purchase.getQuantity());
                    }
                }
            }
            
            // update inventory
            io.writeProductList();
            
            // bill computation
            change = bill - total;
            println("Purchase successfull.");
            println("Change: " + change);
            println("Press Enter to continue: ");
            input.nextLine();
            displayHomeMenu();
        } else {
            displayPurchaseMenu(purchaseList);
        }
    }
}
