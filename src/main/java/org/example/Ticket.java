package org.example;

public class Ticket {
    private String value;

    public Ticket(String value) {
        this.value = value;
    }

    public boolean isLucky(String ticket) {
        String firstPart = this.value.substring(0, 3);
        String secondPart = this.value.substring(3);

        char[] firstPartCharArray = firstPart.toCharArray();
        char[] secondPartCharArray = secondPart.toCharArray();

        int firstPartSum = 0;
        int seconfPartSum = 0;

        for (int i = 0; i < firstPartCharArray.length; i++) {
            firstPartSum += firstPartCharArray[i];
        }

        for (int i = 0; i < secondPartCharArray.length; i++) {
            seconfPartSum += secondPartCharArray[i];
        }

        return  firstPartSum == seconfPartSum;
    }
}
