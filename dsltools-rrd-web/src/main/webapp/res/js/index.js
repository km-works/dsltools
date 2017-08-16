  function clearAll() {
    clearInput();
    clearError();
    clearOutput();
  }
  function clearInput() {
    var ta = document.getElementById('source');
    ta.value = '';
    ta.focus();
  }
  function clearError() {
    var errmsg = document.getElementById('errmsg');
    errmsg.removeChild(errmsg.firstChild);
  }
  function clearOutput() {
    var rrd = document.getElementById('rrd');
    rrd.removeChild(rrd.firstChild);
  }
  function pasteSample(lang) {
    clearInput();
    var sampleNode = document.getElementById(lang+'-sample').firstChild;
    var sampleText = sampleNode.nodeValue;
    document.getElementById('source').value = sampleText;
    document.getElementById(lang+'-lang').checked = true;
    document.getElementById('printsrc').value = sampleText;
  }
  function openPrintWindow() {
    document.forms[1].printlang.value = document.querySelector('input[name = "language"]:checked').value;
    document.forms[1].submit();
  }
