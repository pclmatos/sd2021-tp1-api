import java.net.Socket;
import java.util.List;

import tp1.api.engine.AbstractSpreadsheet;
import tp1.api.Spreadsheet;


public class SpreadSheetImpl implements AbstractSpreadsheet{

	Spreadsheet sheet;

    public SpreadSheetImpl(SpreadSheet sheet){
		this.sheet = sheet;
    }

    @Override
 	public int rows() {
 		return sheet.getLines();
    }
     
 	@Override
 	public int columns() {
 		return sheet.getColumns();
    }
     
 	@Override
 	public String sheetId() {
 		return sheet.getSheetId();
    }
     
 	@Override 
 	public String cellRawValue(int row, int col) {
 		try {
 			return sheet.getRawValues().get(row).get(col);
 		} catch( IndexOutOfBoundsException e) {
 			return "#ERR?";
 		}
	}

    @Override
    public List<String> getRangeValues(String sheetURL, String range) {
		// TODO Auto-generated method stub
		

        return sheet.getRows();
    }

}