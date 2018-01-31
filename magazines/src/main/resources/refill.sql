delete from author;
delete from article;
delete from magazine;
delete from publisher;

insert into publisher (publisher_id, name) values
  (01, 'Time Inc.'),
  (02, 'Hearst Magazines'),
  (03, 'Condé Nast Publications'),
  (04, 'Meredith'),
  (05, 'American Media Inc.'),
  (06, 'Wenner Media'),
  (07, 'The Reader''s Digest Assiciation'),
  (08, 'Bauer Publishing'),
  (09, 'Bonnier'),
  (10, 'Rodale'),
  (11, 'National Geographic Society'),
  (12, 'Martha Stewart Living Omnimedia');
insert into magazine (magazine_id, publisher_id, name) values
  (01, 01, 'People'),
  (02, 01, 'Sports Illustrated'),
  (03, 06, 'US Weekly'),
  (04, 04, 'Better Homes and Gardens'),
  (05, 01, 'Time'),
  (06, 02, 'Cosmopolitan'),
  (07, 02, 'Good Housekeeping'),
  (08, 07, 'Reader''s Digest'),
  (09, 11, 'National Geographic'),
  (10, 04, 'Family Circle'),
  (11, 05, 'Star Magazine'),
  (12, 01, 'Entertainment Weekly');
insert into article (article_id, magazine_id, name) values
  (01, 01, 'Bella Hadid Slays All Day in Paris, Plus Ben Affleck, Diane Kruger & More'),
  (02, 02, 'NFL Replay Critics: Stop the Obsession, and the Unrealistic Expectations for Officials'),
  (03, 03, 'Kim Kardashian Is a ‘Very Hands-On Mom’ With Baby Chicago'),
  (04, 04, 'How to Care for Orchids'),
  (05, 05, 'Jeff Sessions Was Just Questioned in the Russia Investigation. Is President Trump Next?'),
  (06, 06, 'I Said Goodbye to Practically All My Clothing to Live and Work Out of a 200-Square Foot Trailer'),
  (07, 07, 'These Are the Outfits the U.S. Athletes Will Wear at the 2018 Winter Olympics'),
  (08, 08, 'Jennifer Aniston Has Lived with This Common Disorder for Years'),
  (09, 09, 'How China Plans to Feed 1.4 Billion Growing Appetites'),
  (10, 10, 'Weight Loss Journey: How One Woman Lost Over 100 Pounds'),
  (11, 11, 'Marriage From Hell! Farrah Abraham’s Mom Accuses Ex-Husband Of Drug Use & Infidelity'),
  (12, 12, 'This Is Us: Milo Ventimiglia warns Jack''s death will be an ''absolute soul-crushing event''');
insert into author (author_id, article_id, name) values
  (01, 01, 'Grace Gavilanes'),
  (02, 02, 'Peter King'),
  (03, 03, 'Sarah Hearon'),
  (04, 04, 'Eric Liskey'),
  (05, 05, 'Eric Tucker'),
  (06, 06, 'Rachel Torgerson'),
  (07, 07, 'Caroline Picard'),
  (08, 08, 'Bryce Gruber'),
  (09, 09, 'Tracie McMillan'),
  (10, 10, 'Kaitlyn Pirie'),
  (11, 11, 'Teresa Roca'),
  (12, 12, 'Dan Snierson');
