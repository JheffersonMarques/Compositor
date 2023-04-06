package com.hakimen.nodeImageEditor.core;

import java.awt.*;
import java.io.Serializable;

public class Node<T>{
    boolean isReader;
    T value;

    Color nodeColor;
    NodeContainer container;

    public NodeContainer getContainer() {
        return container;
    }

    public void setContainer(NodeContainer container) {
        this.container = container;
    }

    public Node(NodeContainer container, boolean isReader, T value) {
        this.container = container;
        this.isReader = isReader;
        this.value = value;
    }

    public Node(NodeContainer container,boolean isReader) {
        this.container = container;
        this.isReader = isReader;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isReader() {
        return isReader;
    }

    public void setReader(boolean reader) {
        isReader = reader;
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(Color nodeColor) {
        this.nodeColor = nodeColor;
    }
}
