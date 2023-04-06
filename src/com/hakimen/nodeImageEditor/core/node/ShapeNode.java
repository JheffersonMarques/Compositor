package com.hakimen.nodeImageEditor.core.node;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;

import java.awt.*;

public class ShapeNode extends Node<Shape> {
    public ShapeNode(NodeContainer container, boolean isReader, Shape value) {
        super(container, isReader, value);
        setNodeColor(Color.MAGENTA.darker());
    }

    public ShapeNode(NodeContainer container, boolean isReader) {
        super(container, isReader);
        setNodeColor(Color.MAGENTA.darker());
    }
}
