package com.viettel.documentdigitization.entity;

import com.aspose.words.CompositeNode;
import com.aspose.words.Node;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@lombok.Data
public class Data {
    private List<CustomNode> nodeCollection;

    public static class CustomNode {
        public String text;
        public String type;
        public List<CustomNode> children = new ArrayList<>();
    }

    public Data(NodeCollection nodes) throws NoSuchFieldException, IllegalAccessException {
        Iterator<Node> iterator = nodes.iterator();
        nodeCollection = new ArrayList<>();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            nodeCollection.add(traverse(node));
            System.out.println(node.getClass());
        }
    }

    public CustomNode traverse(Node node) throws NoSuchFieldException, IllegalAccessException {
        CustomNode customNode = new CustomNode();
        customNode.text = node.getText();
        customNode.type = NodeType.getName(node.getNodeType());

        if (node instanceof CompositeNode<?>) {
            CompositeNode compositeNode = (CompositeNode) node;
            Iterator<Node> iterator = compositeNode.getChildNodes().iterator();
            while (iterator.hasNext()) {
                customNode.children.add(traverse(iterator.next()));
            }
        }

        return customNode;
    }
}
