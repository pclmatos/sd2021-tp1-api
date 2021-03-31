import java.util.List;

import tp1.api.engine.AbstractSpreadsheet;


public class SpreadSheetImpl implements AbstractSpreadsheet{

    public SpreadSheetImpl(){
    }

    @Override
 	public int rows() {
 		return this.getLines();
    }
     
 	@Override
 	public int columns() {
 		return this.getColumns();
    }
     
 	@Override
 	public String sheetId() {
 		return this.getSheetId();
    }
     
 	@Override
 	public String cellRawValue(int row, int col) {
 		try {
 			return this.getRawValues().get(row).get(col);
 		} catch( IndexOutOfBoundsException e) {
 			return "#ERR?";
 		}
	}

    @Override
    public List<String> getRangeValues(String sheetURL, String range) {
        // TODO Auto-generated method stub
        return null;
    }

}