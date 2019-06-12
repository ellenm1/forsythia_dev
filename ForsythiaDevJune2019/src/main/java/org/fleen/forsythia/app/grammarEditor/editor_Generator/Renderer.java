/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.fleen.forsythia.core.composition.FPolygon;
import org.fleen.forsythia.core.composition.FPolygonSignature;
import org.fleen.forsythia.core.composition.ForsythiaComposition;
import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.util.tree.TreeNode;

public class Renderer
implements Serializable {
    private static final long serialVersionUID = 1885473221116487499L;
    private static final Color BACKGROUNDCOLOR = new Color(128, 128, 128);
    private static final int MARGIN = 10;
    public static final HashMap<RenderingHints.Key, Object> RENDERING_HINTS = new HashMap();
    public String selectedPalette;
    Random rnd = new Random();
    BufferedImage image;
    AffineTransform transform;
    Graphics2D graphics;
    //private Color strokecolor = Color.gray;
    private Color strokecolor = new Color(1, 0, 0, .1f);
    public Color[] color0;
    public Color[] color1;

    private Map<FPolygonSignature, Color> polygoncolors = new Hashtable<FPolygonSignature, Color>();
    private static final float STROKEWIDTH = 0.000f;
    Stroke stroke;
    Map<FPolygon, Path2D> pathbypolygon = new Hashtable<FPolygon, Path2D>();

    static {
        RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DEFAULT);
        RENDERING_HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        RENDERING_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        RENDERING_HINTS.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
    }

    public BufferedImage getImage(int width, int height, ForsythiaComposition composition) {
        this.image = this.getInitImage(width, height);
        this.transform = this.getTransform(width, height, composition);
        this.graphics = (Graphics2D)this.image.getGraphics();
        this.graphics.setTransform(this.transform);
        this.graphics.setRenderingHints(RENDERING_HINTS);
        this.render(composition);
        return this.image;
    }

    private BufferedImage getInitImage(int w, int h) {
        BufferedImage image = new BufferedImage(w, h, 2);
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(BACKGROUNDCOLOR);
        graphics.fillRect(0, 0, w, h);
        return image;
    }
    
   /* static String readFile(String path, Charset encoding) 
    		  throws IOException 
    		{
    		  byte[] encoded = Files.readAllBytes(Paths.get(path));

    		  String line=null;
    		    List<Palette> palettes = new ArrayList<>();
    		    while ((line = inputFile.readLine())!= null) {
    		       StringTokenizer sToken = new StringTokenizer(input, " ");
    		       list.add(new Palette(sToken.nextToken(),sToken.nextToken(),sToken.nextToken())); 
    		    }  
    		  
    		  return new String(encoded, encoding);
    		}
   */
    
   public void getFileData() { 
   //https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
	   StringBuilder sb = new StringBuilder();

    try (BufferedReader br = Files.newBufferedReader(Paths.get("/Users/ellenmeiselman/Google Drive/_Project Notes/fleen/palettelist.txt"))) {

        // read line by line
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

    } catch (IOException e) {
        System.err.format("IOException: %s%n", e);
    }

    System.out.println(sb);

   }
    
    
    private AffineTransform getTransform(int imagewidth, int imageheight, ForsythiaComposition composition) {
        Rectangle2D.Double compositionbounds = composition.getRootPolygon().getDPolygon().getBounds();
        double dmargin = 10.0;
        double cbwidth = compositionbounds.getWidth();
        double cbheight = compositionbounds.getHeight();
        double cbxmin = compositionbounds.getMinX();
        double cbymin = compositionbounds.getMinY();
        AffineTransform transform = new AffineTransform();
        double sw = ((double)imagewidth - dmargin * 2.0) / cbwidth;
        double sh = ((double)imageheight - dmargin * 2.0) / cbheight;
        double scale = Math.min(sw, sh);
        transform.scale(scale, - scale);
        double xoff = ((double)imagewidth / scale - cbwidth) / 2.0 - cbxmin;
        double yoff = - ((double)imageheight / scale + cbheight) / 2.0 + cbymin;
        transform.translate(xoff, yoff);
        return transform;
    }

    private void render(ForsythiaComposition forsythia) {
        Path2D path;
        this.initStroke();
        getFileData();
        this.initColorPairs();
        for (FPolygon polygon : forsythia.getLeafPolygons()) {
        	 System.out.println("In Renderer render() line 147 selectedPalette="+selectedPalette); 
            Color color = this.getColor(polygon,selectedPalette);//ellen changed
            this.graphics.setPaint(color);
            path = this.getPath2D(polygon);
            this.graphics.fill(path);
        }
        this.graphics.setPaint(this.strokecolor);
        this.graphics.setStroke(this.stroke);
        for (FPolygon polygon : forsythia.getLeafPolygons()) {
            path = this.getPath2D(polygon);
            this.graphics.draw(path);
        }
    }

    /*  ***  */
    
    //public static void setPalette(String selectedPaletteString) {
    public void setPalette(String selectedPaletteString) {
        System.out.println("In Renderer setPalette 120, I received: " + selectedPaletteString);
       String sp =  (selectedPaletteString!=null)&&(!selectedPaletteString.isEmpty())?selectedPaletteString:"light coral theme";
        Color[] colorList0;
        Color[] colorList1;

        switch(sp) {
          
          case "Paragliding Peaches":
                 colorList0 = new Color[] {  Color.decode("#FF6F5F"), Color.decode("#A7D2D6") };
                 colorList1 = new Color[]{ Color.white, Color.decode("#FF9D75"),  Color.decode("#FFE39F"),Color.decode("#FFC78C")  };       
                 setColorPairs(colorList0, colorList1);
                 break;

          case "Deep Blue":
                 colorList0 = new Color[] {  Color.decode("#FF6F5F"), Color.decode("#A7D2D6") };
                 colorList1 = new Color[]{ Color.white, Color.decode("#FF9D75"),  Color.decode("#FFE39F"),Color.decode("#FFC78C")  };       
                 setColorPairs(colorList0, colorList1);
                 break;

          case "light coral theme":
                 colorList0 = new Color[]{ Color.decode("#DBDBDD"), Color.decode("#B6B5B8") };
                 colorList1 = new Color[]{  Color.decode("#FDD8D3"),  Color.decode("#FBC1B8"),Color.decode("#F9F4F2")  }; 
                 setColorPairs(colorList0, colorList1);
                 break;            

          case "gp wedding":
                colorList0 = new Color[]{ Color.decode("#E4A399"), Color.decode("#EBC4BA") };
                colorList1 = new Color[]{  Color.decode("#F9EBDF"),  Color.decode("#D0DDD4"),Color.decode("#FFFCA2")  };            
                setColorPairs(colorList0, colorList1);
                break;

          case "Guide des associations":
                 colorList0 = new Color[]{ Color.decode("#CC1B4C"), Color.decode("#511B4C") };
                 colorList1 = new Color[]{  Color.decode("#82C8B5"),  Color.decode("#FFA33C"),Color.decode("#FF6B41")  };           
                 setColorPairs(colorList0, colorList1);
                 break;

          case "2019 BINK Calendar illust":
                 colorList0 = new Color[]{ Color.decode("#F2F2F2"), Color.decode("#5477BF") };
                 colorList1 = new Color[]{  Color.decode("#F2C84B"),  Color.decode("#F2921D"),Color.decode("#F24C26")  };           
                 setColorPairs(colorList0, colorList1);
                 break;
          case "Blue sky ocean":
                colorList0 = new Color[]{  Color.white,  Color.decode("#04658C") };
                colorList1 = new Color[]{ Color.white,  Color.decode("#9BD9F2"), Color.decode("#1A64A5"),  Color.BLACK,Color.decode("#16808C"),Color.decode("#45A5A5")  };            
                 setColorPairs(colorList0, colorList1);
                break;
         case "Colorful houses at Nyhavn":
                 colorList0 = new Color[] {  Color.decode("#13318C"), Color.decode("#0A2459") };
                 colorList1 = new Color[]{ Color.white, Color.decode("#87A5BF"),  Color.decode("#F2B604"),Color.decode("#D84B17")  };       
                 setColorPairs(colorList0, colorList1);
                 break;
            
         case "teal":
        	  colorList0 = new Color[]{Color.decode("#023859"), Color.decode("#03658C") };
        	  colorList1 =new Color[]{ Color.white, Color.decode("#218DA6"),  Color.decode("#B4D2D9"),Color.decode("#50B4BF")  };
              setColorPairs(colorList0, colorList1);
              break;  
         
         case "mediterranean":
        	 colorList0 =  new Color[]{Color.decode("#0476D9"), Color.decode("#79BAF2") };
        	 colorList1 = new Color[]{ Color.white, Color.decode("#0597F2"),  Color.decode("#04BFAD"),Color.decode("#A0A603")  };
              setColorPairs(colorList0, colorList1);
              break;
              
         case "house in tavira":
        	 colorList0 = new Color[]{Color.decode("#023373"), Color.decode("#022859") };
        	 colorList1 =new Color[]{ Color.white, Color.decode("#0468BF"),  Color.decode("#0396A6"),Color.decode("#A68A68")  };
        	 setColorPairs(colorList0, colorList1);
             break;
             
             
         case "origami paper3":
             colorList0 = new Color[]{Color.white, Color.decode("#A41710"), Color.decode("#BEDEC5") };
             colorList1 = new Color[]{ Color.white, Color.decode("#FEEEDA"),  Color.decode("#BF604B"),Color.decode("#D91A1A")  };     
             setColorPairs(colorList0, colorList1);
             break;    
             
         case "kimono 3":
             colorList0 = new Color[]{Color.decode("#BF9039"), Color.decode("#D8BC88") };
             colorList1 = new Color[]{ Color.white, Color.decode("#FEEEDA"),  Color.decode("#BF604B"),Color.decode("#D91A1A")  };     
             setColorPairs(colorList0, colorList1);
             break;    
             	 
        	 
         case "4b107658e5aa935bbac842ca989e17d6":
             colorList0 = new Color[]{Color.decode("#130E70"), Color.decode("#2D439F") };
             colorList1 = new Color[]{ Color.white, Color.decode("#D9352E"),  Color.decode("#F84B34"),Color.decode("#F1B702")  };     
             setColorPairs(colorList0, colorList1);
             break;  	 
        	  
                 //kimono 3
                 
                // this.color1 = new Color[]{Color.decode("#BF9039"), Color.decode("#D8BC88") };
                // this.color0 = new Color[]{ Color.white, Color.decode("#8C4E17"),  Color.decode("#BF574E"),Color.decode("#D89999")  };
                 //origami paper2

               //  this.color0 = new Color[]{Color.decode("#593B34"), Color.decode("#593257") };
                // this.color1 = new Color[]{ Color.white, Color.decode("#F2F2F2"),  Color.decode("#A69258"),Color.decode("#F23838")  };
                 
             	//deep space
                // this.color0 = new Color[]{Color.decode("#023372"), Color.decode("#011C3F") };
                // this.color1 = new Color[]{ Color.white, Color.decode("#026772"),  Color.decode("#028C8C"),Color.decode("#010D00")  };
              
             	//Hermosa
               //  this.color0 = new Color[]{Color.decode("#F2A2B0"), Color.decode("#D7EFF2") };
               //  this.color1 = new Color[]{  Color.decode("#A9D8D3"),  Color.decode("#91D8CB"),Color.decode("#5FBFA4")  };
               //Charming little girl - fairy queen
               //  this.color0 = new Color[]{Color.decode("#A51731"), Color.decode("#3D5901") };
               //  this.color1 = new Color[]{  Color.decode("#393F0E"),  Color.decode("#8C7A59"),Color.decode("#8C0F0F")  };
             
             	
             	//Slipstream Brewing Company
              // this.color0 = new Color[]{Color.decode("#021E73"), Color.decode("#3D79F2") };
                 //   this.color1 = new Color[]{  Color.white,Color.decode("#82B0D9"),  Color.decode("#D9753B"),Color.decode("#BF3F3F")  };
             	
             	//red at night shepherds delight
             	
                   // this.color0 = new Color[]{Color.decode("#A2A0A8"), Color.decode("#7C6A7C"), Color.white };
                  //  this.color1 = new Color[]{ Color.decode("#FFF586"), Color.white,Color.decode("#FF8C64"),  Color.decode("#FF6659")  };
             	//isfahan
                   // this.color0 = new Color[]{Color.decode("#040FD9"), Color.decode("#030BA6"), Color.white };  
            
            
            
            /*
          case "aaa":
                 colorList0 = new Color[] {  Color.decode("#FF6F5F"), Color.decode("#A7D2D6") };
                 colorList1 = new Color[]{ Color.white, Color.decode("#FF9D75"),  Color.decode("#FFE39F"),Color.decode("#FFC78C")  };       
            break;
          case "bbb":
                 colorList0 = new Color[] {  Color.decode("#FF6F5F"), Color.decode("#A7D2D6") };
                 colorList1 = new Color[]{ Color.white, Color.decode("#FF9D75"),  Color.decode("#FFE39F"),Color.decode("#FFC78C")  };       
                 break;
        */    
          default:
                  colorList0 = new Color[] {  Color.decode("#FF6F5F"), Color.decode("#A7D2D6") };
                  colorList1 = new Color[]{  Color.white, Color.decode("#FF9D75"), Color.decode("#FFE39F"), Color.decode("#FFC78C") };      
                  setColorPairs(colorList0, colorList1);
        }

    }
