package org.example;

import java.util.ArrayList;

public class User implements Authoriable{
    private String name;
    private ArrayList<Ticket> tickets;

    public User(String name) {
        this.name = name;
        this.tickets = new ArrayList<>();
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public ArrayList<Ticket> getTickets(){
        return tickets;
    }

    @Override
    public void login() {
        System.out.println("Пользователь {id} авторизовался");
    }
}
