package tp1.server.resources;

import tp1.api.Spreadsheet;
import tp1.api.User;
import tp1.api.service.rest.RestSpreadsheets;
import tp1.util.*;
import tp1.impl.engine.SpreadsheetEngineImpl;

import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.List;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;

import org.apache.commons.lang3.tuple.Pair;

public class SpreadSheetsResource implements RestSpreadsheets{

    private Map<String, Sheet> sheets = new HashMap<>();

    private static Logger Log = Logger.getLogger(SpreadSheetsResource.class.getName());

    public SpreadSheetsResource(){
    }

    @Override
    public String createSpreadsheet(Spreadsheet sheet, String password) {
        // TODO Auto-generated method stub

        if(!sheet.getOwner().getPassword().equals(password)){
            Log.info("Password incorrect");
            throw new WebApplicationException(Status.BAD_REQUEST);
        }
        synchronized(this){
            sheets.put(sheet.getSheetId(), sheet);
        }

        return null;
    }

    @Override
    public void updateCell(String sheetId, String cell, String rawValue, String userId, String password) {
        // TODO Auto-generated method stub
        synchronized(this){
            if( sheets.get(sheetId) == null){
                Log.info("Sheet does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(password == null || !userpassword.equals(password)){
                Log.info("Password incorrect");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

            if(!userId.equals(sheet.getOwner()) && !sheet.getSharedWith().contains(userId)){
                Log.info("No permission");
                throw new WebApplicationException(Status.BAD_REQUEST);
            } else {

                List<List<String>> values = sheets.get(sheetId).getRawValues();
                
                Pair<Integer,Integer> cellValues = Cell.CellId2ListIndexes(cell);

                values.get(cellValues.getKey()).add(cellvalues.getValue(),rawValue);

            }

        }

    }

    @Override
    public void shareSpreadsheet(String sheetId, String userId, String password) {
        // TODO Auto-generated method stub

        synchronized(this){

            SpreadSheet aux = sheets.get(sheetId);
            User u = users.get(userId);

            if( aux == null){
                Log.info("Sheet does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if( u == null) {
                Log.info("User does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(aux.getSharedWith().contains(userId)){
                Log.info("Sheet already shared with this user");
                throw new WebApplicationException(Status.CONFLICT);
            }

            if(!users.get(sheet.getOwner()).getPassword().equals(password)){
                Log.info("Password is incorrect");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

            aux.getSharedWith().add(userId);

        }
        
    }

    @Override
    public void deleteSpreadsheet(String sheetId, String password) {
        // TODO Auto-generated method stub

        synchronized(this){

            Spreadsheet aux = sheets.get(sheetId);
            
            if(aux == null){
                Log.info("Spreadsheet does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(!aux.getPassword().equals(password)){
                Log.info("Password is incorrect");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

        }
        
    }

    @Override
    public Spreadsheet getSpreadsheet(String sheetId, String userId, String password) {
        // TODO Auto-generated method stub

        synchronized(this){

            Spreadsheet aux = sheets.get(sheetId);
            User u = users.get(userId);
            
            if(aux == null){
                Log.info("Spreadsheet does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(u == null){
                Log.info("User does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(!u.getPassword().equals(password)){
                Log.info("Password is incorrect");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

            if(!aux.getSharedWith().contains(userId)){
                Log.info("Spreadsheet is not shared with this user");
                throw new WebApplicationException(Status.BAD_REQUEST);
            }

        }

        return aux;
    }

    @Override
    public List<List<String>> getSpreadsheetValues(String sheetId, String userId, String password) {
        // TODO Auto-generated method stub

        synchronized(this){

            Spreadsheet aux = sheets.get(sheetId);
            User u = users.get(userId);
            
            if(aux == null){
                Log.info("Spreadsheet does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(u == null){
                Log.info("User does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(!u.getPassword().equals(password)){
                Log.info("Password is incorrect");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

            if(!aux.getSharedWith().contains(userId) || !aux.getOwner().equals(userId)){
                Log.info("Spreadsheet is not shared with this user, or user is not the owner");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

        }

        return aux.getRawValues();
    }

    @Override
    public void unshareSpreadsheet(String sheetId, String userId, String password) {
        // TODO Auto-generated method stub

        synchronized(this){

            Spreadsheet aux = sheets.get(sheetId);
            User u = users.get(userId);
            
            if(aux == null){
                Log.info("Spreadsheet does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(u == null){
                Log.info("User does not exist");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(!aux.getSharedWith().contains(userId)){
                Log.info("Spreadsheet is not shared with this user");
                throw new WebApplicationException(Status.NOT_FOUND);
            }

            if(!u.getPassword().equals(password)){
                Log.info("Password is incorrect");
                throw new WebApplicationException(Status.FORBIDDEN);
            }

            aux.getSharedWith().remove(userId);

        }

    }

}