Insert into Users (username, name, age, gender, town, password, balance) values ('testUser1', 'test1',18,'Female','Sofia','123',13);
Insert into Users (username, name, age, gender, town, password, balance) values ('testUser2', 'test2',18,'Male','Sofia','1234',13);

INSERT INTO Charities (name, description, required_amount, required_participants, donated_amount, participants, completed, path_to_thumbnail, owner_username)
VALUES ('TestCharity2','test2',1000,0,30,0,false,'/','testUser2');

INSERT INTO Donations (user_username, charity_id, place_date, amount) VALUES ('testUser2',2,now(),30);