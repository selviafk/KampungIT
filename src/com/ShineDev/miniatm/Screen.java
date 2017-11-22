/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniatm;

/**
 *
 * @author ASUS
 */
public class Screen  
{
    public void displayMessage(String message)
    {
        System.out.print(message);
    }
   
    public void displayMessageLine(String message)
    {
        System.out.println(message);
    }
   
    public void displayRupiahAmount(double amount)
    {
        System.out.printf("Rp%,.2f", amount);
    }
}
