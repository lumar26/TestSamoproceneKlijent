package klijent;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();
        System.out.println(now);
        System.out.println(new Date());
        System.out.println(Instant.ofEpochMilli(new Date().getTime()));
        System.out.println(now.plus(Duration.ofMinutes(20)));
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println(now1);
        LocalDate now2 = LocalDate.now();
        System.out.println(now2);
        System.out.println(LocalDateTime.of(2020, 2, 23, 23, 33));



        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable runnable = () -> {
//            Thread.sleep(1000);
            System.out.println("Karanje");
        };

        executorService.submit(runnable);


//        scheduledExecutorService.shutdown();

        System.out.println("jebanje");
    }

}