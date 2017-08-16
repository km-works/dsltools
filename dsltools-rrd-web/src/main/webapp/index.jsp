<%-- File: index.jsp --%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="no-js ie6" > <![endif]-->
<!--[if IE 7 ]>    <html class="no-js ie7" > <![endif]-->
<!--[if IE 8 ]>    <html class="no-js ie8" > <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html class="no-js" > <!--<![endif]-->
<%@page import="kmworks.util.StringUtil, kmworks.util.tuple.*, kmworks.dsltools.rrd.*, java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String version = "2.0.0";
    String asOf = "2017-03-15";
    String _source = StringUtil.nvl(request.getParameter("source"), "").trim();
    String _lang = StringUtil.nvl(request.getParameter("language"), "");
    String ebnfChecked = "", pegChecked = "", checked="checked='checked'";
    if (_lang.equals("EBNF_W3C")) {
        ebnfChecked = checked;
    } else {
        pegChecked = checked;
    }
    List<String> parts = null;
    List<String> names = null;
    String errmsg = "";
    long nanos = 0;
    boolean isOK = true;
    if (_source.length() > 0 && _lang.length() > 0) {
      RRD_Translator rt = new RRD_Translator(_lang, _source);
      rt.translate();
      if (!rt.hasError()) {
        parts = rt.getResult();
        names = rt.getNames();
      } else {
        errmsg = rt.getError();
        isOK = false;
      }
      nanos = rt.getNanoTiming();
    }
    response.setContentType("text/html; charset=UTF-8");
%>
<%@include file="WEB-INF/jspf/license-header.jspf" %>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>km-works.eu | DSLtools &minus; SVG Syntax Diagram Generator</title>
  <meta name="description" content="A webapp that turns any supported, well-formed grammar into SVG syntax diagrams" />
  <meta name="author" content="Christian P. Lerch" />
  <link rel="stylesheet" href="res/css/style.css" />
</head>
<body>
  <div id="pageRoot">
    <header>
      <table>
        <tbody>
          <tr>
            <td><img style="width: 144px; margin-left: 0.5em" src="res/img/dsltools-logo.png" alt="DSLtools Logo" /></td>
            <td style="text-align: center; padding: 5px 10px; width: 100%">
              <h1>SVG Syntax Diagram Generator</h1>
              <span>A free service provided by <a href="#">km-works.eu</a></span>
            </td>
            <td style="padding: 5px 10px; font-size: smaller; white-space:nowrap">
              <span>Version:</span><br />
              <span style="font-weight: bold"><%= version%></span><br />
              <span>Last update:</span><br />
              <span style="font-weight: bold"><%= asOf%></span> 
            </td>
          </tr>
        </tbody>
      </table>
    </header>
    
    <div id="main" role="main" style="margin: 0.5em">
        <h3>Welcome to the DSLtools &minus; SVG Syntax Diagram Generator!</h3>
        <p>You can use this page to turn grammars written in one of the supported input languages into syntax diagrams in SVG graphics format. 
           For help on any input language supported by this tool follow the respective language link.</p> 
        <p>Enter the textual representation of your grammar into the text box below and then click [Go]. 
           Alternatively you can use the [Sample] buttons to paste a sample grammar for the respective language into the text box.</p>
        <form id="form0" action="index.jsp" method="post" accept-charset="UTF-8">
          <p>Choose input language:<br/>
            <input type="radio" name="language" id="EBNF_W3C-lang" value="EBNF_W3C" <%= ebnfChecked %> /> 
            <a href="#" title="EBNF_W3C Help">EBNF - W3C Dialect</a> 
            <input type="button" value="Sample" onclick="pasteSample('EBNF_W3C')"/><br/>
            <input type="radio" name="language" id="PEG-lang" value="PEG" <%= pegChecked %> /> 
            <a href="#" title="PEG Help">Parser Expression Grammar (PEG)</a> 
            <input type="button" value="Sample" onclick="pasteSample('PEG')"/><br/>
          </p>
          <textarea style="font-family: monospace; width: 99%" spellcheck="false"
                    name="source" id="source" rows ="25"><%= _source %></textarea>
          <div style="margin-top: 0.5em; margin-bottom: 0.5em">
            <input type="submit" value="Go"/>
            <input type="reset"/> 
            <input type="button" value="Clear" onclick="clearAll()" />
          </div>
        </form>
            
        <% if (isOK && parts!=null && parts.size()>0) { %>
          <hr/>
          <div style="display: table; width: 100%">
            <div style="display: table-row">
              <span style="display: table-cell; font-weight: bold">Result:</span>
              <span style="display: table-cell; text-align: right">
                <a href="javascript:openPrintWindow()"><img style="margin: 2px; cursor: pointer" 
                   alt="Printer-friendly page" title="Printer-friendly page" src="res/img/printer.gif" /></a>
              </span>
            </div>
          </div>
          <div id="rrd">
            <% for (int i=0; i<parts.size(); i++) { %>
            <p><img <%= names.get(i) != null ? "id=\""+names.get(i)+"\"" : "" %> 
                    src="data:image/svg+xml;base64,<%= StringUtil.encodeBase64(parts.get(i)) %>" />
            </p>
            <% } %>
          </div>
          <p style="font-style: italic; padding-top: 0.5em;">Translated in <%= nanos/1000000 %> msec</p>
        <% } else { %>
          <pre id="errmsg"><%= errmsg %></pre>
        <% } %>
    </div>
    
    <footer>
      <%@include file="WEB-INF/jspf/footer.jspf" %>
    </footer>
  </div>

