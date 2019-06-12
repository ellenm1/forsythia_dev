/*
 * Decompiled with CFR 0.137.
 */
package org.fleen.forsythia.app.grammarEditor.editor_Generator;

import com.sun.imageio.plugins.png.PNGMetadata;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import org.fleen.forsythia.app.grammarEditor.GE;

public class ImageExporter
implements Serializable {
    private static final long serialVersionUID = -5177659741259613723L;
    private File exportdirectory = null;
    private static final int IMAGESIZE_MIN = 100;
    private static final int IMAGESIZE_MAX = 999000;
    private static final int IMAGESIZE_DEFAULT = 4800;
    private int imagesize = 4800;
    private static final double INCHES_IN_A_METER = 39.3700787;
    private static final int DPI = 300;
    private static final String IMAGEFILEPREFIX = "i";

    public void setExportDirectory(File f) {
        this.exportdirectory = f;
    }

    public File getExportDirectory() {
        if (this.exportdirectory == null) {
            this.exportdirectory = GE.getLocalDir();
        }
        return this.exportdirectory;
    }

    public void setImageSize(int s) {
        if (s < 100) {
            s = 100;
        }
        if (s > 999000) {
            s = 999000;
        }
        this.imagesize = s;
    }

    public int getImageSize() {
        return this.imagesize;
    }

    public void writePNGImageFile(BufferedImage image) {
        System.out.println("write image");
        File file = this.getExportFile();
        this.write(image, file);
    }

    private File getExportFile() {
        File test = null;
        boolean nameIsUsed = true;
        int index = 0;
        while (nameIsUsed) {
            test = new File(String.valueOf(this.getExportDirectory().getPath()) + "/" + IMAGEFILEPREFIX + index + ".png");
            if (test.exists()) {
                ++index;
                continue;
            }
            nameIsUsed = false;
        }
        return test;
    }

    private void write(BufferedImage image, File file) {
        Iterator<ImageWriter> i = ImageIO.getImageWritersBySuffix("png");
        ImageWriter writer = i.next();
        ImageOutputStream imageOutputstream = null;
        try {
            imageOutputstream = ImageIO.createImageOutputStream(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        writer.setOutput(imageOutputstream);
        PNGMetadata metaData = (PNGMetadata)writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
        metaData.pHYs_pixelsPerUnitXAxis = 11811;
        metaData.pHYs_pixelsPerUnitYAxis = 11811;
        metaData.pHYs_present = true;
        metaData.pHYs_unitSpecifier = 1;
        try {
            writer.write(null, new IIOImage(image, null, (IIOMetadata)metaData), null);
            imageOutputstream.flush();
            imageOutputstream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

