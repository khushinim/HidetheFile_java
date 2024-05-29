package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    private String name;

    UserView(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void home(){
        do {
            System.out.println("Welcome " + this.name);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhidden a file");
            System.out.println("Press 0 to Exit");
            Scanner sc =new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files){
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);

                    System.out.println("ID - File Name");
                    for (Data file : files){
                        System.out.println(file.getId() + " - " + file.getFileName());
                    }
                    System.out.println("Enter the ID of the file to unhidden");
                    int id = Integer.parseInt(sc.nextLine());
                    boolean isValidID = false;
                    for(Data file : files){
                        if(file.getId() == id) {
                            isValidID = true;
                            break;
                        }
                    }
                    if(isValidID) {
                        DataDAO.unhide(id);
                    } else {
                        System.out.println("Wrong ID");
                    }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        }while (true);
    }
}
