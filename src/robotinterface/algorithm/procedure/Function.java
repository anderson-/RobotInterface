/**
 * @file .java
 * @author Anderson Antunes <anderson.utf@gmail.com>
 *         *seu nome* <*seu email*>
 * @version 1.0
 *
 * @section LICENSE
 *
 * Copyright (C) 2013 by Anderson Antunes <anderson.utf@gmail.com>
 *                       *seu nome* <*seu email*>
 *
 * RobotInterface is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * RobotInterface is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * RobotInterface. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package robotinterface.algorithm.procedure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.nfunk.jep.Variable;
import robotinterface.algorithm.Command;
import robotinterface.drawable.Drawable;
import robotinterface.drawable.DrawingPanel;
import robotinterface.drawable.graphicresource.GraphicResource;
import robotinterface.gui.panels.SimulationPanel;

/**
 * Função com *futuro* suporte a argumentos. <### EM DESENVOLVIMENTO ###>
 */
public class Function extends Block implements Drawable {

    public static class Wiring {

        public static final Point2D.Double divider = new Point2D.Double(Double.NaN, Double.NaN);
        public static final Point2D.Double arrow = new Point2D.Double(Double.MAX_VALUE, Double.MIN_VALUE);
        public final Class<? extends Command> type;
        public final Class<? extends Command> parentType;
        public final int level;
        public final Point2D.Double[] points;
        public Command tempCmd = null;

        public Wiring(Class<? extends Command> type, Class<? extends Command> parentType, int level, Point2D.Double[] points) {
            this.type = type;
            this.parentType = parentType;
            this.level = level;
            this.points = points;
        }

        public static Wiring contains(ArrayList<Wiring> wiring, Command c) {
            for (Wiring w : wiring) {
                if (w.type.isInstance(c)) {
                    if (w.parentType == null) {
                        w.tempCmd = c;
                        return w;
                    } else {
                        Command it = c;
                        int i = 0;
                        while (i <= w.level && it != null) {
                            if (i == w.level && w.parentType.isInstance(it)) {
                                w.tempCmd = it;
                                return w;
                            }

                            i++;
                            it = it.getParent();
                        }
                    }
                }
                w.tempCmd = null;
            }
            return null;
        }
    }
    private static HashMap<Command, Rectangle2D.Double> teste;
//    private static HashMap<Class<? extends Command>, Point2D.Double[]> wiring;
    private static ArrayList<Wiring> wiring;
    private static Random randNumGen = new Random(); //para testes

    public Function(String name, List<Variable> args) {
        teste = new HashMap<>();
        wiring = new ArrayList<>();
        randNumGen.setSeed(System.currentTimeMillis());
//        randnum.setSeed(0);

        wiring.add(new Wiring(Block.BlockEnd.class, While.class, 1,
                new Point2D.Double[]{
            new Point2D.Double(0, 3),
            new Point2D.Double(0, 5),
            new Point2D.Double(-3, 5),
            new Point2D.Double(-3, 0),
            new Point2D.Double(-1, 0),
            Wiring.arrow,
            Wiring.divider,
            new Point2D.Double(1, 0),
            new Point2D.Double(3, 0),
            new Point2D.Double(3, 6),
            new Point2D.Double(0, 6),
            new Point2D.Double(0, 7),
            Wiring.arrow
        }));

        wiring.add(new Wiring(Block.BlockEnd.class, If.class, 2,
                new Point2D.Double[]{
            new Point2D.Double(2, 3),
            new Point2D.Double(2, 5),
            new Point2D.Double(0, 5),
            new Point2D.Double(0, 7),
            Wiring.arrow
        }));

        wiring.add(new Wiring(If.class, null, 0, new Point2D.Double[]{}));
        
        wiring.add(new Wiring(If.BlockTrue.class, If.class, 1,
                new Point2D.Double[]{
            new Point2D.Double(0, 0),
            new Point2D.Double(-2, 0),
            new Point2D.Double(-2, 2),
            Wiring.arrow,
            Wiring.divider,
            new Point2D.Double(-2, 3),
            new Point2D.Double(-2, 4),
            Wiring.arrow,
        }));
        
        wiring.add(new Wiring(If.BlockFalse.class, If.class, 1,
                new Point2D.Double[]{
            new Point2D.Double(1, 0),
            new Point2D.Double(2, 0),
            new Point2D.Double(2, 2),
            Wiring.arrow,
            Wiring.divider,
            new Point2D.Double(2, 3),
            new Point2D.Double(2, 4),
            Wiring.arrow,
        }));

    }

