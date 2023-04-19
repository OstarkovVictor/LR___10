package EX8;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ReadExcelFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Открываем файл Excel для чтения
        String filePath = "src/LR__10/EX4.xlsx";
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (IOException e) {
            System.err.println("Не удалось открыть файл Excel: " + e.getMessage());
            return;
        }

        // Создаем экземпляр книги Excel из файла
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.err.println("Не удалось создать экземпляр книги Excel: " + e.getMessage());
            closeResources(inputStream, null);
            return;
        }

        // Получаем лист из книги по его имени
        System.out.print("Введите имя листа: ");
        String sheetName = scanner.nextLine();
        XSSFSheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            System.err.println("Лист с именем \"" + sheetName + "\" не найден");
            closeResources(inputStream, workbook);
            return;
        }

        // Перебираем строки и ячейки листа
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Выводим значение ячейки на экран
                System.out.print(cell.toString() + "\t");
            }
            System.out.println();
        }

        // Закрываем файл и освобождаем ресурсы
        closeResources(inputStream, workbook);
    }

    private static void closeResources(FileInputStream inputStream, XSSFWorkbook workbook) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            System.err.println("Не удалось закрыть файл Excel: " + e.getMessage());
        }
    }
}