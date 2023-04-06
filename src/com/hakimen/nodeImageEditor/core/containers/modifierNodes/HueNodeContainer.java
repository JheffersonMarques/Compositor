package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class HueNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String HUE = "Hue", SATURATION = "Saturation", LUMINANCE = "Luminance";
    static final String OUTPUT = "Output Image";

    public HueNodeContainer(float x, float y) {
        super(x, y, "Hue Node");
        readerNodes.put(IMAGE, new ImageNode(this, true, new BufferedImage(1, 1, 2)));
        readerNodes.put(HUE, new NumberNode(this, true, 0));
        readerNodes.put(SATURATION, new NumberNode(this, true, 0));
        readerNodes.put(LUMINANCE, new NumberNode(this, true, 0));
        writerNodes.put(OUTPUT, new ImageNode(this, false, new BufferedImage(1, 1, 2)));
    }

    @Override
    public void render() {
        super.render();
        if (writerNodes.get(OUTPUT) instanceof ImageNode node) {
            if (node.getValue() != null) {
                RenderUtils.ClipShape(new RoundRectangle2D.Float(x, y + sy, sx, sx, 16f, 16f));
                RenderUtils.DrawImage(x, y + sy, sx, sx, node.getValue());
                RenderUtils.ClipShape(null);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(IMAGE) instanceof ImageNode node &&
                readerNodes.get(HUE) instanceof NumberNode hue &&
                readerNodes.get(SATURATION) instanceof NumberNode saturation &&
                readerNodes.get(LUMINANCE) instanceof NumberNode luminance &&
                writerNodes.get(OUTPUT) instanceof ImageNode out) {
            if (node.getValue() != null && Window.ticks % 20 == 0) {
                var buff = new BufferedImage(node.getValue().getWidth(), node.getValue().getHeight(), 2);
                for (int x = 0; x < node.getValue().getWidth(); x++) {
                    for (int y = 0; y < node.getValue().getHeight(); y++) {
                        Color color = new Color(node.getValue().getRGB(x, y));
                        float[] af = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                        float h = af[0];
                        float s = af[1];
                        float l = af[2];

                        float hueNew = (h + hue.getValue().floatValue()) % 1f;
                        float saturationNew = (s + saturation.getValue().floatValue()) % 1f;
                        float luminanceNew = (l + luminance.getValue().floatValue()) % 1f;

                        buff.setRGB(x, y, Color.getHSBColor(hueNew, saturationNew, luminanceNew).getRGB());
                    }
                }
                out.setValue(buff);
            }
        }
    }
}
