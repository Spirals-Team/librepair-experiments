(function() {
  var city, futurists, name, ref, ref1, street;

  futurists = {
    sculptor: "Umberto Boccioni",
    painter: "Vladimir Burliuk",
    poet: {
      name: "F.T. Marinetti",
      address: ["Via Roma 42R", "Bellagio, Italy 22021"]
    }
  };

  ref = futurists.poet, name = ref.name, (ref1 = ref.address, street = ref1[0], city = ref1[1]);

}).call(this);