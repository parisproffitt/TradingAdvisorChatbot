
/**
 * This program simulates a virtual stock trading environment where users can buy and sell
 * shares of different companies, track their portfolio, and receive basic investment advice.
 * It is designed to demonstrate key programming concepts such as arrays, loops, methods,
 * conditional logic, and calculations, while also simulating realistic financial decision-making.
 *
 * @author Paris Proffitt
 * @version August 21st, 2025
 */

import java.util.Scanner;
import java.util.Random;
public class TradingAdvisor {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        String[] stocks = {"TechCorp", "HealthInc", "EnergyCo", "AutoMakers"};
        double[] prices = new double[stocks.length];
        int[] shares = new int[stocks.length];
        double cash = 10000; // starting cash

        // Initialize random stock prices
        for (int i = 0; i < prices.length; i++) {
            prices[i] = 50 + rand.nextDouble() * 150; // $50 - $200
        }

        System.out.println("Welcome to Finance Advisor Bot!");
        boolean running = true;

        while (running) {
            System.out.println("\nCash available: $" + String.format("%.2f", cash));
            System.out.println("Your stock portfolio:");
            for (int i = 0; i < stocks.length; i++) {
                System.out.printf("%s: %d shares @ $%.2f each\n", stocks[i], shares[i], prices[i]);
            }

            System.out.println("\nOptions:");
            System.out.println("1. View stock prices");
            System.out.println("2. Buy stocks");
            System.out.println("3. Sell stocks");
            System.out.println("4. Get investment advice");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    displayPrices(stocks, prices);
                    break;
                case 2:
                    cash = buyStocks(input, stocks, prices, shares, cash);
                    break;
                case 3:
                    cash = sellStocks(input, stocks, prices, shares, cash);
                    break;
                case 4:
                    giveAdvice(stocks, prices, shares, cash);
                    break;
                case 5:
                    running = false;
                    System.out.println("Thanks for using Finance Advisor Bot! Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1-5.");
            }

            // Update stock prices randomly each round
            for (int i = 0; i < prices.length; i++) {
                prices[i] *= 0.95 + rand.nextDouble() * 0.1; // fluctuate ±5%
            }
        }

        input.close();
    }

    public static void displayPrices(String[] stocks, double[] prices) {
        System.out.println("\nCurrent Stock Prices:");
        for (int i = 0; i < stocks.length; i++) {
            System.out.printf("%s: $%.2f\n", stocks[i], prices[i]);
        }
    }

    public static double buyStocks(Scanner input, String[] stocks, double[] prices, int[] shares, double cash) {
        System.out.println("\nWhich stock do you want to buy?");
        for (int i = 0; i < stocks.length; i++) {
            System.out.printf("%d. %s ($%.2f)\n", i + 1, stocks[i], prices[i]);
        }
        int choice = input.nextInt() - 1;

        if (choice < 0 || choice >= stocks.length) {
            System.out.println("Invalid stock selection.");
            return cash;
        }

        System.out.println("How many shares do you want to buy?");
        int amount = input.nextInt();

        double cost = prices[choice] * amount;
        if (cost <= cash) {
            cash -= cost;
            shares[choice] += amount;
            System.out.printf("Bought %d shares of %s for $%.2f\n", amount, stocks[choice], cost);
        } else {
            System.out.println("Insufficient funds.");
        }

        return cash;
    }

    public static double sellStocks(Scanner input, String[] stocks, double[] prices, int[] shares, double cash) {
        System.out.println("\nWhich stock do you want to sell?");
        for (int i = 0; i < stocks.length; i++) {
            System.out.printf("%d. %s (%d shares)\n", i + 1, stocks[i], shares[i]);
        }
        int choice = input.nextInt() - 1;

        if (choice < 0 || choice >= stocks.length) {
            System.out.println("Invalid stock selection.");
            return cash;
        }

        System.out.println("How many shares do you want to sell?");
        int amount = input.nextInt();

        if (amount <= shares[choice]) {
            double revenue = prices[choice] * amount;
            shares[choice] -= amount;
            cash += revenue;
            System.out.printf("Sold %d shares of %s for $%.2f\n", amount, stocks[choice], revenue);
        } else {
            System.out.println("You don't own that many shares.");
        }

        return cash;
    }

    public static void giveAdvice(String[] stocks, double[] prices, int[] shares, double cash) {
        System.out.println("\nInvestment Advice:");
        int maxIndex = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[maxIndex]) maxIndex = i;
        }
        System.out.printf("Consider buying shares of %s, it has a strong price trend.\n", stocks[maxIndex]);

        for (int i = 0; i < shares.length; i++) {
            if (shares[i] > 0 && prices[i] < 100) {
                System.out.printf("Consider selling some shares of %s to lock in profits.\n", stocks[i]);
            }
        }

        System.out.printf("You have $%.2f in cash; consider diversifying your portfolio.\n", cash);
    }
}