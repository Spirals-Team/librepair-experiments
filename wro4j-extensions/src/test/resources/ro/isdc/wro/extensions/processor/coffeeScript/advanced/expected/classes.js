(function() {
  var Animal, Horse, Snake, sam, tom,
    extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  Animal = (function() {
    function Animal(name) {
      this.name = name;
    }

    Animal.prototype.move = function(meters) {
      return alert(this.name + " moved " + meters + "m.");
    };

    return Animal;

  })();

  Snake = (function(superClass) {
    extend(Snake, superClass);

    function Snake() {
      return Snake.__super__.constructor.apply(this, arguments);
    }

    Snake.prototype.move = function() {
      alert("Slithering...");
      return Snake.__super__.move.call(this, 5);
    };

    return Snake;

  })(Animal);

  Horse = (function(superClass) {
    extend(Horse, superClass);

    function Horse() {
      return Horse.__super__.constructor.apply(this, arguments);
    }

    Horse.prototype.move = function() {
      alert("Galloping...");
      return Horse.__super__.move.call(this, 45);
    };

    return Horse;

  })(Animal);

  sam = new Snake("Sammy the Python");

  tom = new Horse("Tommy the Palomino");

  sam.move();

  tom.move();

}).call(this);