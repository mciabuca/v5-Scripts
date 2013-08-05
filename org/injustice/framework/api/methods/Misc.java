/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.framework.api.methods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.injustice.framework.api.IMethodContext;
import org.injustice.framework.api.IMethodProvider;
import org.powerbot.script.util.Timer;
import org.powerbot.script.wrappers.Actor;
import org.powerbot.script.wrappers.Area;
import org.powerbot.script.wrappers.Tile;

/**
 *
 * @author Suhaib
 */
public class Misc extends IMethodProvider {
    
    public Misc(IMethodContext c) {
        super(c);
    }

    public void fillPolygons(Graphics g, Color c, Actor... e) {
        g.setColor(c);
        for (Actor entity : e) {
            for (Polygon p : entity.getModel().getTriangles()) {
                g.fillPolygon(p);
            }
        }
    }

    public void fillTiles(Graphics g, Color c, Tile... t) {
        g.setColor(c);
        for (Tile tile : t) {
            if (tile != null) {
                tile.getMatrix(ctx).draw(g);
            }
        }
    }

    public boolean isOnScreen(Actor e) {
        return e.isOnScreen() && !(ctx.widgets.get(640, 4).isValid() ?
                ctx.widgets.get(640, 4).isValid() :
                ctx.widgets.get(640, 2).getBoundingRect().contains(e.getCenterPoint()));
    }

    public void savePaint(final int x, final int y, final int w, final int h, String status) {
        Timer t = new Timer(6000);
        if (!t.isRunning()) {
            t.reset();
//            final File path = new File(Environment.getStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
//            final BufferedImage img = Environment.captureScreen().getSubimage(x, y, w, h);
            try {
//                ImageIO.write(img, "png", path);
                status = "Saving screenshot";
            } catch (Exception e) {
                e.printStackTrace();
                status = "Failed screenshot";
            }
        }
    }

    public Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }
    
    
    public void percBar(boolean vertical, int x, int y,
            int width, int height, double percentage,
            Color PercCol, Color outC, Stroke outS, Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        int drawPercentage = (int) (((percentage > 100 ? 100 : percentage) / 100) * width);
        g.setColor(PercCol);
        if (!vertical) {
            g.fillRect(x, y, (width > height ? drawPercentage : width), (height >= width ? drawPercentage : height));
        } else {
            int x1 = (width > height ? width - drawPercentage : 0);
            int y1 = (height > width ? height - drawPercentage : 0);
            g.fillRect(x + x1, y + y1, width - x1, height - y1);
        }
        g.setColor(outC);
        g.setStroke(outS);
        g.drawRect(x, y, width, height);

    }

    public String format(long number, int precision) {
        String sign = number < 0 ? "-" : "";
        number = Math.abs(number);
        if (number < 1000) {
            return sign + number;
        }
        int exponent = (int) (Math.log(number) / Math.log(1000));
        return String.format("%s%." + precision + "f%s", sign,
                number / Math.pow(1000, exponent),
                "kmbtpe".charAt(exponent - 1));
    }
    
    public Area makeArea(final int radius, final Tile loc) {
        final int x = loc.getX();
        final int y = loc.getY();
        final int p = loc.getPlane();
        final Tile N = new Tile(x, y + radius + 1, p);
        final Tile S = new Tile(x, y - radius - 1, p);
        final Tile E = new Tile(x - radius, y, p);
        final Tile W = new Tile(x + radius + 1, y, p);
        return new Area(N, E, S, W);
    }
}