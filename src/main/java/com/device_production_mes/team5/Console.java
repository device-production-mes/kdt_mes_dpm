package com.device_production_mes.team5;

import com.device_production_mes.team5.controller.MainController;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Console implements CommandLineRunner {
    private final MainController mainController;

    @Override
    public void run(String... args) throws Exception {
        mainController.menu();
    }
}
