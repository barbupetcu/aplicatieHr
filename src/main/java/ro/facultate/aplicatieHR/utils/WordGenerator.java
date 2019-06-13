package ro.facultate.aplicatieHR.utils;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Service
public class WordGenerator {

    private final String templatePath = Paths.get("").toAbsolutePath().normalize().toString() + File.separator +
            "src"+ File.separator+ "main" + File.separator+"resources"+File.separator+"templates"+File.separator;


    public ByteArrayInputStream generateDocument(Object obj, String templateName){

        try {
            Document doc = new Document(templatePath+templateName+".docx");

            doc.getMailMerge().setTrimWhitespaces(false);

            Field[] fields = obj.getClass().getDeclaredFields();


            HashMap<String, String> mergeFields = new HashMap<>();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


            for (Field f: fields){
                f.setAccessible(true);
                mergeFields.put(f.getName(), f.get(obj)!=null? f.get(obj).toString():"");
            }

            for (String s: mergeFields.keySet()){
                doc.getMailMerge().execute(new String[] {s}, new String[] {mergeFields.get(s)});
            }

//            doc.getMailMerge().execute(new String[] { "FullName", "Company", "Address", "Address2", "City" },
//                    new String[] { "James Bond", "MI5 Headquarters", "Milbank", "", "London" });

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            doc.save(output, SaveFormat.DOCX);
            return new ByteArrayInputStream(output.toByteArray());


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
