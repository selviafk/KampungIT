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
public class Deposit extends Transaction
{
    private double amount;
    private Keypad keypad;
    private DepositSlot depositSlot;
    private final static int CANCELED = 0;
   
    public Deposit(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, DepositSlot atmDepositSlot)
    {
        super (userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    }
   
    @Override
    public void execute()
    {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        amount = promptForDepositAmount();
       
        if (amount != CANCELED)
        {
            screen.displayMessage("\nSilahkan masukan isi dari envelope deposito anda  ");
            screen.displayRupiahAmount(amount);
            screen.displayMessageLine(".");
           
            boolean envelopeReceived = depositSlot.isEnvelopeReceived();
           
            if (envelopeReceived)
            {
                screen.displayMessageLine("\nEnvelope anda telah " + "diterima.\nNOTE: Uang yang didepositokan tidak akan " + "dapat digunakan sampai kita memverifikasi jumlah dari " + "uang anda.");
                bankDatabase.credit(getAccountNumber(), amount);
            }
            else
            {
                screen.displayMessageLine("\nAnda tidak memasukkan " + "envelope, sehingga ATM membatakan transaksi anda.");
            }
        }
    }
   
    private double promptForDepositAmount()
    {
        Screen screen = getScreen();
        screen.displayMessage("\nSilahkan masukan jumlah deposit anda dalam " + "RUPIAH (atau 0 untuk membatalkan): ");
        int input = keypad.getInput();
       
        if (input == CANCELED)
        return CANCELED;
        else
        {
            return (double) input;
        }
    }
}