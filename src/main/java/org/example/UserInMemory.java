package org.example;

import java.util.HashMap;

public class UserInMemory {
    HashMap<String, User> userMap = new HashMap<>();

    public boolean addUser(String id, User user) {
        if (userMap.put(id, user) == null) {
            return true;
        }
        return false;
    }

    public boolean removeUser(String id) {
        if(userMap.remove(id) != null){
            return true;
        }
        return false;
    }
}
