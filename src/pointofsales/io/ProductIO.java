/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pointofsales.io;

import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import pointofsales.model.Product;
import static pointofsales.util.PrintWriter.*;

/**
 * IO class for product list
 * @author patzj
 */
public class ProductIO {
    ArrayList<Product> productList = new ArrayList<>();
    
    /**
     * Read item from text file
     */
    public void readProductList() {
        File fp;
        FileReader fr;
        BufferedReader br;
        String record;
        String[] fields;
        Product product;
        
        try {
            // fp = new File("/productlist.txt");
            fp = new File(System.getProperty("user.dir") + "/productlist.txt");
            fr = new FileReader(fp);
            br = new BufferedReader(fr);
            
            // read each line of the text file
            while((record = br.readLine()) != null) {
                fields = record.split("\t");
                product = new Product();
                product.setId(Integer.parseInt(fields[0]));
                product.setName(fields[1]);
                product.setQuantity(Integer.parseInt(fields[2]));
                product.setPrice(Double.parseDouble(fields[3]));
                productList.add(product);
            }
            
            br.close();
        } catch(FileNotFoundException e) {
            println(e.getMessage());
        } catch(IOException e) {
            println(e.getMessage());
        }
    }
    
    /**
     * Write item to text file
     */
    public void writeProductList() {
        File fp;
        FileWriter fw;
        BufferedWriter bw;
        Iterator iterator;
        Product product;
        String record;
        
        try {
            // fp = new File("/productlist.txt");
            fp = new File(System.getProperty("user.dir") + "/productlist.txt");
            fw = new FileWriter(fp);
            bw = new BufferedWriter(fw);
            iterator = productList.iterator();
            
            // write single record per line of the text file
            while(iterator.hasNext()) {
                product = (Product) iterator.next();
                record = product.getId() + "\t"
                        + product.getName() + "\t"
                        + product.getQuantity() + "\t"
                        + product.getPrice();
                bw.write(record, 0, record.length());
                bw.newLine();
            }
            
            bw.flush();
            bw.close();
        } catch(IOException e) {
            println(e.getMessage());
        } 
    }
    
    /**
     * Add product to the list to be written to the text file
     * @param product to be added to the list
     */
    public void addProduct(Product product) {
        productList.add(product);
    }
    
    /**
     * Set product to the list to be written to the text file
     * @param index of item be updated
     * @param product that will replace product from specified index
     */
    public void setProduct(int index, Product product) {
        productList.set(index, product);
    }
    
    /**
     * Remove product from the list to be written to the text file
     * @param product to be removed from the list
     */
    public void removeProduct(Product product) {
        productList.remove(product);
    }
    
    /**
     * Get list from text file
     * @return list of Product objects from text file
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }
}