    public void ident(double x, double y, double j, double k, double Ix, double Iy, boolean a) {
        Function.ident(this, x, y, j, k, Ix, Iy, a);
    }

    public static void ident(Block b, double x, double y, double j, double k, double Ix, double Iy, boolean a) {

        /*
         * c    -  comando
         * ret  -  retangulo usado para calcular o tamanho do comando (é retornado pela função)
         * j    -  espaçamento entre comandos [px]
         * k    -  indentação [px]
         * Ix   -  direção do fluxograma em x [-1,1]
         * Iy   -  direção do fluxograma em y [-1,1]
         * a    -  indentação simples (ou dupla)
         * 
         */

        double cw = 0;
        double ch = 0;

        double xj = Ix * j;
        double yj = Iy * j;
        double xk = Iy * k;
        double yk = Ix * k;

        Rectangle2D.Double t = getObjectBounds(b);

        if (t != null) {
            cw = t.width;
            ch = t.height;

            double px = x - Iy * (cw / 2);
            double py = y - Ix * (ch / 2);

            if (b instanceof GraphicResource) {
                Drawable d = ((GraphicResource) b).getDrawableResource();

                if (d != null) {
                    d.setObjectLocation(px, py);
                } else {
                    t.x = px;
                    t.y = py;
                }
            } else {
                t.x = px;
                t.y = py;
            }

            x += Ix * (cw + xj);
            y += Iy * (ch + yj);
        }

        Command it = b.start;
        while (it != null) {

            t = getObjectBounds(it);
            if (t != null) {
                cw = t.width;
                ch = t.height;

                double px = x - Iy * (cw / 2);
                double py = y - Ix * (ch / 2);

                if (it instanceof GraphicResource) {
                    Drawable d = ((GraphicResource) it).getDrawableResource();

                    if (d != null) {
                        d.setObjectLocation(px, py);
//                        System.out.println(ch);
                    } else {
                        t.x = px;
                        t.y = py;
                    }
                } else {
                    t.x = px;
                    t.y = py;
                }

                x += Ix * (cw + xj);
                y += Iy * (ch + yj);
            }

            if (it instanceof Block) {
                //ident já cuida do espaçamento do incio do bloco
                x -= Ix * (cw + xj);
                y -= Iy * (ch + yj);
                ident((Block) it, x, y, j, k, Ix, Iy, a);
                Rectangle2D.Double bb = getBounds((Block) it, null, j, k, Ix, Iy, a);
                x += Ix * (bb.width);
                y += Iy * (bb.height);
            } else if (it instanceof If) {
                If i = (If) it;
                double pbtx;
                double pbty;
                double pbfx;
                double pbfy;

                Rectangle2D.Double bfb = getBounds(i.getBlockFalse(), null, j, k, Ix, Iy, a);
                Rectangle2D.Double btb = getBounds(i.getBlockTrue(), null, j, k, Ix, Iy, a);

                if (a) {
                    //true
                    pbtx = 0;
                    pbty = 0;
                    //false
                    pbfx = Iy * (bfb.width / 2 + btb.width / 2 + xk);
                    pbfy = Ix * (bfb.height / 2 + btb.height / 2 + yk);
                } else {
                    //true
                    pbtx = -Iy * (btb.width / 2 + xk);
                    pbty = -Ix * (btb.height / 2 + yk);
                    //false
                    pbfx = Iy * (bfb.width / 2 + xk);
                    pbfy = Ix * (bfb.height / 2 + yk);
                }

                ident(i.getBlockTrue(), x + pbtx, y + pbty, j, k, Ix, Iy, a);
                ident(i.getBlockFalse(), x + pbfx, y + pbfy, j, k, Ix, Iy, a);

                x += Ix * ((bfb.width > btb.width) ? bfb.width : btb.width);
                y += Iy * ((bfb.height > btb.height) ? bfb.height : btb.height);
            }

            //define localização
//            if (it.getCommandName().contains("Move")) {
////                tmpBounds.clear();
//                System.out.println("-> " + t);
////                tmpBounds.add((Rectangle2D.Double) t.clone());
//            }


            it = it.getNext();
        }

    }

