package com.hakimen.nodeImageEditor.core.containers.utilityNodes.value;

import com.hakimen.engine.core.io.Mouse;
import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.NodeEditor;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.ColorNode;
import com.hakimen.nodeImageEditor.core.node.ImageNode;
import com.hakimen.nodeImageEditor.core.node.NumberNode;
import com.hakimen.nodeImageEditor.core.notifications.notification.Notification;
import com.hakimen.nodeImageEditor.core.notifications.notification.WarningNotification;
import com.hakimen.nodeImageEditor.utils.Collisions;
import com.hakimen.nodeImageEditor.utils.ViewTransformer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.hakimen.nodeImageEditor.core.notifications.NotificationHandler.NOTIFY_NORMAL;

public class ImageNodeContainer extends NodeContainer {

    static final String IMAGE = "Image";
    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    JFileChooser chooser = new JFileChooser("/");
    public ImageNodeContainer(float x, float y) {
        super(x, y, "Image Node");
        this.sx = name.length() * 8 + (4*32);
        writerNodes.put(IMAGE, new ImageNode(uuid,false, new BufferedImage(1,1,2)));
        writerNodes.put(WIDTH, new NumberNode(uuid,false,0));
        writerNodes.put(HEIGHT, new NumberNode(uuid,false,0));
    }



    @Override
    public void update() {
        if(Mouse.mouseButtons[MouseEvent.BUTTON1].pressed){
            if(Collisions.pointToRect(ViewTransformer.transformedMouseX,ViewTransformer.transformedMouseY,x+8,y + 48,64,32)){
                if(writerNodes.get(IMAGE) instanceof ImageNode node &&
                        writerNodes.get(WIDTH) instanceof NumberNode width &&
                        writerNodes.get(HEIGHT) instanceof NumberNode height){
                    chooser.showOpenDialog(null);
                    try {
                        if(chooser.getSelectedFile() != null) {
                            node.setValue(ImageIO.read(chooser.getSelectedFile()));
                            width.setValue(node.getValue().getWidth());
                            height.setValue(node.getValue().getHeight());
                            NodeEditor.handler.push(new Notification("Loaded image", "Loaded image ", chooser.getSelectedFile().getName(), NOTIFY_NORMAL).setImg(node.getValue()));
                        }else{
                            NodeEditor.handler.push(new WarningNotification("Couldn't load image", "No file provided", NOTIFY_NORMAL));
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        }
        super.update();
    }

    @Override
    public void render() {
        super.render();

        RenderUtils.FillRoundedRect(x+8,y + 48,64,32,16,16, Color.DARK_GRAY.darker());
        RenderUtils.DrawString((int)x+6+12,(int)y+42+16,Color.WHITE,"Change");

        if(writerNodes.get(IMAGE) instanceof ImageNode node){
            RenderUtils.ClipShape(new RoundRectangle2D.Float(x,y+sy,sx,sx, 16f,16f));
            RenderUtils.DrawImage(x,y+sy,sx,sx,node.getValue());
            RenderUtils.ClipShape(null);
        }
    }
}
