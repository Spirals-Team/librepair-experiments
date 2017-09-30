(function() {
  var filename, fn, i, len;

  fn = function(filename) {
    return fs.readFile(filename, function(err, contents) {
      return compile(filename, contents.toString());
    });
  };
  for (i = 0, len = list.length; i < len; i++) {
    filename = list[i];
    fn(filename);
  }

}).call(this);