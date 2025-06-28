package Toll_Management_System;
import java.util.*;

class Journey {

    int startPoint;
    int endPoint;
    List<Integer> tollsPassed;
    int amountPaid;

    public Journey(int startPoint, int endPoint, List<Integer> tollsPassed, int amountPaid) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.tollsPassed = tollsPassed;
        this.amountPaid = amountPaid;
    }

}