/* *** */
    public void setColorPairs(Color[] zcolor0, Color[] zcolor1) {
        //this.color0 = new Color[]{this.getRandomGoodColor(16), this.getRandomGoodColor(16)};
        //this.color1 = new Color[]{ this.getRandomGoodColor(8),  Color.LIGHT_GRAY };


      this.color0 = zcolor0;
      this.color1 = zcolor1;
      //   Object zobj = new Object[2];
      //  zobj.get(0) = zcolor0;
       // zobj.get(1) = zcolor1;
       // return zobj;
      System.out.println("color0= "+this.color0 + "color1="+color1);
    }   
    
    
    
    
    
    public Color getColor(FPolygon polygon, String sp) {
        FPolygonSignature sig = polygon.getSignature();
        Color color = this.polygoncolors.get(sig);
        if (color == null) {
            int eggdepth = this.getTagDepth(polygon, "egg");
            if (eggdepth % 2 == 0) {
                int colorindex = this.rnd.nextInt(this.color0.length);
                color = this.color0[colorindex];
            } else {
                int colorindex = this.rnd.nextInt(this.color1.length);
                color = this.color1[colorindex];
            }
            this.polygoncolors.put(sig, color);
        }
        return color;
    }

 /* ••••••• */
    
    
    private void initColorPairs() {

        //this.color0 = new Color[]{this.getRandomGoodColor(16), this.getRandomGoodColor(16)};

      //  this.color0 = new Color[]{ Color.LIGHT_GRAY, Color.BLACK, Color.getHSBColor(48, 41, 100), this.getRandomGoodColor(5)};
    	//black and white
    //	this.color0 = new Color[]{ this.getRandomGoodColor(16),Color.white,Color.LIGHT_GRAY, Color.BLACK };
      //  this.color1 = new Color[]{ Color.white,Color.DARK_GRAY };
            
       //teal
        //this.color0 = new Color[]{Color.decode("#023859"), Color.decode("#03658C") };
       //this.color1 = new Color[]{ Color.white, Color.decode("#218DA6"),  Color.decode("#B4D2D9"),Color.decode("#50B4BF")  };
//color.adobe.com
        
      //mediterranean
      //  this.color0 = new Color[]{Color.decode("#0476D9"), Color.decode("#79BAF2") };
      //  this.color1 = new Color[]{ Color.white, Color.decode("#0597F2"),  Color.decode("#04BFAD"),Color.decode("#A0A603")  };
//house in tavira
     //   this.color0 = new Color[]{Color.decode("#023373"), Color.decode("#022859") };
      //  this.color1 = new Color[]{ Color.white, Color.decode("#0468BF"),  Color.decode("#0396A6"),Color.decode("#A68A68")  };
      //origami paper3
      //  this.color0 = new Color[]{Color.white, Color.decode("#A41710"), Color.decode("#BEDEC5") };
      // this.color1 = new Color[]{ Color.white, Color.decode("#FEEEDA"),  Color.decode("#BF604B"),Color.decode("#D91A1A")  };
        
        //kimono 3
        
       // this.color1 = new Color[]{Color.decode("#BF9039"), Color.decode("#D8BC88") };
       // this.color0 = new Color[]{ Color.white, Color.decode("#8C4E17"),  Color.decode("#BF574E"),Color.decode("#D89999")  };
        //origami paper2

      //  this.color0 = new Color[]{Color.decode("#593B34"), Color.decode("#593257") };
       // this.color1 = new Color[]{ Color.white, Color.decode("#F2F2F2"),  Color.decode("#A69258"),Color.decode("#F23838")  };
        
    	//deep space
       // this.color0 = new Color[]{Color.decode("#023372"), Color.decode("#011C3F") };
       // this.color1 = new Color[]{ Color.white, Color.decode("#026772"),  Color.decode("#028C8C"),Color.decode("#010D00")  };
     
    	//Hermosa
      //  this.color0 = new Color[]{Color.decode("#F2A2B0"), Color.decode("#D7EFF2") };
      //  this.color1 = new Color[]{  Color.decode("#A9D8D3"),  Color.decode("#91D8CB"),Color.decode("#5FBFA4")  };
      //Charming little girl - fairy queen
      //  this.color0 = new Color[]{Color.decode("#A51731"), Color.decode("#3D5901") };
      //  this.color1 = new Color[]{  Color.decode("#393F0E"),  Color.decode("#8C7A59"),Color.decode("#8C0F0F")  };
    
    	
    	//Slipstream Brewing Company
     // this.color0 = new Color[]{Color.decode("#021E73"), Color.decode("#3D79F2") };
        //   this.color1 = new Color[]{  Color.white,Color.decode("#82B0D9"),  Color.decode("#D9753B"),Color.decode("#BF3F3F")  };
    	
    	//red at night shepherds delight
    	
          // this.color0 = new Color[]{Color.decode("#A2A0A8"), Color.decode("#7C6A7C"), Color.white };
         //  this.color1 = new Color[]{ Color.decode("#FFF586"), Color.white,Color.decode("#FF8C64"),  Color.decode("#FF6659")  };
    	//isfahan
          // this.color0 = new Color[]{Color.decode("#040FD9"), Color.decode("#030BA6"), Color.white };
           this.setPalette(this.selectedPalette);
           
    	
    }

 
     private Color getRandomGoodColor(int integer) {

        // Color c = new Color(64 + this.rnd.nextInt(12) * 16, 64 + this.rnd.nextInt(12) * 16, 64 + this.rnd.nextInt(12) * 16);

           Color c = new Color(64 + this.rnd.nextInt(12) * integer, 16 + this.rnd.nextInt(12) * integer, 64 + this.rnd.nextInt(12) * integer); //ellen changed

         return c;

     }

  

     private void initStroke() {

        // this.stroke = new BasicStroke(0.008f, 2, 1, 0.0f, null, 0.0f);

         this.stroke = new BasicStroke(0.000f, 1, 1, 0.0f, null, 0.0f);

     }

  
    
    
    
    
    
  /*  
    
    private void initColorPairs() {
        this.color0 = new Color[]{Color.LIGHT_GRAY, this.getRandomGoodColor()};
       // this.color1 = new Color[]{this.getRandomGoodColor(), this.getRandomGoodColor()};
        this.color1 = new Color[]{Color.BLACK, Color.WHITE};
    }

    private Color getRandomGoodColor() {
     //   Color c = new Color(64 + this.rnd.nextInt(12) * 16, 64 + this.rnd.nextInt(12) * 16, 64 + this.rnd.nextInt(12) * 16);
       // Color c = new Color(32 + this.rnd.nextInt(12) * 16, 32 + this.rnd.nextInt(12) * 16, 64 + this.rnd.nextInt(12) * 16);
        Color c = new Color(32 + this.rnd.nextInt(12) * 16, 32 + this.rnd.nextInt(12) * 16, 64 + this.rnd.nextInt(12) * 16);
        return c;
    }

    private void initStroke() {
        this.stroke = new BasicStroke(0.000f, 2, 1, 0.0f, null, 0.0f);
    }
*/
    private Path2D getPath2D(FPolygon polygon) {
        Path2D path = this.pathbypolygon.get(polygon);
        if (path == null) {
            path = this.createPath2D(polygon);
            this.pathbypolygon.put(polygon, path);
        }
        return path;
    }

    private Path2D createPath2D(FPolygon polygon) {
        Path2D.Double path = new Path2D.Double();
        DPolygon points = polygon.getDPolygon();
        DPoint p = (DPoint)points.get(0);
        path.moveTo(p.x, p.y);
        for (int i = 1; i < points.size(); ++i) {
            p = (DPoint)points.get(i);
            path.lineTo(p.x, p.y);
        }
        path.closePath();
        return path;
    }

    protected int getTagDepth(TreeNode node, String tag) {
        int c = 0;
        for (TreeNode n = node; n != null; n = n.getParent()) {
            if (!(n instanceof FPolygon)) continue;
            FPolygon p = (FPolygon)n;
            if (!p.hasTags(tag)) continue;
            ++c;
        }
        return c;
    }
}