    @Deprecated //função de teste
    private static Rectangle2D.Double getObjectBounds(Command c) {
        if (c instanceof GraphicResource) {
            Drawable d = ((GraphicResource) c).getDrawableResource();

            if (d != null) {
                if (teste.containsKey(c)) {
                    teste.remove(c);
                }
                return (Rectangle2D.Double) d.getObjectBouds();
            }
        }
        if (!teste.containsKey(c)) {
            boolean rand = false;
            double w, h;
            if (rand) {
                w = 30;
                h = 30;
            } else {
                w = randNumGen.nextDouble() * 50 + 10;
                h = randNumGen.nextDouble() * 50 + 10;
            }
            teste.put(c, new Rectangle2D.Double(0, 0, w, h));
        }
        return teste.get(c);
    }

//    public static Rectangle2D.Double getBounds (Command c){
//        if (c instanceof GraphicResource) {
//            return ((GraphicResource) c).getDrawableResource().getObjectBouds();
//        }
//        
//        return null;
//    }
    public static Rectangle2D.Double getBounds(Command c, Rectangle2D.Double ret, double j, double k, double Ix, double Iy, boolean a) {

        /*
         * c    -  comando
         * ret  -  retangulo usado para calcular o tamanho do comando (é retornado pela função)
         * j    -  espaçamento entre comandos [px]
         * k    -  indentação [px]
         * Ix   -  direção do fluxograma em x [-1,1]
         * Iy   -  direção do fluxograma em y [-1,1]
         * a    -  indentação simples (ou dupla)
         * 
         */

        double xj = Ix * j;
        double yj = Iy * j;
        double xk = Iy * k;
        double yk = Ix * k;

        if (ret == null) {
            ret = new Rectangle2D.Double();
        }

        ret.setRect(Double.MAX_VALUE, Double.MAX_VALUE, 0, 0);

        //pega o tamnho do comando atual
        Rectangle2D.Double t = getObjectBounds(c);
        if (t != null) {
            ret.x = (t.x < ret.x) ? t.x : ret.x;
            ret.y = (t.y < ret.y) ? t.y : ret.y;

            ret.width += t.width;
            ret.height += t.height;

            ret.width += Ix * j;
            ret.height += Iy * j;
        }

        if (c instanceof Block) {
            Block b = (Block) c;
            Rectangle2D.Double p = new Rectangle2D.Double();
            Command it = b.start;
            while (it != null) {
                p = getBounds(it, p, j, k, Ix, Iy, a);

                ret.x = (p.x < ret.x) ? p.x : ret.x;
                ret.y = (p.y < ret.y) ? p.y : ret.y;

                ret.width = (Iy * p.width > ret.width) ? p.width : ret.width;
                ret.height = (Ix * p.height > ret.height) ? p.height : ret.height;

                ret.width += Ix * p.width;
                ret.height += Iy * p.height;

                it = it.getNext();
            }

        } else if (c instanceof If) {
            If i = (If) c;
            Rectangle2D.Double p = new Rectangle2D.Double();
            //false
            p = getBounds(i.getBlockFalse(), p, j, k, Ix, Iy, a);
            ret.add(p);
            //true
            p = getBounds(i.getBlockTrue(), p, j, k, Ix, Iy, a);
            ret.add(p);
        }
//        
//        if (tmpBounds.size() < 100){
//            tmpBounds.add((Rectangle2D.Double)ret.clone());
//            tmpBoundsName.add(c.getCommandName());
//        }

//        System.out.println(c.getCommandName() + "\t\t-- >\t\t"  + ret);

//        if (c.getCommandName().contains("If")) {
////            tmpBounds.clear();
//            tmpBounds.add((Rectangle2D.Double) ret.clone());
//        }

        return ret;
    }
    public static final int ARR_SIZE = 6;

