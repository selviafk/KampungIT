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
public class Withdrawal extends Transaction //inheritance
{
    private int amount;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private final static int CANCELED = 6;
   
    public Withdrawal(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, CashDispenser atmCashDispenser)
    {
        super(userAccountNumber, atmScreen, atmBankDatabase);
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    }
   
    @Override
    public void execute()
    {
        boolean cashDispensed = false;
        double availableBalance;
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
       
        do
        {
            amount = displayMenuOfAmounts();
            if(amount != CANCELED)
            {
                availableBalance = bankDatabase.getAvailableBalance(getAccountNumber());
                if(amount <= availableBalance)
                {
                    if(cashDispenser.isSufficientCashAvailable(amount))
                    {
                        bankDatabase.debit(getAccountNumber(), amount);
                        cashDispenser.dispenseCash(amount);
                        cashDispensed = true;
                        screen.displayMessageLine("\nUang anda telah dikeluarkan. Silahkan ambil sekarang.");
                    }
                    else
                    {
                        screen.displayMessageLine("\nUang tunai didalam ATM tidak mencukupi.");
                        screen.displayMessageLine("\nSilahkan pilih nilai yang lebih kecil.");
                    }
                }
                else
                {
                    screen.displayMessageLine("\nSaldo anda tidak mencukupi.");
                    screen.displayMessageLine("\nSilahkan pilih nilai yang lebih kecil.");
                }
            }
            else
            {
                screen.displayMessageLine("\nMembatalkan transaksi...");
                return;
            }
        }
        while(!cashDispensed);
    }
   
    private int displayMenuOfAmounts()
    {
        int userChoice = 0;
        Screen screen = getScreen();
       
        int[] amounts = {0, 250000, 500000, 750000, 1000000, 1500000};
        while(userChoice == 0)
        {
            screen.displayMessageLine("\nMenu Penarikan : ");
            screen.displayMessageLine("1. Rp250.000");
            screen.displayMessageLine("2. Rp500.000");
            screen.displayMessageLine("3. Rp750.000");
            screen.displayMessageLine("4. Rp1.000.000");
            screen.displayMessageLine("5. Rp1.500.000");
            screen.displayMessageLine("6. Membatalkan transaksi");
            screen.displayMessage("\nSilahkan pilih jumlah penarikan yang diinginkan: ");
            int input = keypad.getInput();
           
            switch(input)
            {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    userChoice = amounts[input];
                    break;
                case CANCELED :
                    userChoice = CANCELED;
                    break;
                default :
                    screen.displayMessageLine("\nPilihan tidak valid.");
                    screen.displayMessageLine("Silahkan coba lagi.");
            }  
        }
        return userChoice;  
    }
}
