package com.hakimen.nodeImageEditor.core.containers.modifierNodes;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.engine.core.utils.Window;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.notifications.notification.WarningNotification;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

import static com.hakimen.nodeImageEditor.core.notifications.NotificationHandler.NOTIFY_SHORT;

public class BlurNodeContainer extends NodeContainer {
    static final String BLUR_STRENGTH = "Blur Strength";
    static final String IMAGE = "Image";
    static final String OUTPUT = "Output Image";
    int lastValue = 0;
    BufferedImage lastImage = new BufferedImage(1,1,2);
    public BlurNodeContainer(float x, float y) {
        super(x, y, "Blur Node");
        sx += 64;
        readerNodes.put(BLUR_STRENGTH, new NumberNode(uuid, true, 0f));
        readerNodes.put(IMAGE, new ImageNode(uuid, true));
        writerNodes.put(OUTPUT, new ImageNode(uuid, false));
    }

    @Override
    public void render() {
        super.render();
        if (writerNodes.get(OUTPUT) instanceof ImageNode node) {
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x, y + sy, sx, sx, 16f, 16f));
            RenderUtils.DrawImage(x, y + sy, sx, sx, node.getValue());
            RenderUtils.ClipShape(null);
        }
    }

    @Override
    public void update() {
        super.update();
        if (readerNodes.get(BLUR_STRENGTH) instanceof NumberNode blur &&
                readerNodes.get(IMAGE) instanceof ImageNode image &&
                writerNodes.get(OUTPUT) instanceof ImageNode node) {
            if (lastValue != blur.getValue().intValue() && lastImage != image.getValue()) {
                if(image.getValue() != null){
                    NodeEditor.handler.push(new WarningNotification("Processing","This may take a while", NOTIFY_SHORT));

                    var buff = new BufferedImage(image.getValue().getWidth(),image.getValue().getHeight(),2);

                    float weight = 1.0f / (blur.getValue().intValue() * blur.getValue().intValue());
                    float[] data = new float[blur.getValue().intValue() * blur.getValue().intValue()];

                    Arrays.fill(data, weight);

                    Kernel kernel = new Kernel(blur.getValue().intValue(), blur.getValue().intValue(), data);
                    var bf = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null).filter(image.getValue(), buff);
                    node.setValue(bf);
                    lastValue = blur.getValue().intValue();
                    lastImage = bf;
                }
            }
        }
    }
}
