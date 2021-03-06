///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package robotinterface.plugin.cmdpack.util;
//
///**
// *
// * @author antunes
// */
///**
// * @file .java
// * @author Anderson Antunes <anderson.utf@gmail.com>
// *         *seu nome* <*seu email*>
// * @version 1.0
// *
// * @section LICENSE
// *
// * Copyright (C) 2013 by Anderson Antunes <anderson.utf@gmail.com>
// *                       *seu nome* <*seu email*>
// *
// * RobotInterface is free software: you can redistribute it and/or modify it
// * under the terms of the GNU General Public License as published by the Free
// * Software Foundation, either version 3 of the License, or (at your option) any
// * later version.
// *
// * RobotInterface is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
// * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
// * details.
// *
// * You should have received a copy of the GNU General Public License along with
// * RobotInterface. If not, see <http://www.gnu.org/licenses/>.
// *
// */
//package
//
//import robotinterface.robot.connection.Connection;
//
// robotinterface.plugin.cmdpack.begginer;
//
//import java.awt.Color;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.geom.RoundRectangle2D;
//import java.util.Collection;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JComponent;
//import javax.swing.JSpinner;
//import javax.swing.SpinnerNumberModel;
//import org.nfunk.jep.Variable;
//import robotinterface.algorithm.Command;
//import robotinterface.algorithm.parser.FunctionToken;
//import robotinterface.algorithm.procedure.Procedure;
//import robotinterface.drawable.DrawableCommandBlock;
//import robotinterface.drawable.GraphicObject;
//import robotinterface.drawable.MutableWidgetContainer;
//import robotinterface.drawable.TextLabel;
//import robotinterface.drawable.WidgetContainer;
//import robotinterface.drawable.Widget;
//import robotinterface.drawable.graphicresource.GraphicResource;
//import robotinterface.drawable.graphicresource.SimpleContainer;
//import robotinterface.drawable.util.QuickFrame;
//import robotinterface.gui.panels.sidepanel.Classifiable;
//import robotinterface.gui.panels.sidepanel.Item;
//import robotinterface.interpreter.ExecutionException;
//import robotinterface.plugin.cmdpack.util.PrintString;
//import robotinterface.robot.Robot;
//import robotinterface.robot.device.Device;
//import robotinterface.robot.device.HBridge;
//import robotinterface.util.trafficsimulator.Clock;
//
///**
// * Procedimento de mover o robô.
// */
//public class Send extends Procedure implements GraphicResource, Classifiable, FunctionToken<Send> {
//
//    private byte m1, m2;
//    private String var1 = null;
//    private String var2 = null;
//    private HBridge hBridge = null;
//
//    public Send() {
//        m1 = m2 = 0;
//    }
//
//    public Send(int m1, int m2) {
//        super();
//        this.m1 = (byte) m1;
//        this.m2 = (byte) m2;
//        updateProcedure();
//    }
//
//    public byte getM1() {
//        return m1;
//    }
//
//    public void setM1(byte m1) {
//        this.m1 = m1;
//        updateProcedure();
//    }
//
//    public byte getM2() {
//        return m2;
//    }
//
//    public void setM2(byte m2) {
//        this.m2 = m2;
//        updateProcedure();
//    }
//
//    public void updateProcedure() {
//        setProcedure("move(" + ((var1 != null) ? var1 : m1) + "," + ((var2 != null) ? var2 : m2) + ")");
//    }
//
//    @Override
//    public void begin(Robot robot, Clock clock) throws ExecutionException {
//        
//        Connection c = robot.getMainConnection();
//        
//        if (c != null){
//            
//        }
//        
//        hBridge = robot.getDevice(HBridge.class);
//        if (hBridge != null) {
//
//            byte t1 = m1;
//            byte t2 = m2;
//
//            if (var1 != null) {
//                Variable v = getParser().getSymbolTable().getVar(var1);
//                if (v != null && v.hasValidValue()) {
//                    Object o = v.getValue();
//                    if (o instanceof Number) {
//                        Number n = (Number) o;
//                        t1 = n.byteValue();
//                    }
//                }
//            }
//
//            if (var2 != null) {
//                Variable v = getParser().getSymbolTable().getVar(var2);
//                if (v != null && v.hasValidValue()) {
//                    Object o = v.getValue();
//                    if (o instanceof Number) {
//                        Number n = (Number) o;
//                        t2 = n.byteValue();
//                    }
//                }
//            }
//
//            hBridge.setWaiting();
//            hBridge.setFullState(t1, t2);
//        }
//    }
//
//    @Override
//    public boolean perform(Robot r, Clock clock) throws ExecutionException {
//        try {
//            if (hBridge != null && hBridge.isValidRead()) {
////                String deviceState = device.stateToString();
////                if (!deviceState.isEmpty()) {
////                    execute(var + " = " + deviceState);
////                }
//                return true;
//            }
//        } catch (Device.TimeoutException ex) {
////            System.err.println("RE-ENVIANDO hBridge");
//            begin(r, clock);
//        }
//        return false;
//    }
//    private GraphicObject resource = null;
//
//    @Override
//    public GraphicObject getDrawableResource() {
//        if (resource == null) {
//            resource = createDrawableMove(this);
//        }
//        return resource;
//    }
//
//    public static MutableWidgetContainer createDrawableMove(final Move m) {
//
//        final int TEXTFIELD_WIDTH = 80;
//        final int TEXTFIELD_HEIGHT = 25;
//        final int BUTTON_WIDTH = 25;
//        final int INSET_X = 5;
//        final int INSET_Y = 5;
//
//        //HEADER LINE
//
//        int headerHeight = 4 * INSET_Y + 2 * TEXTFIELD_HEIGHT + 20;
//        int headerWidth = 4 * INSET_X + 2 * BUTTON_WIDTH + TEXTFIELD_WIDTH;
//        final WidgetLine headerLine = new WidgetLine(headerWidth, headerHeight) {
//            @Override
//            public void createRow(Collection<Widget> widgets, Collection<TextLabel> labels, final MutableWidgetContainer container, Object data) {
//                labels.add(new TextLabel("Mover:", 20, true));
//
//                final JSpinner spinner1 = new JSpinner();
//                final JSpinner spinner2 = new JSpinner();
//                spinner1.setModel(new SpinnerNumberModel(0, -128, 127, 2));
//                spinner2.setModel(new SpinnerNumberModel(0, -128, 127, 2));
//                JComboBox combobox1 = new JComboBox();
//                JComboBox combobox2 = new JComboBox();
//                boolean num1 = true, num2 = true;
//
//                MutableWidgetContainer.setAutoFillComboBox(combobox1, m);
//                MutableWidgetContainer.setAutoFillComboBox(combobox2, m);
//
//                if (data != null) {
//                    if (data instanceof Move) {
//                        Move m = (Move) data;
//
//                        if (m.var1 != null) {
//                            combobox1.setSelectedItem(m.var1);
//                            num1 = false;
//                        } else {
//                            spinner1.setValue((int) m.m1);
//                        }
//
//                        if (m.var2 != null) {
//                            combobox2.setSelectedItem(m.var2);
//                            num2 = false;
//                        } else {
//                            spinner2.setValue((int) m.m2);
//                        }
//                    }
//                }
//
//                final JButton changeButton1 = new JButton();
//                final JButton changeButton2 = new JButton();
//                ImageIcon icon = new ImageIcon(getClass().getResource("/resources/tango/16x16/actions/system-search.png"));
//                changeButton1.setIcon(icon);
//                changeButton2.setIcon(icon);
//
////                changeButton1.setEnabled(false);
////                changeButton2.setEnabled(false);
//
//                int x = INSET_X;
//                int y = INSET_Y + 40;
//                labels.add(new TextLabel("V1:", x + 5, y));
//
//                x += 26;
//                y -= 18;
//
//                final Widget wspinner1 = new Widget(spinner1, x, y, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
//                final Widget wcombobox1 = new Widget(combobox1, x, y, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
//                widgets.add(wspinner1);
//                widgets.add(wcombobox1);
//
//                x += INSET_Y + TEXTFIELD_WIDTH;
//
//                widgets.add(new Widget(changeButton1, x, y, BUTTON_WIDTH, BUTTON_WIDTH));
//
//                x -= INSET_Y + TEXTFIELD_WIDTH;
//
//                x -= 26;
//                y += 50;
//
//                labels.add(new TextLabel("V2:", x + 5, y));
//
//                x += 26;
//                y -= 18;
//
//                final Widget wspinner2 = new Widget(spinner2, x, y, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
//                final Widget wcombobox2 = new Widget(combobox2, x, y, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
//                widgets.add(wspinner2);
//                widgets.add(wcombobox2);
//
//                x += INSET_Y + TEXTFIELD_WIDTH;
//
//                widgets.add(new Widget(changeButton2, x, y, BUTTON_WIDTH, BUTTON_WIDTH));
//
//                x -= INSET_Y + TEXTFIELD_WIDTH;
//
//
//                changeButton1.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (container.contains(wspinner1)) {
//                            container.removeWidget(wspinner1);
//                            container.addWidget(wcombobox1);
//                        } else {
//                            container.removeWidget(wcombobox1);
//                            container.addWidget(wspinner1);
//                        }
//                    }
//                });
//
//                changeButton2.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (container.contains(wspinner2)) {
//                            container.removeWidget(wspinner2);
//                            container.addWidget(wcombobox2);
//                        } else {
//                            container.removeWidget(wcombobox2);
//                            container.addWidget(wspinner2);
//                        }
//                    }
//                });
//
//                wspinner1.setDynamic(true);
//                wcombobox1.setDynamic(true);
//                wspinner2.setDynamic(true);
//                wcombobox2.setDynamic(true);
//
//                if (num1) {
//                    container.addWidget(wspinner1);
//                } else {
//                    container.addWidget(wcombobox1);
//                }
//
//                if (num2) {
//                    container.addWidget(wspinner2);
//                } else {
//                    container.addWidget(wcombobox2);
//                }
//            }
//
//            @Override
//            public String getString(Collection<Widget> widgets, Collection<TextLabel> labels, MutableWidgetContainer container) {
//
//                StringBuilder sb = new StringBuilder();
//
//                sb.append("move(");
//
//                for (Widget w : widgets) {
//                    if (container.contains(w)) {
//                        JComponent jc = w.getJComponent();
//                        if (jc instanceof JComboBox) {
//                            JComboBox cb = (JComboBox) jc;
//                            Object o = cb.getSelectedItem();
//                            if (o != null) {
//                                sb.append(o.toString());
//                                sb.append(" ");
//                            }
//                        } else if (jc instanceof JSpinner) {
//                            JSpinner s = (JSpinner) jc;
//                            sb.append(s.getValue());
//                            sb.append(" ");
//                        }
//                    }
//                }
//
//                String str = sb.toString().trim().replace(" ", ",") + ")";
//                return str;
//            }
//        };
//
//        DrawableCommandBlock dcb = new DrawableCommandBlock(m, Color.decode("#FF6200")) {
//            {
//                string = m.getProcedure();
//                updateLines();
//            }
//
//            @Override
//            public void updateLines() {
//                clear();
//                if (string.length() <= 1) {
//                    addLine(headerLine, m);
//                } else {
//                    String str = string.substring(string.indexOf("(") + 1, string.indexOf(")"));
//                    updateMove(str, m);
//                    addLine(headerLine, m);
//                }
//                string = getString();
//            }
//        };
//
//        return dcb;
//    }
//
//    @Override
//    public Item getItem() {
//        return new Item("Move", new RoundRectangle2D.Double(0, 0, 20, 20, 5, 5), Color.decode("#80DE71"));
//    }
//
//    @Override
//    public Object createInstance() {
//        return new Move();
//    }
//
//    @Override
//    public String getToken() {
//        return "move";
//    }
//
//    private static void updateMove(String str, Move m) {
//        String[] argv = str.split(",");
//        if (argv.length == 0) {
//            m.m1 = (byte) 0;
//            m.m2 = (byte) 0;
//        } else if (argv.length == 1) {
//            argv[0] = argv[0].trim();
//            if (Character.isLetter(argv[0].charAt(0))) {
//                m.var1 = argv[0];
//                m.var2 = argv[0];
//            } else {
//                int v = Integer.parseInt(argv[0].trim());
//                m.m1 = (byte) v;
//                m.m2 = (byte) v;
//                m.var1 = null;
//                m.var2 = null;
//            }
//        } else if (argv.length == 2) {
//            argv[0] = argv[0].trim();
//            if (Character.isLetter(argv[0].charAt(0))) {
//                m.var1 = argv[0];
//            } else {
//                int v = Integer.parseInt(argv[0].trim());
//                m.m1 = (byte) v;
//                m.var1 = null;
//            }
//
//            argv[1] = argv[1].trim();
//            if (Character.isLetter(argv[1].charAt(0))) {
//                m.var2 = argv[1];
//            } else {
//                int v = Integer.parseInt(argv[1].trim());
//                m.m2 = (byte) v;
//                m.var2 = null;
//            }
//        }
//        m.updateProcedure();
//    }
//
//    @Override
//    public Move createInstance(String args) {
//        Move m = new Move(0, 0);
//        if (!args.isEmpty()) {
//            updateMove(args, m);
//        }
//
//        return m;
//        //return new ParseErrorProcedure(this, args);
//    }
//
//    public static void main(String[] args) {
//        Move p = new Move();
//        Move.updateMove("x", p);
//        p.addBefore(new Procedure("var x, y;"));
//        QuickFrame.applyLookAndFeel();
//        QuickFrame.drawTest(p.getDrawableResource());
//    }
//}
