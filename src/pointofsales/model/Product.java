/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pointofsales.model;

import java.io.Serializable;

/**
 * JavaBean for product
 * @author patzj
 */
public class Product implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private double price;
    
    public Product() {
        id = 0;
        name = "";
        quantity = 0;
        price = 0.0;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(int id) {
        if(id == 0) { id = 1; }
        this.id = id;
    }
    
    /**
     * 
     * @return 
     */
    public int getId() {
        return id;
    }
    
    /**
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param quantity 
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * 
     * @return 
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * 
     * @param price 
     */
    public void setPrice(double price) {
        this.price = (Math.round(price * 100.0)) / 100.0;
    }
    
    /**
     * 
     * @return 
     */
    public double getPrice() {
        return price;
    }
}
