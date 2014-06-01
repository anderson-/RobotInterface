/* Generated By:JavaCC: Do not edit this line. SendBytesArgumentDecoder.java */
package robotinterface.plugin.cmdpack.low.decoder;

import java.nio.ByteBuffer;
import java.util.Vector;
import java.math.BigInteger;
import org.nfunk.jep.JEP;

/** Simple brace matcher. */
public class SendBytesArgumentDecoder implements SendBytesArgumentDecoderConstants {

  final public Object decode(ByteBuffer msg, JEP parser) throws ParseException {
  Object o;
    o = expression(parser);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 17:
      o = suffix(o, parser, msg);
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    if (o instanceof String){
        //System.out.println("string:" + o);
        parser.parseExpression((String) o);
        o = parser.getValueAsObject();
        //System.out.println("result:" + o);
    }
    {if (true) return o;}
    throw new Error("Missing return statement in function");
  }

  final private String expression(JEP parser) throws ParseException {
  boolean invertBytes = false;
  String statement = "";
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 16:
      jj_consume_token(16);
        invertBytes = true;
      break;
    default:
      jj_la1[1] = jj_gen;
      ;
    }
    statement = evaluableThing();
    parser.parseExpression(statement);
    Object value = parser.getValueAsObject();
    StringBuilder sb = new StringBuilder();
    if (invertBytes){
        if (value instanceof Number) {
            double d = ((Number) value).doubleValue();
            sb.append("[").append(d).append("]");
        } else if (value instanceof String) {
            sb.append("[");
            boolean first = true;
            char [] array = ((String)value).toCharArray();
            for (int i = array.length-1; i >= 0; i--){
                char c = array[i];
                if (first){
                    sb.append(((byte)c));
                    first = false;
                } else {
                    sb.append(",").append(((byte)c));
                }
            }
            sb.append("]");
        } else if (value instanceof Vector) {
            Vector v = (Vector) value;
            sb.append("[");
            boolean first = true;
            for (int i = v.size()-1; i >= 0; i--){
                if (first){
                    sb.append(v.get(i));
                    first = false;
                } else {
                    sb.append(",").append(v.get(i));
                }
            }
            sb.append("]");
        } else {
            //System.out.println("ND:" + value);
            {if (true) return "";}
        }
        //System.out.println("-:" + value + " -> '" + sb.toString()+ "'");
        {if (true) return sb.toString();}
    } else {
        if (value instanceof Number) {
            double d = ((Number) value).doubleValue();
            sb.append("[").append(d).append("]");
        } else if (value instanceof String) {
            sb.append("[");
            boolean first = true;
            for (char c : ((String)value).toCharArray()){
                if (first){
                    sb.append(((byte)c));
                    first = false;
                } else {
                    sb.append(",").append(((byte)c));
                }
            }
            sb.append("]");
        } else {
            sb.append("[").append(statement).append("]");
        }
        //System.out.println("p:" + statement + " -> " + sb.toString());
        {if (true) return sb.toString();}
    }
    throw new Error("Missing return statement in function");
  }

  final private String evaluableThing() throws ParseException {
    String statement = "";
    Token t;
    Object o;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
    case STRING_LITERAL:
    case IDENTIFIER:
      statement = literal();
    {if (true) return statement;}
      break;
    case LBRACE:
      statement = codeExpression();
    {if (true) return statement;}
      break;
    case 17:
      jj_consume_token(17);
    StringBuilder sb = new StringBuilder();
    sb.append("[");
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
      case STRING_LITERAL:
      case IDENTIFIER:
      case LBRACE:
      case 17:
        statement = evaluableThing();
      sb.append(statement);
        label_1:
        while (true) {
          if (jj_2_1(2)) {
            ;
          } else {
            break label_1;
          }
          jj_consume_token(18);
          statement = evaluableThing();
        sb.append(",").append(statement);
        }
        break;
      default:
        jj_la1[2] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 18:
        jj_consume_token(18);
        break;
      default:
        jj_la1[3] = jj_gen;
        ;
      }
      jj_consume_token(19);
    sb.append("]");
    {if (true) return sb.toString();}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private String literal() throws ParseException {
  Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      t = jj_consume_token(INTEGER_LITERAL);
    {if (true) return t.image;}
      break;
    case STRING_LITERAL:
      t = jj_consume_token(STRING_LITERAL);
    {if (true) return t.image;}
      break;
    case IDENTIFIER:
      t = jj_consume_token(IDENTIFIER);
    {if (true) return t.image;}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final private Object suffix(Object o, JEP parser, ByteBuffer msg) throws ParseException {
    String statement = "";
    int a = 0, b = 8;
    Object value;
    jj_consume_token(17);
    statement = evaluableThing();
    parser.parseExpression(statement);
    value = parser.getValueAsObject();
    if (value instanceof Number) {
        double d = ((Number) value).doubleValue();
        a = (int) d;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 18:
      jj_consume_token(18);
      statement = evaluableThing();
    parser.parseExpression(statement);
    value = parser.getValueAsObject();
    if (value instanceof Number) {
        double d = ((Number) value).doubleValue();
        b = (int) d;
    }
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    jj_consume_token(19);
    double d = 0;
    //System.out.println("Parsing:" + o);
    parser.parseExpression(o.toString());
    o = parser.getValueAsObject();
    if (o instanceof Number) {
        d = ((Number) o).doubleValue();
    } else if (o instanceof Vector){
        Vector v = (Vector) o;
        if (v.size() > a){
            o = v.get(a);
            if (o instanceof Number) {
                d = ((Number) o).doubleValue();
            }
        }
    }
    //System.out.println("d:" + d);

    int umax = (int)Math.pow(2,b);
    int min = (int)-Math.pow(2,b-1);
    int max = (int)Math.pow(2,b-1)-1;

    if (d >= 0){
        while(d > max){
            d -= umax;
        }
    } else {
        while(d < min){
            d += umax;
        }
    }

    //System.out.println("cast["+b+":"+max+","+min+"]:" + d);

    BigInteger bi = new BigInteger("" + ((long)d), 10);
    //System.out.println("bi:" + bi);
    byte[] array = bi.toByteArray();
    //for (int i = 0; i < array.length; i++){
         //System.out.println(i+":" + array[i]);
    //}
    if (a >= 0 && a < array.length){
        {if (true) return (Byte) array[a];}
    } else {
        {if (true) return (Byte) array[0];}
    }
    throw new Error("Missing return statement in function");
  }

  final private String codeExpression() throws ParseException {
  Token t;
    jj_consume_token(LBRACE);
    t = jj_consume_token(RBRACE);
    {if (true) return t.image.substring(0, t.image.length() - 1);}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3R_5() {
    if (jj_scan_token(17)) return true;
    return false;
  }

  private boolean jj_3R_4() {
    if (jj_3R_7()) return true;
    return false;
  }

  private boolean jj_3R_10() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3R_2() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_3()) {
    jj_scanpos = xsp;
    if (jj_3R_4()) {
    jj_scanpos = xsp;
    if (jj_3R_5()) return true;
    }
    }
    return false;
  }

  private boolean jj_3R_3() {
    if (jj_3R_6()) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_scan_token(18)) return true;
    if (jj_3R_2()) return true;
    return false;
  }

  private boolean jj_3R_9() {
    if (jj_scan_token(STRING_LITERAL)) return true;
    return false;
  }

  private boolean jj_3R_7() {
    if (jj_scan_token(LBRACE)) return true;
    return false;
  }

  private boolean jj_3R_6() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_8()) {
    jj_scanpos = xsp;
    if (jj_3R_9()) {
    jj_scanpos = xsp;
    if (jj_3R_10()) return true;
    }
    }
    return false;
  }

  private boolean jj_3R_8() {
    if (jj_scan_token(INTEGER_LITERAL)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public SendBytesArgumentDecoderTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x20000,0x10000,0x22620,0x40000,0x22620,0x620,0x40000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SendBytesArgumentDecoder(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SendBytesArgumentDecoder(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SendBytesArgumentDecoderTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SendBytesArgumentDecoder(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new SendBytesArgumentDecoderTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SendBytesArgumentDecoder(SendBytesArgumentDecoderTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SendBytesArgumentDecoderTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        exists = true;
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.add(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[20];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 20; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
