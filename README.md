# MercadoLivre Java SDK

[![Build Status](https://travis-ci.org/igorventurelli/mercadolivre-java-sdk.svg?branch=master)](https://travis-ci.org/igorventurelli/mercadolivre-java-sdk) [![Maintainability](https://api.codeclimate.com/v1/badges/fdae62416424a7dc7d02/maintainability)](https://codeclimate.com/github/igorventurelli/mercadolivre-java-sdk/maintainability) [![Test Coverage](https://api.codeclimate.com/v1/badges/fdae62416424a7dc7d02/test_coverage)](https://codeclimate.com/github/igorventurelli/mercadolivre-java-sdk/test_coverage) [![Join the chat at https://gitter.im/mercadolivre-java-sdk/Lobby](https://badges.gitter.im/mercadolivre-java-sdk/Lobby.svg)](https://gitter.im/mercadolivre-java-sdk/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

This is an unofficial MercadoLivre Java SDK.  
I've created a PR to the [official repo](https://github.com/mercadolibre/java-sdk) but it seems to be no more maintained, so I decided to create my own implementation.

### What does this project do?

This project is a Java Object-Oriented representation of the [MercadoLivre's REST API](http://developers.mercadolibre.com/)  
In the future, you'll be able to do something like that:

    Product product = new MercadoLivreProduct();
    product.setTitle("Title");
    product.setDescription("Description");
    product.setPrice(10.0);
    product.setQuantity(2);
    product.addPicture(new File("C:\pictures\pic1.png"));
    product.addPicture(new File("C:\pictures\pic2.png"));

    MercadoLivre mercadoLivre = new MercadoLivre();
    mercadoLivre.sell(product);

### Why is this project useful?

Well, you don't need to use this SDK if you don't want.  
You can make requests and handle responses from the REST API directly.

But, if you want to write some _fluent Java sintax_ instead of interact with the REST API, you're on the right place :)

### How do I get started?

Everything starts with `MercadoLivre` class.  
This class can manage everything of MercadoLivre's world.

### Where can I get more help, if I need it?

You can open a issue!
