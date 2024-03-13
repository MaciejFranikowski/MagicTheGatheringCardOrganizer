INSERT INTO `box` (id, name, location, color) VALUES (2,'second box', 'in the cupboard', 'black');
INSERT INTO `deck_card` (box_id,name,deck_name) VALUES (2,'Force of will', 'Sultai Beanstalk');
INSERT INTO `loan_card` (box_id,name,owner_firstname, owner_lastname) VALUES (2,'Daze', 'Josh', 'Smith');
INSERT INTO `collection_card` (box_id,name,set_name) VALUES (2,'Plains', 'Beta C');