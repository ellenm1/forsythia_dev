package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import java.util.List;

import org.beryx.awt.color.ColorFactory;

import java.util.ArrayList;
import java.awt.Color;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
 

public class PaletteList {

@SerializedName("palettelist")
@Expose
private List<Palette> palettelist = null;

public List<Palette> getPaletteList() {
return palettelist;
}

public void setPaletteList(List<Palette> plist) {

 this.palettelist = plist;
}


public Palette getPalette(PaletteList pl, String selectedPaletteTitle ) {
	 Palette p=null;
	 for (Palette t : palettelist) {	 
		 if ((t.getTitle().toString()).equals(selectedPaletteTitle.toString())){
			 System.out.println( "t.getTitle()="+selectedPaletteTitle);
			p=t; 
			//TODO set colors using awt-color-factory
			//create them AS Color objects
			int len0 = t.getColorList0().size();
			int len1 = t.getColorList1().size();
			 Color[] colorarray0 = new Color[len0];
			 Color[] colorarray1 = new Color[len1];
			 int currentPosition0 = 0;
			 int currentPosition1 = 0;
			for ( String str0  : t.getColorList0()){				     
				//https://stackoverflow.com/questions/20961617/get-the-current-index-of-a-for-each-loop-iterating-an-arraylist	
				Color c =  ColorFactory.valueOf(str0);
					//https://stackoverflow.com/questions/36001860/how-to-randomly-set-jbutton-color-from-array-of-colors/36004026#36004026
					//https://www.javatpoint.com/how-to-convert-arraylist-to-array-and-array-to-arraylist-in-java
					colorarray0[currentPosition0] = c;
					 currentPosition0++;
			}
			for ( String str1  : t.getColorList1()){				     
				//https://stackoverflow.com/questions/20961617/get-the-current-index-of-a-for-each-loop-iterating-an-arraylist	
				Color d =  ColorFactory.valueOf(str1);
					//https://stackoverflow.com/questions/36001860/how-to-randomly-set-jbutton-color-from-array-of-colors/36004026#36004026
					//https://www.javatpoint.com/how-to-convert-arraylist-to-array-and-array-to-arraylist-in-java
					colorarray1[currentPosition1] = d;
					 currentPosition1++;
			}
			Renderer.color0 = colorarray0;
			Renderer.color1 = colorarray1;
		 }
		//TODO add else here
	 }
	 return p;
}

 
//https://stackoverflow.com/questions/52864625/java-search-for-a-specific-property-whilst-iterating-through-a-list-of-objects?noredirect=1&lq=1
public static boolean findPalette(PaletteList pl, Renderer r, String enteredTitle) {
	System.out.println( "enteredTitle="+enteredTitle);
	
     Palette selectedPalette;
   //  for (List<String> s: Palette.getColorList0()) {	   
    	 
    	 
   //  }
     
    // if (palette.getTitle()==enteredTitle) {
     
	// for (List <String> s : List<String> selectedPalette.getColorList0() ) {

      //  if (palette.getTitle().equals(enteredTitle)) {
      
    	 //  System.out.println( "palette.getTitle()=="+enteredTitle);
       
     //   }
  //  }

    return false;
}


}