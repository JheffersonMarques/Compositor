package com.hakimen.nodeImageEditor.core;

import com.hakimen.engine.core.utils.RenderUtils;
import com.hakimen.nodeImageEditor.utils.NodeColorsUtils;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class NodeContainer implements Serializable{
    public float x,y,sx,sy;
    public String name;
    public UUID uuid;
    public Map<String,Node<?>> readerNodes = new LinkedHashMap<>();
    public Map<String,Node<?>> writerNodes = new LinkedHashMap<>();

    public NodeContainer(float x, float y, String name) {
        this.x = x;
        this.y = y;
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.sx = name.length() * 8 + 64;
    }

    public NodeContainer(float x, float y, String name, UUID uuid) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.uuid = uuid;
        this.sx = name.length() * 8 + 64;
    }

    public UUID getUuid() {
        return uuid;
    }

    public NodeContainer setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void render(){
        RenderUtils.FillRoundedRect(x,y,sx,sy, 16f,16f, Color.DARK_GRAY);
        RenderUtils.FillRoundedRect(x,y,sx,40f, 16f,16f, Color.GRAY);
        RenderUtils.DrawString((int)(x+sx-24),(int)(y + 14), Color.WHITE,"⛌");

        RenderUtils.DrawString((int)x + 8,(int)y + 14, Color.WHITE, name);
        readerNodes.forEach((k,v)->{
            float circleX = x-2;
            float circleY = y + (readerNodes.keySet().stream().toList().indexOf(k)-1) * 24 + 96;
            RenderUtils.DrawCircle(circleX,circleY,4, NodeColorsUtils.nodeColors.get(v.getClass()));
            RenderUtils.DrawString((int)x + 8, (int)circleY - 4,Color.WHITE,k);
        });
        writerNodes.forEach((k,v)->{
            float circleX = x + sx - 2;
            float circleY = y + (writerNodes.keySet().stream().toList().indexOf(k)-1) * 24 + 96;

            RenderUtils.DrawCircle(circleX,circleY,4,NodeColorsUtils.nodeColors.get(v.getClass()));
            RenderUtils.DrawString((int)(x + sx - RenderUtils.g.getFontMetrics().stringWidth(k) - 8),(int)circleY - 4,Color.WHITE,k);
        });
    }


    public void update(){
        if(readerNodes.size() > writerNodes.size()){
            this.sy = 64 + readerNodes.size() * 24 + 8;
        }else{
            this.sy = 64 + writerNodes.size() * 24 + 8;
        }
    }
}
