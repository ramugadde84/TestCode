import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NewWorkBook {

	public static void main(String args[]) throws IOException {
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(
				"test.xlsx"));

		FileOutputStream fileOut = new FileOutputStream("new.xlsx");

		Sheet datatypeSheet = wb.getSheetAt(0);

		Iterator<Row> iterator = datatypeSheet.iterator();

		int count = 0;
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();

			//count++;

			if (count > 0) {

				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					currentCell.setCellValue("test");
				}
			}
			count++;
		}

		wb.write(fileOut);

		fileOut.close();
		wb.close();

	}

}
