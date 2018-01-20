
SELECT * FROM product WHERE id IN (SELECT product_id FROM stock);
SELECT * FROM stock WHERE id IN (SELECT product_id FROM stock);

SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name='Joy Of Java';
SELECT product.product_name, stock.amount, stock.stock_id FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id='STS1';
SELECT product.product_name, product.product_id, product.product_description, stock.amount, stock.stock_id, stock.stock_description FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id='STS1';