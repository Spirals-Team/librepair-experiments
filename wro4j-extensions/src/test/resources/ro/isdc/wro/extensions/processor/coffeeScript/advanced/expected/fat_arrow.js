(function() {
  var Account;

  Account = function(customer, cart) {
    this.customer = customer;
    this.cart = cart;
    return $('.shopping_cart').bind('click', (function(_this) {
      return function(event) {
        return _this.customer.purchase(_this.cart);
      };
    })(this));
  };

}).call(this);