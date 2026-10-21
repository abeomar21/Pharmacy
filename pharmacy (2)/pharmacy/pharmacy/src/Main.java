import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MedicalSupply ms1 = new MedicalSupply("bandage", "ezaby", "big", 20, 50, "white");
        MedicalSupply ms2 = new MedicalSupply("grob", "seif", "bigss", 200, 150, "black");
        MedicalSupply ms3 = new MedicalSupply("badsdsdndage", "ezabdsdsy", "bsdsdig", 120, 550, "sddsf");
        PharmacyManagementSystem p = new PharmacyManagementSystem();
        Customer customer = new Customer();
        p.addItem(ms1);
        p.addItem(ms2);
        p.addItem(ms3);

        while (true) {
            int userInput;
            System.out.println("Welcome to our Pharmacy");//HOME PAGE
            Scanner scanner = new Scanner(System.in);
            userInput = takeInputInt("Please specify your role", "Admin", "Customer");
            //Admin Page
            if (userInput == 1) {
                while (true) {
//                p.viewInventory();
                    //Admin Page
                    //Add item - All 5 options from tablet-syringe.
                    userInput = takeInputInt("Please specify the action you would like to perform", "Add Item", "Remove Item", "Update Item", "View Item by Name", "View Inventory");
                    if (userInput == 1) {
                        userInput = takeInputInt("Specify the type of Item:", "Tablet", "Liquid", "Injection", "Bandages", "Syringe");
                        if (userInput == 1) {
                            Tablet tablet = new Tablet();
                            takeInputTablet(tablet);
                            p.addItem(tablet);
                            p.viewInventory();
                        } else if (userInput == 2) {
                            Liquid liquid = new Liquid();
                            takeInputLiquid(liquid);
                            p.addItem(liquid);
                            p.viewInventory();
                        } else if (userInput == 3) {
                            Injection injection = new Injection();
                            takeInputInjection(injection);
                            p.addItem(injection);
                            p.viewInventory();
                        } else if (userInput == 4) {
                            Bandages bandages = new Bandages();
                            takeInputBandages(bandages);
                            p.addItem(bandages);
                            p.viewInventory();
                        } else if (userInput == 5) {
                            Syringe syringe = new Syringe();
                            ;
                            takeInputSyringe(syringe);
                            p.addItem(syringe);
                            p.viewInventory();
                        }
                    }
                    //Remove item by name
                    else if (userInput == 2) {
                        System.out.println("Please enter item name to be removed: ");
                        while (true) {
                            try {
                                String name = scanner.nextLine();
                                if (p.getItemByName(name) == null) {
                                    throw new NullPointerException();
                                }
                                p.removeItem(p.getItemByName(name));
                                break;

                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid name.");
                            } catch (NullPointerException e) {
                                System.out.println("Item doesnt exist. Please enter a valid name.");
                            }
                        }
                    }
                    //Update item
                    else if (userInput == 3) {
                        System.out.println("Please enter item name to be updated:");
                        String nname;
                        while (true) {
                            try {
                                String name = scanner.nextLine();
                                nname = name;
                                if (p.getItemByName(name) == null) {
                                    throw new NullPointerException();
                                }
                                p.getItemByName(name).displayInfo();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid name.");

                            } catch (NullPointerException e) {
                                System.out.println("Item doesnt exist. Please enter a valid name.");
                            }
                        }
                        String Type = p.getItemByName(nname).getClass().getSimpleName();
                        if (Type == "Tablet") {
                            Tablet pi = (Tablet) p.getItemByName(nname);
                            while (true) {
                                userInput = takeInputInt("Please specify the property you would like to Update", "Name", "Manufacturer", "Description", "Quantity", "Price", "Dosage", "Expiration Date", "Requires Prescription", "Concentration", "Count");
                                switch (userInput) {
                                    case 1:
                                        takeName(pi);
                                        break;
                                    case 2:
                                        takeManufacturer(pi);
                                        break;
                                    case 3:
                                        takeDescription(pi);
                                        break;
                                    case 4:
                                        takeQuantity(pi);
                                        break;
                                    case 5:
                                        takePrice(pi);
                                        break;
                                    case 6:
                                        takeDosage(pi);
                                        break;
                                    case 7:
                                        takeDate(pi);
                                        break;
                                    case 8:
                                        takeRequiresPrescription(pi);
                                        break;
                                    case 9:
                                        takeConcentration(pi);
                                        break;
                                    case 10:
                                        takeCount(pi);
                                        break;
                                    case 11:
                                        break;
                                    default:
                                        break;

                                }
                                if (userInput == 11)
                                    break;
                            }
                        } else if (Type == "Liquid") {
                            Liquid pi = (Liquid) p.getItemByName(nname);
                            while (true) {
                                userInput = takeInputInt("Please specify the property you would like to Update", "Name", "Manufacturer", "Description", "Quantity", "Price", "Dosage", "Expiration Date", "Requires Prescription", "Volume");
                                switch (userInput) {
                                    case 1:
                                        takeName(pi);
                                        break;
                                    case 2:
                                        takeManufacturer(pi);
                                        break;
                                    case 3:
                                        takeDescription(pi);
                                        break;
                                    case 4:
                                        takeQuantity(pi);
                                        break;
                                    case 5:
                                        takePrice(pi);
                                        break;
                                    case 6:
                                        takeDosage(pi);
                                        break;
                                    case 7:
                                        takeDate(pi);
                                        break;
                                    case 8:
                                        takeRequiresPrescription(pi);
                                        break;
                                    case 9:
                                        takeVolume(pi);
                                        break;
                                    case 10:
                                        break;
                                    default:
                                        break;
                                }

                                if (userInput == 10)
                                    break;
                            }
                        } else if (Type == "Injection") {
                            Injection pi = (Injection) p.getItemByName(nname);
                            while (true) {
                                userInput = takeInputInt("Please specify the property you would like to Update", "Name", "Manufacturer", "Description", "Quantity", "Price", "Dosage", "Expiration Date", "Requires Prescription", "Volume per Dosage", "Needle Gauge");
                                switch (userInput) {
                                    case 1:
                                        takeName(pi);
                                        break;
                                    case 2:
                                        takeManufacturer(pi);
                                        break;
                                    case 3:
                                        takeDescription(pi);
                                        break;
                                    case 4:
                                        takeQuantity(pi);
                                        break;
                                    case 5:
                                        takePrice(pi);
                                        break;
                                    case 6:
                                        takeDosage(pi);
                                        break;
                                    case 7:
                                        takeDate(pi);
                                        break;
                                    case 8:
                                        takeRequiresPrescription(pi);
                                        break;
                                    case 9:
                                        takeVolumePerDose(pi);
                                        break;
                                    case 10:
                                        takeNeedleGauge(pi);
                                        break;
                                    case 11:
                                        break;
                                    default:
                                        break;
                                }
                                if (userInput == 11)
                                    break;
                            }
                        } else if (Type == "Bandages") {
                            Bandages pi = (Bandages) p.getItemByName(nname);
                            while (true) {
                                userInput = takeInputInt("Please specify the property you would like to Update", "Name", "Manufacturer", "Description", "Quantity", "Price", "Category", "Size", "Material", "Quantity per Pack");
                                switch (userInput) {
                                    case 1:
                                        takeName(pi);
                                        break;
                                    case 2:
                                        takeManufacturer(pi);
                                        break;
                                    case 3:
                                        takeDescription(pi);
                                        break;
                                    case 4:
                                        takeQuantity(pi);
                                        break;
                                    case 5:
                                        takePrice(pi);
                                        break;
                                    case 6:
                                        takeCategory(pi);
                                        break;
                                    case 7:
                                        takeSize(pi);
                                        break;
                                    case 8:
                                        takeMaterial(pi);
                                        break;
                                    case 9:
                                        takeQuantityPerPack(pi);
                                        break;
                                    case 10:
                                        break;
                                    default:
                                        break;
                                }

                                if (userInput == 10)
                                    break;
                            }
                        } else if (Type == "Syringe") {
                            Syringe pi = (Syringe) p.getItemByName(nname);
                            while (true) {
                                userInput = takeInputInt("Please specify the property you would like to Update", "Name", "Manufacturer", "Description", "Quantity", "Price", "Category", "Needle Gauge", "Capacity");
                                switch (userInput) {
                                    case 1:
                                        takeName(pi);
                                        break;
                                    case 2:
                                        takeManufacturer(pi);
                                        break;
                                    case 3:
                                        takeDescription(pi);
                                        break;
                                    case 4:
                                        takeQuantity(pi);
                                        break;
                                    case 5:
                                        takePrice(pi);
                                        break;
                                    case 6:
                                        takeCategory(pi);
                                        break;
                                    case 7:
                                        takeNeedleGauge(pi);
                                        break;
                                    case 8:
                                        takeCapacity(pi);
                                        break;
                                    case 9:
                                        break;
                                    default:
                                        break;
                                }

                                if (userInput == 9)
                                    break;
                            }
                        }
                    }
                    //View Item by name
                    else if (userInput == 4) {
                        System.out.println("Please enter item name to be viewed: ");
                        while (true) {
                            try {
                                String name = scanner.nextLine();
                                if (p.getItemByName(name) == null) {
                                    throw new NullPointerException();
                                }
                                p.getItemByName(name).displayInfo();
                                break;

                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid name.");
                            } catch (NullPointerException e) {
                                System.out.println("Item doesnt exist. Please enter a valid name.");
                            }
                        }

                    }
                    //View Inventory
                    else if (userInput == 5) {
                        if (p.getInventory().isEmpty())
                            System.out.println("There are no items in your inventory.");
                        p.viewInventory();
                    }
                    //Exit - to Home Page
                    else if (userInput == 6) {
                        break;
                    }
                }
            }
            //Customer Page
            else if (userInput == 2) {
                System.out.println("Customer Page ");
                Order order = new Order();
                while (true) {
                    p.viewInventory();
                    userInput = takeInputInt("Please Specify the Action", "Add to Cart", "Remove from Cart", "View Cart", "Checkout");

                    //Add to Cart
                    if(userInput == 1){
                        System.out.println("Please enter item name to be purchased: ");
                        String nname;
                        while (true) {
                            try {
                                String name = scanner.nextLine();
                                nname = name;
                                if (p.getItemByName(name) == null) {
                                    throw new NullPointerException();
                                }
                                p.getItemByName(name).displayInfo();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid name.");

                            } catch (NullPointerException e) {
                                System.out.println("Item doesnt exist. Please enter a valid name.");
                            }
                        }
                        System.out.println("Please enter quantity: ");
                        int qquantity;
                        while (true) {
                            try {
                                int quantity = scanner.nextInt();
                                qquantity = quantity;
                                if (p.getItemByName(nname).checkAvailibility() && p.getItemByName(nname).getQuantity() >= quantity)
                                    break;
                                else
                                    throw new Exception();

                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid quantity.");
                                scanner.next(); // Consume the invalid input from the scanner
                            } catch (Exception e) {
                                System.out.println("Quantity in Stock is not enough. Please enter a valid quantity, Only " + p.getItemByName(nname).getQuantity() + "in stock.");
                            }
                        }
                        if (p.getItemByName(nname).getType() == "Tablet" || p.getItemByName(nname).getType() == "Liquid" || p.getItemByName(nname).getType() == "Injection") {
                            Medication pi = (Medication) p.getItemByName(nname);
                            if (pi.getRequiresPrescription()) {
                                Prescription pres = new Prescription();
                                takePrescription(pres);
                                if (pres.isExpired()) {
                                    System.out.println("Prescription is Expired");
                                } else {
                                    pi.setQuantity(pi.getQuantity() - qquantity);
                                    PharmacyItem item = ((Medication) p.getItemByName(nname)).deepCopy();
                                    item.setQuantity(qquantity);
                                    order.addItem(item);
                                }
                            } else {
                                pi.setQuantity(pi.getQuantity() - qquantity);
                                PharmacyItem item = ((Medication) p.getItemByName(nname)).deepCopy();
                                item.setQuantity(qquantity);
                                order.addItem(item);
                            }
                        } else {

                            MedicalSupply pi = (MedicalSupply) p.getItemByName(nname);
                            pi.setQuantity(pi.getQuantity() - qquantity);
                            PharmacyItem item = ((MedicalSupply) p.getItemByName(nname)).deepCopy();
                            item.setQuantity(qquantity);
                            item.displayInfo();
                            order.addItem(item);
                        }
                    }
                    //Remove from Cart
                    else if (userInput == 2) {
                        if (order.getCart().isEmpty())
                            System.out.println("There are no items in your cart.");
                        else {
                            System.out.println("Cart:");
                            order.viewCart();
                            System.out.println("Please enter item name to be removed:");
                            scanner.nextLine();

                            while (true) {
                                try {
                                    String name = scanner.nextLine();
                                    if (order.getItemByName(name) == null) {
                                        throw new NullPointerException();
                                    }
                                    p.getItemByName(name).setQuantity(p.getItemByName(name).getQuantity() + order.getItemByName(name).getQuantity());
                                    order.removeItem(order.getItemByName(name));
                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a valid name.");
                                } catch (NullPointerException e) {
                                    System.out.println("Item doesnt exist. Please enter a valid name.");
                                }
                            }
                        }
                    }
                    //View Cart
                    else if (userInput == 3) {
                        if(order.getCart().isEmpty())
                            System.out.println("There are no items in your cart.");
                        else {
                            System.out.println("Cart: ");
                            order.viewCart();
                        }
                    }
                    //Proceed to checkout
                    else if (userInput == 4) {
                      takeCustomerName(customer);
                      takeCustomerPhone(customer);
                      takeCustomerAddress(customer);
                      takeCustomerBalance(customer);
                      takePaymentMethod(customer);
                        customer.getPayment().setPaymentDate(new Date());
                        if(customer.checkout(order)) { System.out.println("INVOICE:");
                            customer.getPayment().generateReceipt(order);
                            System.out.println("Items Purchased successfully.");
                            customer.setBalance(customer.getBalance()-order.getTotalAmount());
                            System.out.println("Current Balance: " + customer.getBalance());
                        }
                        else System.out.println("Not enough balance");
                        userInput=takeInputInt("Thanks for your visit","Homepage");
                        if(userInput == 1)break;
                        else {
                            System.out.println("Thanks for using our Application!");
                            return;
                        }
                    }
                    //Exit
                    else if (userInput == 5) {
                        break;
                    }
                }
            }
            //Exit App
            else if (userInput == 3) {
                break;
            }
        }
        System.out.println("Thanks for using our Application!");
    }






    static void takeCustomerName(Customer c) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        while (true) {
            try {
                String name = scanner.nextLine();
                c.setName(name);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid name.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeCustomerAddress(Customer c) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your address : ");
        while (true) {
            try {
                String name = scanner.nextLine();
                c.setAddress(name);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid address.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeCustomerPhone(Customer c) {
        Scanner scanner = new Scanner(System.in);
        String phoneNumber;
while (true) {
    try {
        System.out.print("  Please enter your phone number (11 digits): ");
        phoneNumber = scanner.next();

        // Try parsing to long to simulate numeric-only input
        long num = Long.parseLong(phoneNumber); // Throws NumberFormatException if invalid

        if (phoneNumber.length() != 11) {
            throw new InputMismatchException("Phone number must be exactly 11 digits.");
        }
        c.setPhone(phoneNumber);
        break;
    } catch (NumberFormatException e) {
        System.out.println("Invalid input: phone number must contain only digits.");
    } catch (InputMismatchException e) {
        System.out.println("Invalid phone number: " + e.getMessage());
    }
    System.out.println("Please enter your phone number : ");
        }
    }
    static void takeCustomerBalance(Customer c) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your balance: ");
        while (true) {
            try {
                double price = scanner.nextDouble();
                c.setBalance(price);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid balance.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takePaymentMethod(Customer c) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your payment method (cash or credit): ");
        while (true) {
            try {
                String method = scanner.nextLine();
                if(method.equals("cash")||method.equals("credit"))
                c.getPayment().setPaymentMethod(method);
                else throw new InputMismatchException();
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid payment method.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }


    static void takeName(PharmacyItem item) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter item name: ");
        while (true) {
            try {
                String name = scanner.nextLine();
                item.setName(name);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid name.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeManufacturer(PharmacyItem item) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter manufacturer: ");
        while (true) {
            try {
                String manufacturer = scanner.nextLine();
                item.setManufacturer(manufacturer);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid manufacturer.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeDescription(PharmacyItem item) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Description: ");
        while (true) {
            try {
                String description = scanner.nextLine();
                item.setDescription(description);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid description.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takePrice(PharmacyItem item) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter price: ");
        while (true) {
            try {
                double price = scanner.nextDouble();
                item.setPrice(price);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeQuantity(PharmacyItem item) {//ONLY POSITIVE QUANTITIES - FIX
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter quantity: ");
        while (true) {
            try {
                int quantity = scanner.nextInt();
                item.setQuantity(quantity);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid quantity.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }

    static void takePharmacyItem(PharmacyItem item){
        takeName(item);
        takeManufacturer(item);
        takeDescription(item);
        takePrice(item);
        takeQuantity(item);
    }


    static void takeDosage(Medication m) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter dosage: ");
        while (true) {
            try {
                double dosage = scanner.nextDouble();
                m.setDosage(dosage);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid dosage.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }

    }
    static void takeDate(Medication m) {
        Scanner scanner = new Scanner(System.in);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy"); // Define the expected date format
        System.out.println("Please enter Expiration Date (DD/MM/YY): ");
        while (true) {
            try {
                String dateString = scanner.next(); // Read the date as a String
                Date expirationDate = dateFormat.parse(dateString); // Parse the String to a Date object
                m.setExpirationDate(expirationDate);
                break;

            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in DD/MM/YY format.");
                scanner.nextLine();
            }
        }

    }
    static void takeRequiresPrescription(Medication m) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please specify if  your tablet requires prescription (1:requires, 0:doesnt require): ");
        while (true) {
            try {
                boolean req = scanner.nextBoolean();
                m.setRequiresPrescription(req);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid boolean.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }

    static void takeMedication(Medication m) {
        takePharmacyItem(m);
        takeDosage(m);
        takeDate(m);
        takeRequiresPrescription(m);
    }


    static void takeConcentration(Tablet t) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your concentration: ");
        while (true) {
            try {
                int conc = scanner.nextInt();
                t.setConcentration(conc);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid concentration.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeCount(Tablet t) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter count: ");
        while (true) {
            try {
                int count = scanner.nextInt();
                t.setCount(count);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid count.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }

    static void takeInputTablet(Tablet t) {
        takeMedication(t);
        takeConcentration(t);
        takeCount(t);

    }


    static void takeVolumePerDose(Injection i) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Volume per Dose: ");
        while (true) {
            try {
                double vol = scanner.nextDouble();
                i.setVolumePerDose(vol);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid volume.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeNeedleGauge(Injection i) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Needle Gauge: ");
        while (true) {
            try {
                double needle = scanner.nextDouble();
                i.setNeedleGauge(needle);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Needle Gauge.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }

    static void takeInputInjection(Injection i) {
        takeMedication(i);
        takeVolumePerDose(i);
        takeNeedleGauge(i);
    }


    static void takeVolume(Liquid l) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Volume: ");
        while (true) {
            try {
                double vol = scanner.nextDouble();
                l.setVolume(vol);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid volume.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }

    static void takeInputLiquid(Liquid l) {
        takeMedication(l);
        takeVolume(l);
    }


    static void takeCategory(MedicalSupply m) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter category: ");
        while (true) {
            try {
                String cat = scanner.nextLine();
                m.setCategory(cat);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid category.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }

    }

    static void takeMedicalSupply(MedicalSupply m) {
        takePharmacyItem(m);
        takeCategory(m);
    }


    static void takeSize(Bandages b) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter size: ");
        while (true) {
            try {
                String size = scanner.nextLine();
                b.setSize(size);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid size.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }

    }
    static void takeMaterial(Bandages b) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter material: ");
        while (true) {
            try {
                String mat = scanner.nextLine();
                b.setSize(mat);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid material.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }

    }
    static void takeQuantityPerPack(Bandages b) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Quantity per Pack: ");
        while (true) {
            try {
                int q = scanner.nextInt();
                b.setQuantityPerPack(q);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Quantity.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }


    static void takeInputBandages(Bandages b) {
        takeMedicalSupply(b);
        takeSize(b);
        takeMaterial(b);
        takeQuantityPerPack(b);
    }


    static void takeCapacity(Syringe s) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter capacity: ");
        while (true) {
            try {
                double cap = scanner.nextDouble();
                s.setCapacity(cap);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid capacity.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }
    static void takeNeedleGauge(Syringe s) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Needle Gauge: ");
        while (true) {
            try {
                double needle = scanner.nextDouble();
                s.setNeedleGauge(needle);
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid Needle Gauge.");
                scanner.next(); // Consume the invalid input from the scanner
            }
        }
    }

    static void takeInputSyringe(Syringe s) {
        takeMedicalSupply(s);
        takeCapacity(s);
        takeNeedleGauge(s);
    }

    static void takePrescription(Prescription p) {

            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy"); // Define the expected date format
            System.out.println("Please enter Prescription Date (DD/MM/YY): ");
            while (true) {
                try {
                    String dateString = scanner.next(); // Read the date as a String
                    Date d = dateFormat.parse(dateString); // Parse the String to a Date object
                    p.setDate(d);
                    break;

                } catch (ParseException e) {
                    System.out.println("Invalid date format. Please enter the date in DD/MM/YY format.");
                    scanner.nextLine();
                }
            }

            System.out.println("Please Enter Doctor Name:");
            String docName = scanner.nextLine();
            p.setDoctorName(docName);
        }

    static int takeInputInt(String arg1, String arg2) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2.Exit");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter 1 or 2");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }
    static int takeInputInt(String arg1, String arg2, String arg3) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2." + arg3
                    + "\n3.Exit");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }
    static int takeInputInt(String arg1, String arg2, String arg3, String arg4, String arg5) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2." + arg3
                    + "\n3." + arg4
                    + "\n4." + arg5
                    + "\n5.Back");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 5) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter 1, 2, 3, 4 or 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }
    static int takeInputInt(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2." + arg3
                    + "\n3." + arg4
                    + "\n4." + arg5
                    + "\n5." + arg6
                    + "\n6.Back");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 5 || userInput == 6) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter 1, 2, 3, 4, 5 or 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }
    static int takeInputInt(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6,String arg7, String arg8, String arg9) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2." + arg3
                    + "\n3." + arg4
                    + "\n4." + arg5
                    + "\n5." + arg6
                    + "\n6." + arg7
                    + "\n7." + arg8
                    + "\n8." + arg9
                    + "\n9.Back");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 5 || userInput == 6|| userInput==7 ||userInput== 8 || userInput== 9) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter a number from 1 to 9.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }
    static int takeInputInt(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6,String arg7, String arg8, String arg9, String arg10) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2." + arg3
                    + "\n3." + arg4
                    + "\n4." + arg5
                    + "\n5." + arg6
                    + "\n6." + arg7
                    + "\n7." + arg8
                    + "\n8." + arg9
                    + "\n9." + arg10
                    + "\n10.Back");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 5 || userInput == 6|| userInput==7 ||userInput== 8 || userInput== 9 || userInput== 10 ) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter a number from 1 to 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }

    static int takeInputInt(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6,String arg7, String arg8, String arg9, String arg10,String arg11) {
        int userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(arg1);
            System.out.println("1." + arg2
                    + "\n2." + arg3
                    + "\n3." + arg4
                    + "\n4." + arg5
                    + "\n5." + arg6
                    + "\n6." + arg7
                    + "\n7." + arg8
                    + "\n8." + arg9
                    + "\n9." + arg10
                    + "\n10." + arg11
                    + "\n11.Back");
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2 || userInput == 3 || userInput == 4 || userInput == 5 || userInput == 6|| userInput==7 ||userInput== 8 || userInput== 9 || userInput== 10 || userInput== 11) {
                    System.out.println("You entered: " + userInput);
                    break; // Exit the loop if the input is valid
                } else {
                    throw new IllegalArgumentException("Invalid input. Please enter a number from 1 to 11.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input from the scanner
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return userInput;
    }

}



