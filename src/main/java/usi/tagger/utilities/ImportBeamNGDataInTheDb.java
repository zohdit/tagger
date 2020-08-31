package usi.tagger.utilities;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import usi.tagger.bean.Entity;
import usi.tagger.entityManagement.DbEntity;

public class ImportBeamNGDataInTheDb {

    public static void main(String[] args) throws IOException, PropertyVetoException {

        Utilities.initializeFromFile("config.cfg");

        File toRead = new File(args[0]);

        ArrayList<Entity> entitiesToAdd = new ArrayList<Entity>();

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(toRead));
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (!line[0].endsWith("interactive_plot.json")) {
                    continue;
                }

                String textToShow = line[0];
                Entity entity = new Entity();
                entity.setTextToShow(textToShow);
                entity.setType("json");
                entitiesToAdd.add(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Entity toAdd : entitiesToAdd) {
            DbEntity.insert(toAdd);
        }

    }

}
