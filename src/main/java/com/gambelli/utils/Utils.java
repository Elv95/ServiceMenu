package com.gambelli.utils;

import java.io.File;
import java.io.FileNotFoundException;

import static com.gambelli.servicemenu.ServiceMenu.JSON_INPUT_NAME;

public final class Utils {

    /** Creates Input and Output Directory **/
    public static void createFileInputOutput() throws FileNotFoundException {

        System.out.print("Creation of Input and Output Directory...");
        File inputDir = new File(System.getProperty("user.dir") + "/input");
        File outputDir = new File(System.getProperty("user.dir") + "/output");
        File inputJson = new File(inputDir.toPath() + "/" + JSON_INPUT_NAME);

        //input directory
        if (!inputDir.exists()) { //if the input directory does not exist
            if (inputDir.mkdir()) { //create the input directory and throw the exception
                throw new FileNotFoundException("Folder input not found in path " + System.getProperty("user.dir") +
                        ". Folder created, insert a valid json in the folder input");
            } else { //can not create the input directory, throw and exception
                throw new FileNotFoundException("Folder input not found in path " + System.getProperty("user.dir") +
                        ". Please create the folder 'input' and insert a valid json in it");
            }
        } else if (!inputJson.exists()) {
            throw new FileNotFoundException("File " + inputJson.toPath() + " does not exist");
        }

        //output directory
        if (!outputDir.exists()) { //if the output directory does not exist, create it
            if (!outputDir.mkdir()) {//if can not create, throw and exception
                throw new FileNotFoundException("Folder output not found in path " + System.getProperty("user.dir") +
                        ". Unable to create it.");
            }
        }
        System.out.println(" OK");
    }

}
