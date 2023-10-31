package com.example.busybuddy;

import java.util.ArrayList;

public class MenuItemModel {
    private String title;

    private String folder;

    public static ArrayList<MenuItemModel> menulist = new ArrayList<>();

    public static ArrayList<MenuItemModel> a() {
        ArrayList<MenuItemModel> menuItemModels = new ArrayList<>();

        for (MenuItemModel menuItemModel : menulist) {
            menuItemModels.add(menuItemModel);
        }

        return menuItemModels;

    }

    public MenuItemModel(String title, String folder) {
        this.title = title;
        this.folder = folder;
    }


    public String getFolder() {
        return folder;
    }
    public String getTitle() {
        return title;
    }


}
