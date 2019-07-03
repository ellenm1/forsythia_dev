package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import java.awt.Color;
import java.util.List; 
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Palette {

@SerializedName("title")
@Expose
private String title;

@SerializedName("colorList0")
@Expose
private List<String> colorList0  = null;

@SerializedName("colorList1")
@Expose
private List<String> colorList1 = null;

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public List<String> getColorList0() {
return colorList0;
}

public List<String> getColorList1() {
	return colorList1;
}

public void setColorList0(List<String> color0) {
this.colorList0 = color0;
}



public void setColorList1(List<String> color1) {
this.colorList1 = color1;
}

}