<div style="display: none">
<pre id="EBNF_W3C-sample">
(* 
 * A grammar of the W3C EBNF dialect, as supported by this tool - expressed in its own syntax
 *)
Grammar = Production { Production } ;

</pre>
<pre id="PEG-sample">
/* 
 * A (partial) grammar of the PEG language, as supported by this tool - expressed in its own syntax
 */
Grammar     &lt;-  w Definition+ EOI
Definition  &lt;-  Identifier DEFINES Expression
Expression  &lt;-  Sequence (ALT Sequence)* ALT?
Sequence    &lt;-  Term+
Term        &lt;-  ( AND / NOT )? Primary ( OPTION / STAR / PLUS / )
Primary     &lt;-  Identifier !DEFINES / LPAREN Expression RPAREN / Terminal
Terminal    &lt;-  StringLiteral / LBRACK CodePointRange+ RBRACK / DOT
CodePointRange &lt;- CodePoint '-' CodePoint / CodePoint
CodePoint   &lt;-  EscapeSequence / ![\\\-\]] DOT
DEFINES     &lt;- "&lt;-" w
ALT         &lt;- '/' w
AND         &lt;- '&' w
NOT         &lt;- '!' w
OPTION      &lt;- '?' w
STAR        &lt;- '*' w
PLUS        &lt;- '+' w
LPAREN      &lt;- '(' w
RPAREN      &lt;- ')' w
LBRACK      &lt;- '[' w
RBRACK      &lt;- ']' w
DOT         &lt;- '.' w
EOI         &lt;- !.
</pre>
</div>

<div style="display:none">
  <form id="form1" action="print.jsp" method="post" accept-charset="UTF-8" target="_blank">
    <textarea name="printsrc" id="printsrc"><%= _source %></textarea>
    <input type="text" name="printlang" id="printlang" value=""/>
  </form>
</div>

<script src="res/js/modernizr.min.js"></script>
<script src="res/js/svg/svg.js" data-path="res/js/svg" data-htc-filename="svg-htc.jsp"></script>
<script src="res/js/svg/shimvg.js" data-debug="true"></script>
<script src="res/js/index.js" data-debug="true"></script>

</body>
</html>
