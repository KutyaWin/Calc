package com.example.main;

import com.example.service.CalculatorService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CalculatorServer {
    private static final int PORT = 8888;
    private final CalculatorService calculatorService;

    public CalculatorServer() {
        this.calculatorService = new CalculatorService();
        System.out.println("Калькулятор инициализирован");
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);
            System.out.println("Ожидание подключений...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(
                             clientSocket.getOutputStream())) {

                    String request = in.readLine();
                    if (request == null) continue;

                    System.out.println("Получен запрос: " + request);

                    // Simple HTTP response
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/plain");
                    out.println("Connection: close");
                    out.println();

                    try {
                        // Parse GET request
                        if (request.startsWith("GET /calculate")) {
                            String[] parts = request.split(" ")[1].split("\\?");
                            if (parts.length > 1) {
                                String[] params = parts[1].split("&");
                                String num1 = "";
                                String op = "";
                                String num2 = "";

                                for (String param : params) {
                                    String[] keyValue = param.split("=");
                                    if (keyValue.length == 2) {
                                        String key = keyValue[0];
                                        String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);

                                        switch (key) {
                                            case "num1" -> num1 = value;
                                            case "op" -> op = value;
                                            case "num2" -> num2 = value;
                                        }
                                    }
                                }

                                if (!num1.isEmpty() && !op.isEmpty() && !num2.isEmpty()) {
                                    String expression = num1 + " " + op + " " + num2;
                                    String result = calculatorService.calculate(expression);
                                    out.println("Результат: " + result);
                                    System.out.println("Отправлен результат: " + result);
                                } else {
                                    out.println("Ошибка: Неверные параметры запроса");
                                }
                            }
                        } else {
                            out.println("Использование: /calculate?num1=ЧИСЛО1&op=ОПЕРАЦИЯ&num2=ЧИСЛО2");
                        }
                    } catch (Exception e) {
                        out.println("Ошибка: " + e.getMessage());
                        System.err.println("Ошибка: " + e.getMessage());
                    }

                    out.flush();
                } catch (IOException e) {
                    System.err.println("Ошибка при обработке соединения: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new CalculatorServer().start();
    }
}