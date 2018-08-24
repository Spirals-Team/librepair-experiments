/*jshint node:true*/
var proxyPath = ['/j_logout', '/j_security_check'];

//Generado usando el blueprint `ember g http-proxy /j_security_check http://127.0.0.1:9090/j_security_check`
module.exports = function(app) {
  // For options, see:
  // https://github.com/nodejitsu/node-http-proxy
  var proxy = require('http-proxy').createProxyServer({});

  proxy.on('error', function(err, req) {
    console.error(err, req.url);
  });

  proxyPath.forEach(path => {
    app.use(path, function(req, res, next){
      req.url = path + '/' + req.url;
      proxy.web(req, res, { target: 'http://127.0.0.1:9090' });
    });
  })
};
