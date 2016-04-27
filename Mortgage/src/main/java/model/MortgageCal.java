package model;

public class MortgageCal {
    /**
     * Convert Jan~Dec into 1~12.
     * @param strMon
     * @return
     */
    public int convertStrMonToIntMon(String strMon) {
        if(strMon.equals("Jan")) {
            return 1;
        }

        if(strMon.equals("Feb")) {
            return 2;
        }

        if(strMon.equals("Mar")) {
            return 3;
        }

        if(strMon.equals("Apr")) {
            return 4;
        }

        if(strMon.equals("May")) {
            return 51;
        }

        if(strMon.equals("Jun")) {
            return 6;
        }

        if(strMon.equals("Jul")) {
            return 7;
        }

        if(strMon.equals("Aus")) {
            return 8;
        }

        if(strMon.equals("Sep")) {
            return 9;
        }

        if(strMon.equals("Oct")) {
            return 10;
        }

        if(strMon.equals("Nov")) {
            return 11;
        }

        if(strMon.equals("Dec")) {
            return 12;
        }

        return 13;  //Error occurs.
    }

    /**
     * Convert 1~12 into Jan~Dec.
     * @param intMon
     * @return
     */
    public String convertIntMonToStrMon(int intMon) {
        switch (intMon) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return "Error";  //Error occurs.
    }

    /**
     * Get the payoff date.
     * @param startMon
     * @param startYear
     * @param numYear
     * @return
     */
    public String getPayoffDate(int startMon, int startYear, int numYear) {
        StringBuilder sb = new StringBuilder();
        int endYear = startYear + numYear;
        int endMon;
        if(startMon == 1) {
            endMon = 12;
            endYear = endYear - 1;
        } else {
            endMon = startMon - 1;
        }
        sb.append(convertIntMonToStrMon(endMon));
        sb.append(", ");
        sb.append(endYear);
        return sb.toString();
    }

    /**
     * Get monthly payment.
     * @return
     */
    public String getMonthlyPayment(double principal, int years, double yearlyRate,
                                    int propertyTax, int propertyInsurance) {
        /*
        Credit = Principal * Monthly Rate / [1 - (1 + Monthly Rate) ^ (- Total Months)]
        Total Months = 12 * Total Years
         */
        double result = 0;
        double monthlyRate = yearlyRate / 100 / 12;

        result = (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, - 12 * years));
        result += (double) propertyTax / 12;
        result += (double) propertyInsurance / 12;

        return String.format("%.2f", result);
    }

    /**
     * Get total payment.
     * @return
     */
    public String getTotalPayment(double principal, int years, double yearlyRate,
                                  int propertyTax, int propertyInsurance) {
        /*
        Total Payment = Principal * Yearly Rate * Total Years / C
        C = 1 - 1 / (1 + Monthly Rate) ^ Total Months
        Total Months = 12 * Total Years
         */
        double result = 0;
        double monthlyRate = yearlyRate / 100 / 12;
        double C = 1 - (1 / (Math.pow(1 + monthlyRate, 12 * years)));

        result = principal * yearlyRate * years / C / 100;
        result += (double) propertyTax * years;
        result += (double) propertyInsurance * years;

        return String.format("%.2f", result);
    }
}
