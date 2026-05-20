package com.inventory.inventory.service;

import com.inventory.inventory.model.User;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class UserService {

    private final String FILE = "users.txt";

    public void register(User user) throws IOException {

        FileWriter writer = new FileWriter(FILE, true);

        writer.write(user.getUsername() + "," + user.getPassword() + "\n");

        writer.close();
    }

    public boolean login(String username, String password) throws IOException {

        File file = new File(FILE);

        if (!file.exists()) return false;

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",");

            if (data[0].equals(username) && data[1].equals(password)) {
                return true;
            }
        }

        return false;
    }
}