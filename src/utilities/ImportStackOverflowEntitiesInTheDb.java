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

public class ImportStackOverflowEntitiesInTheDb {

	public static void main(String[] args) throws IOException, PropertyVetoException {
		
		File toRead = new File("/Users/gbavota/publications/inprogress/dl-mutation/stack-overflow/tensorflow.csv");
		ArrayList<Entity> entitiesToAdd = new ArrayList<Entity>();
		
		CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(toRead));
            String[] line;
            while ((line = reader.readNext()) != null) {
            	if(line[0].startsWith("Link"))
            		continue;
            	String textToShow = "<p>" + line[1] + 
						"</p><a href=\"" + line[0] + "\" target=_blank> Open SO Discussion</a>";
            	Entity entity = new Entity();
            	entity.setTextToShow(textToShow);
            	entity.setType(line[2]);
            	entitiesToAdd.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		Collections.shuffle(entitiesToAdd);
		
		for(Entity toAdd: entitiesToAdd){
			DbEntity.insert(toAdd);
		}

	}

}
