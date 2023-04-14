package com.hakimen.nodeImageEditor.core.containers.utilityNodes.spliters;

import com.hakimen.nodeImageEditor.core.Node;
import com.hakimen.nodeImageEditor.core.NodeContainer;
import com.hakimen.nodeImageEditor.core.node.NumberNode;

public class NumberSplitterNodeContainer extends NodeContainer {

    static final String COUNT = "Count";

    int lastCount = 0;

    static final String INPUT = "Number Input";
    public NumberSplitterNodeContainer(float x, float y) {
        super(x, y, "Number Splitter Node");
        this.readerNodes.put(COUNT,new NumberNode(uuid,true));
        this.readerNodes.put(INPUT,new NumberNode(uuid,true));
    }

    @Override
    public void update() {
        if(readerNodes.get(COUNT) instanceof NumberNode node){
            if(node.getValue().intValue() != lastCount){
                writerNodes.clear();
                for (int i = 0; i < node.getValue().intValue(); i++) {
                    try {
                        writerNodes.put("Output "+i, new NumberNode(uuid,false));
                    } catch (Exception e){

                    }
                }
                lastCount = node.getValue().intValue();
            }
        }

        super.update();
        if(this.readerNodes.get(INPUT) instanceof NumberNode node){
            for (int i = 0; i < writerNodes.values().size(); i++) {
                var writer = writerNodes.values().stream().toList().get(i);
                if(writer instanceof NumberNode out){
                    out.setValue(node.getValue());
                }
            }
        }
    }

    @Override
    public void render() {
        super.render();
    }
}
