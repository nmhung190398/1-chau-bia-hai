package net.devnguyen;

import net.devnguyen.service.DataService;

public class MainApplication {
    public static void main(String[] args) {
        DataService dataService = new DataService();
        dataService.syncData();
    }


}
