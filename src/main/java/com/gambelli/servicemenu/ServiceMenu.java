package com.gambelli.servicemenu;

import com.gambelli.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


public class ServiceMenu {

    public static final String JSON_INPUT_NAME = "ServiceMenu.json";

    private static MenuContent menucontent;


    public static void main(String[] args) throws FileNotFoundException {

        Utils.createFileInputOutput();

        readFileJson();

        XSSFWorkbook workbook = populateWorkbook(menucontent, populateAttribute(), 0);

        writeOutput(workbook);

    }


    /**
     * Populates the workbook analyzing every single node of the menu using a recursive depth method
     **/
    private static XSSFWorkbook populateWorkbook(Object menu, XSSFWorkbook workbook, int depth) {

        if (menu != null) {
            if (menu instanceof MenuContent) { //if menu is an instance of MenuContent
                if (((MenuContent) menu).getNodes() != null) { //if the list of child nodes is not null
                    for (int i = 0; i < ((MenuContent) menu).getNodes().size(); i++) { //recursive call for each child nodes
                        populateWorkbook(((MenuContent) menu).getNodes().get(i), workbook, depth + 1);
                    }
                }
            } else if (menu instanceof MenuNode) { //else if menu is an instance of MenuNode

                populateRow(menu, workbook, depth); //populate the workbook with the info of the actual node

                if (((MenuNode) menu).getNodes() != null) { //if the actual node has a list of child nodes
                    for (int j = 0; j < ((MenuNode) menu).getNodes().size(); j++) { //recursive call for each child nodes
                        populateWorkbook(((MenuNode) menu).getNodes().get(j), workbook, depth + 1);
                    }
                }
            }
        }
        return workbook;
    }


    /**
     * Populates the row of the Workbook with the informations of the node (object menu)
     **/
    private static void populateRow(Object menu, XSSFWorkbook workbook, int depth) {
        int i = getMaxDepth(menucontent);
        XSSFRow row = workbook.getSheetAt(0).createRow(workbook.getSheetAt(0).getLastRowNum() + 1);

        //set the X on the corresponding depth cell
        row.createCell(depth - 1).setCellValue("X");

        if (((MenuNode) menu).getNodeType().equals("service")) {//if NodeType is equal to "service"
            row.createCell(++i).setCellValue(((MenuNode) menu).getNodeId());
        } else {
            ++i;
        }

        row.createCell(++i).setCellValue(((MenuNode) menu).getNodeName());
        row.createCell(++i).setCellValue(((MenuNode) menu).getNodeType());

        if (((MenuNode) menu).getGroupType() != null) { // if GroupType is not null
            row.createCell(++i).setCellValue(((MenuNode) menu).getGroupType());
        } else {
            ++i;
        }

        if (((MenuNode) menu).getFlowType() != null) { //if FlowType is not null
            row.createCell(++i).setCellValue(((MenuNode) menu).getFlowType());
        } else {
            ++i;
        }

        if (((MenuNode) menu).getResource() != null) { //if Resource is not null
            if (((MenuNode) menu).getResource().getId() != null) {
                row.createCell(++i).setCellValue(((MenuNode) menu).getResource().getId());
            }
        } else {
            ++i;
        }

    }

    /**
     * Populates the Row of Attributes (First Row)
     **/
    private static XSSFWorkbook populateAttribute() {
        int i;
        XSSFWorkbook workbook = new XSSFWorkbook();

        System.out.print("Population of the Workbook...");
        //sets the sheet name and version
        XSSFSheet sheet = workbook.createSheet("Menu " + menucontent.getVersion());
        XSSFRow rowhead = sheet.createRow(0);

        //Depth cells
        for (i = 0; i < (getMaxDepth(menucontent) + 1); i++) {
            rowhead.createCell(i).setCellValue(i);
        }
        //Attributes
        rowhead.createCell(i).setCellValue("ServiceId");
        rowhead.createCell(++i).setCellValue("NodeName");
        rowhead.createCell(++i).setCellValue("NodeType");
        rowhead.createCell(++i).setCellValue("GroupType");
        rowhead.createCell(++i).setCellValue("FlowType");
        rowhead.createCell(++i).setCellValue("ResourceId");

        //sets column width
        setColumnWidth(workbook);

        return workbook;
    }


    /**
     * Bolds first row and Changes width of each column
     **/
    private static void setColumnWidth(XSSFWorkbook workbook) {

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int j = 0; j < workbook.getSheetAt(0).getRow(0).getLastCellNum(); j++) {
            workbook.getSheetAt(0).getRow(0).getCell(j).setCellStyle(style);
        }

        for (int i = 0; i < (getMaxDepth(menucontent) + 1); i++) {
            workbook.getSheetAt(0).setColumnWidth(i, 512);
        }

        workbook.getSheetAt(0).setColumnWidth(8, 11000);
        workbook.getSheetAt(0).setColumnWidth(9, 3000);
        workbook.getSheetAt(0).setColumnWidth(10, 3000);
        workbook.getSheetAt(0).setColumnWidth(11, 6000);
        workbook.getSheetAt(0).setColumnWidth(12, 3000);
    }


    /**
     * Calculates the maximum depth of the tree with a recursive depth search method
     **/
    public static int getMaxDepth(Object menu) {
        int depth = 0;
        if (menu instanceof MenuContent) {
            if (((MenuContent) menu).getNodes() != null) {
                for (int i = 0; i < ((MenuContent) menu).getNodes().size(); i++) {
                    depth = Math.max(depth, getMaxDepth(((MenuContent) menu).getNodes().get(i)));
                }
            }
        } else if (menu instanceof MenuNode) {
            if (((MenuNode) menu).getNodes() != null) {
                for (int i = 0; i < ((MenuNode) menu).getNodes().size(); i++) {
                    depth = Math.max(depth, getMaxDepth(((MenuNode) menu).getNodes().get(i)));
                }
            }
        }
        return depth + 1;
    }

    /**
     * Writes the workbook content in the output file
     **/
    private static void writeOutput(XSSFWorkbook workbook) {

        System.out.println(" OK");
        try {
            FileOutputStream fileOut = new FileOutputStream("output/ServiceMenu.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Xlsx File successfully created!");
    }


    /**
     * Reads the File JSon and turns it in a MenuContent
     **/
    private static void readFileJson() throws FileNotFoundException {
        Gson gson = new Gson();

        System.out.print("Reading File Json and turns it in a MenuContent...");
        JsonReader reader = new JsonReader(new FileReader("input/" + JSON_INPUT_NAME));
        //Turns it in a MenuContent
        menucontent = gson.fromJson(reader, MenuContent.class);
        System.out.println(" OK");

    }

}
