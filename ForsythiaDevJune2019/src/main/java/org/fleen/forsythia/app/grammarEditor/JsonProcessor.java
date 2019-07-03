package org.fleen.forsythia.app.grammarEditor;
import javax.annotation.Generated;
import javax.swing.JComboBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectReader;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonParseException;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Palette;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.PaletteList;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Renderer;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.UI_Generator;
import org.fleen.forsythia.app.grammarEditor.util.UI;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.UI_Generator;
import org.fleen.forsythia.app.grammarEditor.UIMain;

public class JsonProcessor {

//https://medium.com/@ssaurel/parse-and-write-json-data-in-java-with-gson-a61f8772e786	
	public static PaletteList getPaletteList(String args[]) throws IOException {
	Gson gson = new Gson();
	 BufferedReader br = null;
	 try {
	   br = new BufferedReader(new FileReader("/Users/ellenmeiselman/Google Drive/_Project Notes/fleen/palettelist.txt"));
	   System.out.println( "JsonProcessor 51" );
	 
	    PaletteList palettelist =   gson.fromJson(br, PaletteList.class);	   
	   
	   if (palettelist != null) { 
		   System.out.println( "JsonProcessor 54 palettelist= "+palettelist );
		   UI_Generator.paletteTitles.setLength(0); //empty the array
		   Renderer.zpalettelist = palettelist;
		  
	     for (Palette t : palettelist.getPaletteList()) {	    	
	       //System.out.println("JsonProcessor 59="+ t.getTitle() + " - " + t.getColorList0() );
	       UI_Generator.paletteTitles.append(t.getTitle()+","); 
	       UI_Generator.paletteMenuString = UI_Generator.paletteTitles.toString().split(",");
	      // Renderer.color0 = t.getColorList0();
	     // Renderer.color1 = t.getColorList1();
	     }//end for
	     
	   }//end if
	  	     
	    // UI_Generator.panviewer.paletteMenu = new JComboBox( UI_Generator.paletteMenuString);
	     //create a new methodin UI_Generator to refresh the jcombobox.
	     //https://stackoverflow.com/a/49411906 how to change jcombobox options
	     //((UI_Generator)GE.ge.editor_generator.getUI()).refreshPalettes();
	   
	  } catch (FileNotFoundException e) {
	    e.printStackTrace();
	  } finally {
	    if (br != null) {
	     try {
	      br.close();
	     } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     }
	  }
	 }
	  
	return null;
	 
	}
	
}
	
	
	 
	  
 
	
	



