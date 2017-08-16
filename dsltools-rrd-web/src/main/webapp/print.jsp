<%-- File: print.jsp --%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="no-js ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]>    <html class="no-js ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]>    <html class="no-js ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html class="no-js" lang="en""> <!--<![endif]-->
<%@page import="kmworks.util.StringUtil, kmworks.dsltools.rrd.*, java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  String _source = StringUtil.nvl(request.getParameter("printsrc"), "").trim();
    String _lang = StringUtil.nvl(request.getParameter("printlang"), "");
    List<String> splitResult = null;
    if (_source.length() > 0 && _lang.length() > 0) {
      RRD_Translator rt = new RRD_Translator(_lang, _source);
      rt.translate();
      if (!rt.hasError()) {
        splitResult = rt.getResult();
      }
    }
    response.setContentType("text/html; charset=UTF-8");
%>
<%@include file="WEB-INF/jspf/license-header.jspf" %>
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <title>km-works.eu | DSLtools &minus; SVG Syntax Diagram Generator  &minus; printer-friendly page</title>
  <meta name="description" content="A webapp that turns any supported, well-formed grammar into SVG syntax diagrams" />
  <meta name="author" content="Christian P. Lerch" />
  <link rel="stylesheet" href="res/css/style.css" />
</head>
<body id="pageRoot">
  <div id="rrd">
    <% for (String result : splitResult) { %>
    <p><img src="data:image/svg+xml;base64,<%= StringUtil.encodeBase64(result) %>" />
    </p>
    <% } %>
  </div>
  
<script src="res/js/modernizr.min.js"></script>
<script src="res/js/svg/svg.js" data-path="res/js/svg" data-htc-filename="svg-htc.jsp"></script>
<script src="res/js/svg/shimvg.js" data-debug="true"></script>
<script src="res/js/index.js" data-debug="true"></script>

</body>
</html>
