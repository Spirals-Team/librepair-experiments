(function() {
  var globals, name;

  globals = ((function() {
    var results;
    results = [];
    for (name in window) {
      results.push(name);
    }
    return results;
  })()).slice(0, 10);

}).call(this);