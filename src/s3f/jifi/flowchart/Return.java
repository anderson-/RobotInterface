/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3f.jifi.flowchart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import s3f.jifi.core.Command;
import s3f.jifi.core.interpreter.ExecutionException;
import s3f.jifi.core.interpreter.ResourceManager;
import s3f.jifi.core.parser.parameterparser.Argument;
import static s3f.jifi.flowchart.DummyBlock.createSimpleBlock;
import s3f.magenta.GraphicObject;
import s3f.magenta.sidepanel.Item;

/**
 *
 * @author antunes2
 */
public class Return extends Procedure {

    private static Color myColor = Color.BLACK;

    public Return() {
        addLineArg(0, Argument.EXPRESSION, "");
    }

    private GraphicObject resource = null;
    private Object returnValue = null;

    @Override
    public GraphicObject getDrawableResource() {
        if (resource == null) {
            resource = createSimpleBlock(this, " return " + getArg(0) + "; ", Color.black, myColor, 0);
        }
        return resource;
    }

    @Override
    public boolean perform(ResourceManager rm) throws ExecutionException {
        returnValue = super.execute(getArg(0).getStringValue(), rm);
        return true;
    }

    @Override
    public Command step(ResourceManager rm) throws ExecutionException {
        Command loop = getParent();
        while (!(loop instanceof Function)) {
            loop = loop.getParent();
        }

//        if (loop instanceof While){
//            ((Block)loop).breakLoop(true);
//            return ((Block)loop).getNext();
//        } else 
        if (loop instanceof Block) {
            ((Block) loop).breakLoop(true);
            return ((Block) loop).getEnd();
        }
        return loop.getNext();
    }

    @Override
    public Item getItem() {

        Area myShape = new Area();
        Polygon tmpPoly = new Polygon();
        tmpPoly.addPoint(10, 0);
        tmpPoly.addPoint(20, 10);
        tmpPoly.addPoint(10, 20);
        tmpPoly.addPoint(0, 10);
        myShape.add(new Area(tmpPoly));
        myShape.subtract(new Area(new Ellipse2D.Double(5, 5, 10, 10)));

        tmpPoly.reset();
        tmpPoly.addPoint(18, 0);
        tmpPoly.addPoint(20, 2);
        tmpPoly.addPoint(2, 20);
        tmpPoly.addPoint(0, 18);
        myShape.add(new Area(tmpPoly));

        myShape.add(new Area(new Ellipse2D.Double(7, 7, 6, 6)));
        return new Item("Return", myShape, myColor, "Interrompe o laço de repetição quando é executado");
    }

    @Override
    public void drawLines(Graphics2D g) {

    }

    @Override
    public Object createInstance() {
        return new Return();
    }

    @Override
    public void toString(String ident, StringBuilder sb) {
        sb.append(ident).append("return ").append(getArg(0)).append(";\n");
    }

    public Object getValue() {
        return returnValue;
    }
}
