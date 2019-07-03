/*
* Decompiled with CFR 0.137.
*/
package org.fleen.forsythia.app.grammarEditor.editor_Generator.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fleen.forsythia.app.grammarEditor.GE;
import org.fleen.forsythia.app.grammarEditor.JsonProcessor;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Editor_Generator;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Renderer;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanDetailFloor;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanExportImageSize;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanInterval;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.PanViewer;
import org.fleen.forsythia.app.grammarEditor.util.UI;
import org.fleen.util.ui.WrapLayout;

public class UI_Generator
extends JPanel {
   private static final long serialVersionUID = -4764589355634085547L;
   public JButton btngeneratestopgo;
   public JButton btngeneratemode;
   public JComboBox paletteMenu; //ellen changed
   public PanInterval pangenerateinterval;
   public PanDetailFloor pandetailfloor;
   public PanViewer panviewer;
   public JButton btnexportdir;
   public PanExportImageSize panexportsize;
   public JLabel lblinfo;
   public String selectedpalette = "Guide des associations"; //ellen changed
   public static StringBuilder paletteTitles = new StringBuilder();
   public static String[] paletteMenuString;
   //ellen added
   private Renderer renderer;
   String selectedString(ItemSelectable is) {
       Object selected[] = is.getSelectedObjects();
       return ((selected.length == 0) ? "null" : (String) selected[0]);
     }
  
   public Renderer getRenderer() {
       if (this.renderer == null) {
           this.renderer = new Renderer();
       }
       return this.renderer;
   }
  //end ellen added 

   public UI_Generator() {
	   System.out.println("In UI_Generator() AAX selectedpalette="+this.selectedpalette);
       GridBagLayout gridBagLayout = new GridBagLayout();
       gridBagLayout.columnWidths = new int[2];
       gridBagLayout.rowHeights = new int[4];
       gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
       gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
       this.setLayout(gridBagLayout);
       JPanel pantop = new JPanel();
       WrapLayout wl_pantop = new WrapLayout();
       wl_pantop.setAlignment(0);
       pantop.setLayout(wl_pantop);
       GridBagConstraints gbc_pantop = new GridBagConstraints();
       gbc_pantop.insets = new Insets(0, 0, 5, 0);
       gbc_pantop.fill = 1;
       gbc_pantop.gridx = 0;
       gbc_pantop.gridy = 0;
       
    		   //["Light Greens","Dark blue glow3","Dark blue glow2","Dark blue glow particle","Patriotic red white blue","pinoy pride","black blue","origami paper 2","black gray","red at night shepherds delight","ren architecture", "vuitton 2", "peach violet","Paragliding Peaches","Deep Blue", "light coral theme","gp wedding","Guide des associations","2019 BINK Calendar illust", "Blue sky ocean","Colorful houses at Nyhavn","house in tavira","origami paper3","kimono 3","4b107658e5aa935bbac842ca989e17d6"];//ellen changed
       this.add((Component)pantop, gbc_pantop);
       this.btngeneratestopgo = new JButton("stopgo foo");
       this.btngeneratestopgo.setBackground(UI.BUTTON_PURPLE);
       this.btngeneratestopgo.addMouseListener(new MouseAdapter(){

           @Override
           public void mouseClicked(MouseEvent e) {
               GE.ge.editor_generator.toggleStopGo();
           }
       });     
       pantop.add(this.btngeneratestopgo);
       this.btngeneratemode = new JButton("mode foo");
       this.btngeneratemode.setBackground(UI.BUTTON_PURPLE);
       this.btngeneratemode.addMouseListener(new MouseAdapter(){

           @Override
           public void mouseClicked(MouseEvent e) {
               GE.ge.editor_generator.toggleMode();
           }
       });
       pantop.add(this.btngeneratemode);
       this.pangenerateinterval = new PanInterval();
       pantop.add(this.pangenerateinterval);
       Component horizontalStrut = Box.createHorizontalStrut(12);
       pantop.add(horizontalStrut);
       this.pandetailfloor = new PanDetailFloor();
       pantop.add(this.pandetailfloor);
       Component horizontalStrut_1 = Box.createHorizontalStrut(12);
       pantop.add(horizontalStrut_1);
       JButton btnexport = new JButton("Export Image");
       btnexport.setBackground(UI.BUTTON_ORANGE);
       btnexport.addMouseListener(new MouseAdapter(){

           @Override
           public void mouseClicked(MouseEvent e) {
               GE.ge.editor_generator.exportImage();
           }
       });
       pantop.add(btnexport);
       this.btnexportdir = new JButton("ExportDir=~/fleen/export");
       this.btnexportdir.setBackground(UI.BUTTON_ORANGE);
       this.btnexportdir.addMouseListener(new MouseAdapter(){

           @Override
           public void mouseClicked(MouseEvent e) {
               GE.ge.editor_generator.setExportDir();
           }
       });
       pantop.add(this.btnexportdir);
       this.panexportsize = new PanExportImageSize();
       pantop.add(this.panexportsize);
       Component horizontalStrut_4 = Box.createHorizontalStrut(12);
       pantop.add(horizontalStrut_4);
       JButton btngrammar = new JButton("Grammar");
       btngrammar.setBackground(UI.BUTTON_RED);
       btngrammar.addMouseListener(new MouseAdapter(){

           @Override
           public void mouseClicked(MouseEvent e) {
               GE.ge.editor_generator.openGrammarEditor();
           }
       });
       pantop.add(btngrammar);
       Component horizontalStrut_3 = Box.createHorizontalStrut(12);
       pantop.add(horizontalStrut_3);
       JButton btnabout = new JButton("About");
       btnabout.setBackground(UI.BUTTON_GREEN);
       btnabout.addMouseListener(new MouseAdapter(){

           @Override
           public void mouseClicked(MouseEvent e) {
               GE.ge.editor_generator.openAboutPopup();
           }
       });
       //ellen added
       //"dark colors 1","dark colors 2","Weird holiday", "teal","mediterranean"
      // String[] paletteMenuString = {"Light Greens","Dark blue glow3","Dark blue glow2","Dark blue glow particle","Patriotic red white blue","pinoy pride","black blue","origami paper 2","black gray","red at night shepherds delight","ren architecture", "vuitton 2", "peach violet","Paragliding Peaches","Deep Blue", "light coral theme","gp wedding","Guide des associations","2019 BINK Calendar illust", "Blue sky ocean","Colorful houses at Nyhavn","house in tavira","origami paper3","kimono 3","4b107658e5aa935bbac842ca989e17d6"};//ellen changed
      // String[] paletteMenuString =  paletteTitles.toString().split(",");
     
       
       //https://stackoverflow.com/a/12899989
       //how to get the type of a value https://stackoverflow.com/a/15770571
      // paletteMenuString = paletteTitles.toString().split(","); //this is set over in jsonprocessor
        if(  (UI_Generator.paletteMenuString != null) && (UI_Generator.paletteMenuString).length > 0 ){
			 ((UI_Generator)GE.ge.editor_generator.getUI()).refreshPalettes();
        }
        else {
        	 System.out.println("UI_Generator 172 paletteMenuString was null");
        	 JsonProcessor jsonprocessor = new JsonProcessor();
        	 try {
        		 System.out.println("UI_Generator 176 paletteMenuString.length= "+paletteMenuString);
        		 JsonProcessor.getPaletteList(null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
	 
       System.out.println("UI_Generator 166 paletteMenuString.tostring="+paletteMenuString.toString());
       this.paletteMenu = new JComboBox(paletteMenuString);//ellen changed
       this.paletteMenu.setSelectedIndex(0);//ellen changed
       this.paletteMenu.addActionListener(this.paletteMenu);//ellen changed
       pantop.add((Component)this.paletteMenu, this.paletteMenu);//ellen changed

      //ellen added this ellen changed
       String selectedString;
     //ellen added

       ItemListener itemListener = new ItemListener() {
           public void itemStateChanged(ItemEvent itemEvent) {
             int state = itemEvent.getStateChange();
             System.out.println((state == ItemEvent.SELECTED) ? "Selected" : "Deselected");
             System.out.println("Item: " + itemEvent.getItem());
             ItemSelectable is = itemEvent.getItemSelectable();
            
             //int itmid = itemEvent.getID();
              System.out.println(", Selected: " + selectedString(is));
              System.out.println(", Selected: " + itemEvent.getID());
              
            // Renderer zrenderer = getRenderer();
            // zrenderer.setPalette(selectedString(is));
             ((UI_Generator)GE.ge.editor_generator.getUI()).selectedpalette =  selectedString(is);
             // public void setPalette(this.selectedString(is));  
             //setPalette(this.selectedString(is))
           }
           
         };
       this.paletteMenu.addItemListener(itemListener);
       //http://www.java2s.com/Tutorial/Java/0240__Swing/ListentoJComboBoxwithItemListener.htm


       pantop.add(btnabout);
       this.panviewer = new PanViewer();
       this.panviewer.setBackground(Color.YELLOW);
       GridBagConstraints gbc_panimage = new GridBagConstraints();
       gbc_panimage.insets = new Insets(0, 0, 5, 0);
       gbc_panimage.fill = 1;
       gbc_panimage.gridx = 0;
       gbc_panimage.gridy = 1;
       this.add((Component)this.panviewer, gbc_panimage);
       JPanel paninfo = new JPanel();
       GridBagConstraints gbc_paninfo = new GridBagConstraints();
       gbc_paninfo.fill = 1;
       gbc_paninfo.gridx = 0;
       gbc_paninfo.gridy = 2;
       this.add((Component)paninfo, gbc_paninfo);
       this.lblinfo = new JLabel("info");
       paninfo.add(this.lblinfo);
   }
   //http://www.java2s.com/Tutorial/Java/0240__Swing/ListentoJComboBoxwithItemListener.htm
//   public class ItemListenerSelectingComboSample {
 	//  static private String selectedString(ItemSelectable is) {
 	//    Object selected[] = is.getSelectedObjects();
 	//    return ((selected.length == 0) ? "null" : (String) selected[0]);
 	//  }

public void refreshPalettes() { //paletteMenuString
	// TODO Auto-generated method stub
	// System.out.println("paletteMenuString = "+ UI_Generator.paletteTitles);
	this.paletteMenu = new JComboBox(UI_Generator.paletteMenuString);
}
}