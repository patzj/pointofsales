/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pointofsales.model;

import java.io.Serializable;

/**
 *
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
    
    public void setId(int id) {
        if(id == 0) { id = 1; }
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setPrice(double price) {
        this.price = (Math.round(price * 100.0)) / 100.0;
    }
    
    public double getPrice() {
        return price;
    }
}
