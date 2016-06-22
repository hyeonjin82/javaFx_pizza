package finalproject;

import java.util.ArrayList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author qqq4343
 */
public class EmployeeTable {
    
    public static ArrayList<TableColumn<Employee, ?>> getColum(TableView table) {
        int i;

        ArrayList<TableColumn<Employee, ?>> columns = new ArrayList<TableColumn<Employee, ?>>();
        String[] columnNames = {"EmployeeID", "EmployeeName"};
        String[] variableNames = {"employeeId", "employeeName"};
        Integer[] column_width = {50, 50};

        i = 0;
        TableColumn<Employee, String> firstnameCol = new TableColumn(columnNames[i++]);
        TableColumn<Employee, String> lastnameCol = new TableColumn(columnNames[i++]);

        i = 0;
        firstnameCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));
        lastnameCol.prefWidthProperty().bind(table.widthProperty().divide(100 / column_width[i++]));
        
        i = 0;
        firstnameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>(variableNames[i++]));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>(variableNames[i++]));
        
        columns.add(firstnameCol);
        columns.add(lastnameCol);

        return columns;
    }
}
