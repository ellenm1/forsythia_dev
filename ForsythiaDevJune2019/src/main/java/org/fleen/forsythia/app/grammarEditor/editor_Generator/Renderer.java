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
import org.fleen.forsythia.app.grammarEditor.JsonProcessor;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.Palette;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.PaletteList;
import org.fleen.forsythia.app.grammarEditor.editor_Generator.ui.UI_Generator;

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
   // public static  List<String> color0;
   // public static List<String> color1;
    public static Color[] color0;
    public static Color[] color1;
    public static PaletteList zpalettelist;

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
       // getFileData();
        JsonProcessor jsonprocessor = new JsonProcessor();
        try {
			JsonProcessor.getPaletteList(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.initColorPairs();
        System.out.println("In Renderer render() line 147 selectedPalette="+selectedPalette); 
        for (FPolygon polygon : forsythia.getLeafPolygons()) {
        	
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
    


 //   }
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
      System.out.println("In Renderer line 395 color0= "+this.color0 + "color1="+color1);
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

     
    	
          // this.color0 = new Color[]{Color.decode("#A2A0A8"), Color.decode("#7C6A7C"), Color.white };
         //  this.color1 = new Color[]{ Color.decode("#FFF586"), Color.white,Color.decode("#FF8C64"),  Color.decode("#FF6659")  };
    	//isfahan
          // this.color0 = new Color[]{Color.decode("#040FD9"), Color.decode("#030BA6"), Color.white };
          // this.setPalette(this.selectedPalette);
          zpalettelist.getPalette(zpalettelist, this.selectedPalette);
           
    	
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

     //public static void setPalette(String selectedPaletteString) {
     public void setPalette(String selectedPaletteString) {
     	//PaletteList.findPalette( (List<Palette>) zpalettelist, selectedPaletteString);
         System.out.println("In Renderer setPalette 120, I received: " + selectedPaletteString);
        String sp =  (selectedPaletteString!=null)&&(!selectedPaletteString.isEmpty())?selectedPaletteString:"light coral theme";
       
        List<String> colorArray0;
        List<String> colorArray1;
        Palette selectedPalette =  zpalettelist.getPalette(zpalettelist, selectedPaletteString);
        System.out.println("In Renderer setPalette 225");
       //ArrayList<String> colorString0.add( selectedPalette.getColorList0());
       colorArray0 = selectedPalette.getColorList0();//errors out here
       
       colorArray1 = selectedPalette.getColorList1();
         
        Color str = null;
    	 for (String i : colorArray0) {
    		 System.out.println("in Renderer setPalette 237 i="+i);
    		 //https://stackoverflow.com/questions/46570440/convert-string-to-java-awt-color
    		// color0[i]=Color.decode(i);
    	 }
      color0 = new Color[]{str};
     
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

