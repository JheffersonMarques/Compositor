package com.hakimen.nodeImageEditor.core.containers.gradientNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.node.utility.ColorArrayNode;
import com.hakimen.nodeImageEditor.core.node.utility.NumberArrayNode;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class LinearGradientNodeContainer extends NodeContainer {

    static final String FIRST_X = "First X";
    static final String FIRST_Y= "First Y";
    static final String SECOND_X = "Second X";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String SECOND_Y = "Second Y";
    static final String COLORS = "Colors";
    static final String DISTRIBUTION = "Distribution";
    static final String OUTPUT = "Output Image";

    public LinearGradientNodeContainer(float x, float y) {
        super(x, y, "Linear Gradient Node");

        readerNodes.put(FIRST_X,new NumberNode(uuid,true));
        readerNodes.put(FIRST_Y,new NumberNode(uuid,true));
        readerNodes.put(SECOND_X,new NumberNode(uuid,true));
        readerNodes.put(SECOND_Y,new NumberNode(uuid,true));
        readerNodes.put(WIDTH,new NumberNode(uuid,true));
        readerNodes.put(HEIGHT,new NumberNode(uuid,true));
        readerNodes.put(COLORS,new ColorArrayNode(uuid,true));
        readerNodes.put(DISTRIBUTION,new NumberArrayNode(uuid,true));
        writerNodes.put(OUTPUT,new ImageNode(uuid,false));

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
        if(readerNodes.get(FIRST_X) instanceof NumberNode x1 &&
                readerNodes.get(FIRST_Y) instanceof NumberNode y1 &&
                readerNodes.get(SECOND_X) instanceof NumberNode x2 &&
                readerNodes.get(SECOND_Y) instanceof NumberNode y2 &&
                readerNodes.get(WIDTH) instanceof NumberNode width &&
                readerNodes.get(HEIGHT) instanceof NumberNode height &&
                readerNodes.get(COLORS) instanceof ColorArrayNode colors &&
                readerNodes.get(DISTRIBUTION) instanceof NumberArrayNode distributions &&
                writerNodes.get(OUTPUT) instanceof ImageNode outImage){
            var tempImage = new BufferedImage(width.getValue().intValue() | 1,height.getValue().intValue() | 1,2);
            var floats =  new float[distributions.getValue().length];
            for (int i = 0; i < distributions.getValue().length; i++) {
                floats[i] = distributions.getValue()[i].floatValue();
            }
            if(distributions.getValue().length >= 2 && colors.getValue().length >= 2 && distributions.getValue().length == colors.getValue().length){
                var g = tempImage.createGraphics();
                g.setPaint(new LinearGradientPaint(x1.getValue().floatValue(),
                        y1.getValue().floatValue(),
                        x2.getValue().floatValue(),
                        y2.getValue().floatValue(),
                        floats,colors.getValue()));
                g.fill(new Rectangle2D.Float(0,0,width.getValue().floatValue(),height.getValue().floatValue()));
                g.dispose();
            }


            outImage.setValue(tempImage);
        }
    }
}
