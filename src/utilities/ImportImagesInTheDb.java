package utilities;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.opencsv.CSVReader;

import entityManagement.DbEntity;
import bean.Entity;

public class ImportImagesInTheDb {

	public static void main(String[] args) throws IOException, PropertyVetoException {
		
		File toRead = new File("D:/eclipse-workspace/tagger/dl-mutation-tagger/csvfile.csv");
		ArrayList<Entity> entitiesToAdd = new ArrayList<Entity>();
		
		CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(toRead));
            String[] line;
            while ((line = reader.readNext()) != null) {
            	//if(line[0].startsWith("Link"))
            	//	continue;
            	String textToShow = line[0];
            	Entity entity = new Entity();
            	entity.setTextToShow(textToShow);
            	entity.setType("png");
            	entitiesToAdd.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		//Collections.shuffle(entitiesToAdd);
		
		for(Entity toAdd: entitiesToAdd){
			DbEntity.insert(toAdd);
		}

	}

}
