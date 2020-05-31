package by.myshkovets.app.javaFX.excel.worker;


import by.myshkovets.app.javaFX.entity.tables.StatisticsDataProperty;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelWorker {
    private Stage stage;
    private List<StatisticsDataProperty> selectedItems;

    public ExcelWorker(Stage stage, List<StatisticsDataProperty> selectedItems) {
        this.stage = stage;
        this.selectedItems = selectedItems;
    }

    public void makeExcelReport() throws IOException {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel file(*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);


        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("Статистика");

        Row row = sheet.createRow(0);
        Cell operation = row.createCell(0);
        operation.setCellValue("операция");
        Cell content = row.createCell(1);
        content.setCellValue("содержание");
        Cell quantity = row.createCell(2);
        quantity.setCellValue("количество");
        Cell cash = row.createCell(3);
        cash.setCellValue("средства");
        Cell date = row.createCell(4);
        date.setCellValue("дата");


        Row rowIndex;
        int i = 1;
        for (StatisticsDataProperty line : selectedItems) {
            rowIndex = sheet.createRow(i);

            operation = rowIndex.createCell(0);
            operation.setCellValue(line.getOperation());
            content = rowIndex.createCell(1);
            content.setCellValue(line.getContent());
            quantity = rowIndex.createCell(2);
            quantity.setCellValue(line.getQuantity());
            cash = rowIndex.createCell(3);
            cash.setCellValue(line.getCash());
            date = rowIndex.createCell(4);
            date.setCellValue(line.getDateToString());

            i++;
        }
        for (i = 0; i < 5; ++i)
            sheet.autoSizeColumn(i);
        if(file != null)
            book.write(new FileOutputStream(file));
            book.close();
    }
}