describe('html5printshiv', function() {
  this.timeout(10000);
  var iframeWindow;
  var $iframe;

  before(function(done) {
    var url = './iframe.html?id=printshiv';
    $iframe = $('<iframe>');

    $(document.body).append($iframe);

    $iframe
      .css({
        'height': 10,
        'width': 10,
        'position': 'absolute',
        'top': 0,
        'left': 0
      })
      .attr({
        'src': url,
        'id': 'printshiv'
      })
      .on('lockedAndLoaded', function() {
        iframeWindow = $(this)[0].contentWindow;
        iframeWindow.requirejs.config({
          baseUrl: '../src'
        });
        done();
      });

  });

  it('shivs the document', function(done) {
    try {
      iframeWindow.requirejs(['html5printshiv'], function() {
        expect('html5' in iframeWindow).to.be(true);
        expect(iframeWindow.html5.type).to.equal('default print');
        done();
      });
    } catch (e) {
      done(e);
    }
  });

  after(function() {
    $iframe.remove();
    iframeWindow = $iframe = undefined;
  });
});
