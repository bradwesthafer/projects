package Date;

import java.util.Scanner;

/**
 * Created by Brad on 10/15/2016.
 */
public class DateFormatter {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int month = 0, day = 0, year = 0;
        String initialDate;
        String convertedDate = "";
        System.out.println("This program converts dates from the format mm/dd/yyyy to the format Month dd, yyyy.");
        System.out.println("For the month, enter a number between 01 and 12.");
        System.out.println("For the day, enter a number between 01 and 31.");
        System.out.println("For the year, enter a number between 1000 and 3000.");
        System.out.print("Enter a date: ");
        initialDate = keyboard.nextLine();
        try {
            month = Integer.valueOf(initialDate.substring(0, 2));
            if (month > 12 || month < 1) {
                throw new MonthException();
            }
        } catch(MonthException me) {
            System.err.println(me.getMessage());
            while(month > 12 || month < 1) {
                try {
                    System.out.print("Enter a valid month (1-12): ");
                    month = keyboard.nextInt();
                    if (month > 12 || month < 1) {
                        throw new MonthException();
                    }
                } catch(MonthException me2) {
                    System.err.println(me2.getMessage());
                } catch(Exception e) {
                    System.err.println("ERROR: Non-Numeric Input.");
                }
            }
        } catch(Exception e) {
            System.err.println("The date must be numeric and entered in the format mm/dd/yyyy.");
            return;
        }
        try {
            day = Integer.valueOf(initialDate.substring(3, 5));
            if (day > 31 || day < 1) {
                throw new DayException();
            }
        } catch(DayException de) {
            System.err.println(de.getMessage());
            while(day > 31 || day < 1) {
                try {
                    System.out.print("Enter a valid day (1-31): ");
                    day = keyboard.nextInt();
                    if (day > 31 || day < 1) {
                        throw new DayException();
                    }
                } catch(DayException de2) {
                    System.err.println(de2.getMessage());
                } catch(Exception e) {
                    System.err.println("ERROR: Non-Numeric Input.");
                }
            }
        } catch(Exception e) {
            System.err.println("The date must be numeric and entered in the format mm/dd/yyyy.");
            return;
        }

        try {
            year = Integer.valueOf(initialDate.substring(6, 10));
            if (year > 3000 || year < 1000) {
                throw new YearException();
            }
        } catch(YearException ye) {
            System.err.println(ye.getMessage());
            while(year > 3000 || year < 1000) {
                try {
                    System.out.print("Enter a valid year (1000-3000): ");
                    year = keyboard.nextInt();
                    if (year > 3000 || year < 1000) {
                        throw new YearException();
                    }
                } catch(YearException ye2) {
                    System.err.println(ye2.getMessage());
                } catch(Exception e) {
                    System.err.println("ERROR: Non-Numeric Input.");
                }
            }
        } catch(Exception e) {
            System.err.println("The date must be numeric and entered in the format mm/dd/yyyy.");
            return;
        }
        switch(month) {
            case 1:
                convertedDate = "January " + day + ", " + year;
                break;
            case 2:
                convertedDate = "February " + day + ", " + year;
                break;
            case 3:
                convertedDate = "March " + day + ", " + year;
                break;
            case 4:
                convertedDate = "April " + day + ", " + year;
                break;
            case 5:
                convertedDate = "May " + day + ", " + year;
                break;
            case 6:
                convertedDate = "June " + day + ", " + year;
                break;
            case 7:
                convertedDate = "July " + day + ", " + year;
                break;
            case 8:
                convertedDate = "August " + day + ", " + year;
                break;
            case 9:
                convertedDate = "September " + day + ", " + year;
                break;
            case 10:
                convertedDate = "October " + day + ", " + year;
                break;
            case 11:
                convertedDate = "November " + day + ", " + year;
                break;
            case 12:
                convertedDate = "December " + day + ", " + year;
                break;
        }
        System.out.println(convertedDate);
    }
}