    public static void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
//        g.setStroke(new BasicStroke(2));

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform o = (AffineTransform) g.getTransform().clone();
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len - ARR_SIZE - 1, 0);
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        at.setToIdentity();
        g.setTransform(o);
    }

    public void wire(double j, double k, double Ix, double Iy, boolean a) {
        myLines.clear();
        myArrows.clear();
        Function.wire(this, myLines, myArrows, j, k, Ix, Iy, a);
    }

    public static void wire(Block A, ArrayList<Line2D.Double> lines, ArrayList<Line2D.Double> arrows, double j, double k, double Ix, double Iy, boolean a) {

        Rectangle2D.Double aBounds = getObjectBounds(A);//(Command)A
        Rectangle2D.Double bBounds = getBounds(A, null, j, k, Ix, Iy, a);//(Block)A
        Rectangle2D.Double cBounds = getObjectBounds(A.getEnd());//A(EndBlock)
        Rectangle2D.Double dBounds;//(Command)It

        Command it = A;
        Wiring w;
        while (it != null) {
            
            if (it != A && it instanceof Block) {
                wire((Block) it, lines, arrows, j, k, Ix, Iy, a);
            } else if (it instanceof If) {
                wire(((If) it).getBlockTrue(), lines, arrows, j, k, Ix, Iy, a);
                wire(((If) it).getBlockFalse(), lines, arrows, j, k, Ix, Iy, a);
            }

            dBounds = getObjectBounds(it);
            w = Wiring.contains(wiring, it);
            if (w != null) {
                if (w.tempCmd != it) {
                    aBounds = getObjectBounds(w.tempCmd);
                    bBounds = getBounds(w.tempCmd, null, j, k, Ix, Iy, a);
                }

                Point2D.Double[] reference = w.points;
                Point2D.Double pts[] = new Point2D.Double[]{new Point2D.Double(), new Point2D.Double()};
                for (int i = 1; i < reference.length; i++) {
                    if (reference[i] == Wiring.divider || reference[i] == Wiring.arrow || reference[i - 1] == Wiring.divider) {
                        continue;
                    }
                    pts[0].setLocation(reference[i - 1]);
                    pts[1].setLocation(reference[i]);

                    for (Point2D.Double p : pts) {

                        switch ((int) p.x) {
                            case -3:
                                p.x = bBounds.x - (j / 2);
                                break;
                            case -2:
                                p.x = dBounds.getCenterX();//*
                                break;
                            case -1:
                                p.x = aBounds.getCenterX() - aBounds.width / 2;
                                break;
                            case 0:
                                p.x = aBounds.getCenterX();
                                break;
                            case 1:
                                p.x = aBounds.getCenterX() + aBounds.width / 2;
                                break;
                            case 2:
                                p.x = dBounds.getCenterX();//*
                                break;
                            case 3:
                                p.x = bBounds.x + bBounds.width + (j / 2);
                                break;
                            default:
                                System.err.println("Parametro inválido, wire");
                        }

                        switch ((int) p.y) {
                            case 0:
                                p.y = aBounds.getCenterY();
                                break;
                            case 1:
                                p.y = cBounds.getCenterY();
                                break;
                            case 2:
                                p.y = dBounds.getMinY();
                                break;
                            case 3:
                                p.y = dBounds.getMaxY();
                                break;
                            case 4:
                                p.y = dBounds.getMaxY() + j;
                                break;
                            case 5:
                                p.y = bBounds.getMaxY() - j * .60;
                                break;
                            case 6:
                                p.y = bBounds.getMaxY() - j * .40;
                                break;
                            case 7:
                                p.y = bBounds.getMaxY();
                                break;
                            default:
                                System.err.println("Parametro inválido, wire");
                        }
                    }
                    if (i + 1 < reference.length && reference[i + 1] == Wiring.arrow) {
                        arrows.add(new Line2D.Double(pts[0], pts[1]));
                    } else {
                        lines.add(new Line2D.Double(pts[0], pts[1]));
                    }
                }

//                if (it instanceof Block) {
//                    wire((Block) it, lines, arrows, j, k, Ix, Iy, a);
//                } else if (it instanceof If) {
//                    wire(((If) it).getBlockTrue(), lines, arrows, j, k, Ix, Iy, a);
//                    wire(((If) it).getBlockFalse(), lines, arrows, j, k, Ix, Iy, a);
//                }
            } else {
                arrows.add(new Line2D.Double(dBounds.getCenterX(), dBounds.getMaxY(), dBounds.getCenterX(), dBounds.getMaxY() + j));
            }

            if (it == A){
                it = A.start;
            } else {
                it = it.getNext();
            }
        }
    }

    public static void appendDCommandsOn(Command c, DrawingPanel p) {
        if (c instanceof GraphicResource) {
//            System.out.println("Adicionado: " + c.getCommandName());
            p.add(((GraphicResource) c).getDrawableResource());
        }

        if (c instanceof Block) {
            Block b = (Block) c;
            Command it = b.start;
            while (it != null) {
                appendDCommandsOn(it, p);
                it = it.getNext();
            }
        } else if (c instanceof If) {
            If i = (If) c;
            appendDCommandsOn(i.getBlockTrue(), p);
            appendDCommandsOn(i.getBlockFalse(), p);
        }
    }

    public void appendDCommandsOn(DrawingPanel p) {
        appendDCommandsOn(this, p);
    }
    Rectangle2D.Double shape = new Rectangle2D.Double();
    static ArrayList<Rectangle2D.Double> tmpBounds = new ArrayList<>();
    static ArrayList<String> tmpBoundsName = new ArrayList<>();

    @Override
    public Shape getObjectShape() {
        return shape;
    }

    @Override
    public Rectangle2D.Double getObjectBouds() {
        return shape;
    }

    @Override
    public void setObjectBounds(double x, double y, double width, double height) {
    }

    @Override
    public void setObjectLocation(double x, double y) {
    }

    @Override
    public int getDrawableLayer() {
        return Drawable.DEFAULT_LAYER;
    }

    @Override
    public void drawBackground(Graphics2D g, DrawingPanel.GraphicAttributes ga, DrawingPanel.InputState in) {
    }
    public ArrayList<Line2D.Double> myLines = new ArrayList<>();
    public ArrayList<Line2D.Double> myArrows = new ArrayList<>();
    public static final BasicStroke dashedStroke = new BasicStroke(2.0f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_MITER,
            10.0f, new float[]{5}, 0.0f);

    @Override
    public void draw(Graphics2D g, DrawingPanel.GraphicAttributes ga, DrawingPanel.InputState in) {
        if (in.isKeyPressed(KeyEvent.VK_1)) {
            Function.ident(this, 0, 0, 10, 100, 1, 0, true);
            myLines.clear();
            myArrows.clear();
            Function.wire(this, myLines, myArrows, 50, 50, 0, 1, true);
        } else if (in.isKeyPressed(KeyEvent.VK_2)) {
            Function.ident(this, 0, 0, 10, 100, 0, 1, true);
            myLines.clear();
            myArrows.clear();
            Function.wire(this, myLines, myArrows, 50, 50, 0, 1, true);
        } else if (in.isKeyPressed(KeyEvent.VK_3)) {
            Function.ident(this, 0, 0, 10, 100, 1, 1, true);
            myLines.clear();
            myArrows.clear();
            Function.wire(this, myLines, myArrows, 50, 50, 0, 1, true);
        }

        g.setColor(Color.BLUE);

        g.setStroke(new BasicStroke(2));//dashedStroke);

        for (Line2D.Double l : myArrows) {
//            g.setStroke(new BasicStroke(2));
            drawArrow(g, (int) l.x1, (int) l.y1, (int) l.x2, (int) l.y2);
        }

        for (Line2D.Double l : myLines) {
//            g.setStroke(new BasicStroke(2));
            g.draw(l);
        }
        
        g.setColor(Color.gray);

        for (Command c : teste.keySet()) {
            g.setColor(Color.LIGHT_GRAY);
            Rectangle2D.Double r = teste.get(c);
            g.fill(r);
            g.setColor(Color.BLACK);
            g.drawString(c.getCommandName(), (int) r.getCenterX(), (int) r.getCenterY());
        }

        for (int i = 0; i < tmpBoundsName.size(); i++) {
            g.setColor(Color.BLUE);
            Rectangle2D.Double r = tmpBounds.get(i);
            g.draw(r);
            g.drawString(tmpBoundsName.get(i), (int) r.getCenterX(), (int) r.getCenterY());
        }

        for (Rectangle2D.Double r : tmpBounds) {
            g.setColor(Color.BLUE);
            g.draw(r);
        }

        
    }

    @Override
    public void drawTopLayer(Graphics2D g, DrawingPanel.GraphicAttributes ga, DrawingPanel.InputState in) {
    }
}