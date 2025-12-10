import java.util.*;

public class Main {

    // ----- Stock Class -----
    static class Stock {
        private String symbol;
        private String name;
        private double price;

        public Stock(String symbol, String name, double price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }

        public String getSymbol() { return symbol; }
        public String getName() { return name; }
        public double getPrice() { return price; }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return symbol + " - " + name + " : ₹" + String.format("%.2f", price);
        }
    }

    // ---- Portfolio Holding ----
    static class Holding {
        private Stock stock;
        private int quantity;

        public Holding(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }

        public Stock getStock() { return stock; }
        public int getQuantity() { return quantity; }

        public void addQuantity(int qty) { quantity += qty; }
        public void removeQuantity(int qty) { quantity -= qty; }

        public double getTotalValue() {
            return stock.getPrice() * quantity;
        }
    }

    // ----- Portfolio Class -----
    static class Portfolio {
        private ArrayList<Holding> holdings = new ArrayList<>();
        private double balance;

        public Portfolio(double balance) {
            this.balance = balance;
        }

        public double getBalance() {
            return balance;
        }

        public void buyStock(Stock stock, int qty) {
            double cost = stock.getPrice() * qty;
            if (cost > balance) {
                System.out.println("❌ Not enough balance to buy.");
                return;
            }
            balance -= cost;

            for (Holding h : holdings) {
                if (h.getStock().getSymbol().equals(stock.getSymbol())) {
                    h.addQuantity(qty);
                    System.out.println("Bought " + qty + " shares of " + stock.getSymbol());
                    return;
                }
            }
            holdings.add(new Holding(stock, qty));
            System.out.println("Bought " + qty + " shares of " + stock.getSymbol());
        }

        public void sellStock(Stock stock, int qty) {
            for (Holding h : holdings) {
                if (h.getStock().getSymbol().equals(stock.getSymbol())) {
                    if (h.getQuantity() < qty) {
                        System.out.println("❌ Not enough shares.");
                        return;
                    }
                    balance += stock.getPrice() * qty;
                    h.removeQuantity(qty);
                    System.out.println("Sold " + qty + " shares of " + stock.getSymbol());
                    if (h.getQuantity() == 0) holdings.remove(h);
                    return;
                }
            }
            System.out.println("❌ You don't own this stock.");
        }

        public void displayPortfolio() {
            System.out.println("\n----- Portfolio -----");
            if (holdings.isEmpty()) {
                System.out.println("No holdings yet.");
            } else {
                double total = 0;
                for (Holding h : holdings) {
                    double value = h.getTotalValue();
                    total += value;
                    System.out.println(h.getStock().getSymbol() +
                            " | Qty: " + h.getQuantity() +
                            " | Current Price: ₹" + h.getStock().getPrice() +
                            " | Value: ₹" + value);
                }
                System.out.println("Total Portfolio Value: ₹" + total);
            }
            System.out.println("Cash Balance: ₹" + balance);
            System.out.println("---------------------");
        }
    }

    // ----- Main Application -----
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Stock> market = new ArrayList<>();
        market.add(new Stock("TCS", "Tata Consultancy Services", 3800.0));
        market.add(new Stock("INFY", "Infosys", 1600.0));
        market.add(new Stock("RELI", "Reliance Industries", 2550.0));
        market.add(new Stock("HDFC", "HDFC Bank", 1550.0));

        System.out.print("Enter initial deposit amount (₹): ");
        double balance = sc.nextDouble();
        Portfolio portfolio = new Portfolio(balance);

        while (true) {
            System.out.println("\n===== STOCK TRADING MENU =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n----- Market Stocks -----");
                    for (int i = 0; i < market.size(); i++) {
                        System.out.println((i + 1) + ". " + market.get(i));
                    }
                    break;

                case 2:
                    System.out.print("Enter Stock Number to BUY: ");
                    int b = sc.nextInt() - 1;
                    System.out.print("Enter Quantity: ");
                    int qtyBuy = sc.nextInt();
                    portfolio.buyStock(market.get(b), qtyBuy);
                    break;

                case 3:
                    System.out.print("Enter Stock Number to SELL: ");
                    int s = sc.nextInt() - 1;
                    System.out.print("Enter Quantity: ");
                    int qtySell = sc.nextInt();
                    portfolio.sellStock(market.get(s), qtySell);
                    break;

                case 4:
                    portfolio.displayPortfolio();
                    break;

                case 5:
                    System.out.println("Thank you for trading!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
