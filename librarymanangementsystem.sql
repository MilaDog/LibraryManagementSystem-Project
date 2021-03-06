CREATE TABLE IF NOT EXISTS books(BookID TEXT, Title TEXT, Authors TEXT, Genres TEXT, ISBN10 TEXT, ISBN13 TEXT, Amount INT, PRIMARY KEY(BookID));
CREATE TABLE IF NOT EXISTS registered_users(UserID TEXT, First_Name TEXT, Surname TEXT, DOB DATE, Phone TEXT, Email TEXT, PRIMARY KEY(UserID));
CREATE TABLE IF NOT EXISTS login_details(UserID TEXT REFERENCES registered_users (UserID), Password TEXT, PRIMARY KEY(UserID));
CREATE TABLE IF NOT EXISTS staff(StaffID TEXT, UserID TEXT REFERENCES registered_users (UserID), PRIMARY KEY(StaffID));
CREATE TABLE IF NOT EXISTS taken_out(TakeoutID TEXT, BookID TEXT REFERENCES books (BookID), UserID TEXT REFERENCES registered_users (UserID), Date_Takeout DATE, Date_Return DATE, Returned BOOLEAN, PRIMARY KEY(TakeoutID));
CREATE TABLE IF NOT EXISTS requested_books(RequestID TEXT, userID TEXT REFERENCES registered_users (UserID), title TEXT, authors TEXT, isbn10 TEXT, isbn13 TEXT, PRIMARY KEY(RequestID));
CREATE TABLE IF NOT EXISTS fix_books(FixID TEXT, BookID TEXT REFERENCES books (BookID), StaffID TEXT REFERENCES staff (StaffID), Issue TEXT, Fixed BOOLEAN, PRIMARY KEY(FixID));

INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS2145697', '1984', 'George Orwell', 'Political Fiction, Social Science Fiction, Utopian Fiction, Dystopian Fiction', '0451524934', '9780451524935', 3) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS3960429', 'The Adventures of Huckleberry Finn', 'Mark Twain', 'Picaresque Fiction', '0486280616', '9780486280615', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS2145277', 'Animal Farm', 'George Orwell', 'Political Fiction, Dystopian Fiction', '143410432X', '978-1434104328', 2) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS3960402', 'The Adventures of Tom Sawyer', 'Mark Twain', 'Folklore, Adventure Fiction, Picaresque Fiction', '1724522191', '9781724522191', 2) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS3924369', 'The Curious Incident of the Dog in the Night-Time', 'Mark Haddon', 'Bildungsroman, Mystery, Crime Fiction', '0099456761', '9780099456766', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS0306577', 'Darkest Minds', 'Alexandra Bracken', 'Adventure, Science Fiction, Thriller', '178654024X', '9781786540249', 4) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS6957181', 'Antony and Cleopatra: Oxford School Shakespeare', 'William Shakespeare', 'Tragedy', '0198393342', '9780198393344', 3) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS7539473', 'Life Of Pi', 'Yann Martel', 'Adventure Fiction, Psychological Fiction, Philosophical Fiction', '1406330264', '9781406330267', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS0924535', 'The Dream House: A Novel', 'Craig Higginson', 'Fiction', '1770104895', '9781770104891', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS2436319', 'To Kill a Mockingbird', 'Harper Lee', 'Southern Gothic, Bildungsroman', '0099549484', '9780099549482', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS0936580', 'The Chronicles of Narnia', 'C. S. Lewis', 'Fantasy Fiction', '0007117302', '9780007117307', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS3603406', 'Little Women', 'Louisa May Alcott', 'Novel, Fiction, Comedy, Bildungsroman', '0451529308', '9780451529305', 3) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS6012370', 'The Bride Collector', 'Ted Dekker', 'Fiction, Mystery, Thriller, Suspense', '0340964987', '9780340964989', 2) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS1224675', 'Danger in the Shadows', 'Dee Henderson', 'Christian Fiction, Romantic Suspense, Religious Fiction', '1414310552', '9781414310558', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS1854403', 'Redeeming Love', 'Francine Rivers', 'Novel, Romance Novel, Historical Fiction, Christian Fiction', '1590525132', '9781590525135', 5) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS5709751', 'The Hunger Games', 'Suzanne Collins', 'Action, Science Fiction, Fantasy, Adventure, Thriller', '0439023483', '9780439023481', 3) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS3978345', 'The Book Thief', 'Markus Zusak', 'Novel, Young Adult Fiction, Historical Fiction', '0375831002', '9780375831003', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS0357515', 'Black Beauty', 'Anna Sewell', 'Novel, Fiction, Children Literature', '0439228905', '9780439228909', 1) ON CONFLICT (BookID) DO NOTHING;
INSERT INTO books(BookID, Title, Authors, Genres, ISBN10, ISBN13, Amount) VALUES ('LMS3609491', 'Look Into My Eyes', 'Lauren Child', 'Fiction, Children Literature', '0007334060', '9780007334063', 2) ON CONFLICT (BookID) DO NOTHING;

INSERT INTO registered_users(UserID, first_name, surname, dob, phone, email) VALUES ('TT160895305', 'Tim', 'Thiymons', '1995-08-06', '0547859654', 'test@gmail.com') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO login_details(UserID, password) VALUES ('TT160895305', 'testing101') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO staff(StaffID, UserID) VALUES ('STT01', 'TT160895305') ON CONFLICT (StaffID) DO NOTHING;
INSERT INTO registered_users(UserID, first_name, surname, dob, phone, email) VALUES ('MR171182406', 'Michael', 'Rensburg', '1982-11-17', '0827561322', 'michael.rensburg@icloud.com') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO login_details(UserID, password) VALUES ('MR171182406', 'wackledoodle') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO registered_users(UserID, first_name, surname, dob, phone, email) VALUES ('SC200494623', 'Sarah-Jane', 'Crosby', '1994-04-20', '0723431123', 's.j.crosby@telkom.com') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO login_details(UserID, password) VALUES ('SC200494623', 'd@rks1d30fm00n') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO registered_users(UserID, first_name, surname, dob, phone, email) VALUES ('MJ180701791', 'Melissa', 'John', '2001-07-18', '0823437654', 'missie.john@gmail.com') ON CONFLICT (UserID) DO NOTHING;
INSERT INTO login_details(UserID, password) VALUES ('MJ180701791', 't3nbottl3sofRum') ON CONFLICT (UserID) DO NOTHING;

INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('BT1807202684', 'LMS3978345', 'MR171182406', '2020-07-04', '2020-07-18', false) ON CONFLICT (takeoutid) DO NOTHING;
INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('BT1807202683', 'LMS2145697', 'MR171182406', '2020-07-04', '2020-07-18', false) ON CONFLICT (takeoutid) DO NOTHING;
INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('BT1807207895', 'LMS2436319', 'MR171182406', '2020-07-04', '2020-07-18', false) ON CONFLICT (takeoutid) DO NOTHING;
INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('BT1807208783', 'LMS0306577', 'MJ180701791', '2020-07-04', '2020-07-18', false) ON CONFLICT (takeoutid) DO NOTHING;
INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('BT1807202958', 'LMS7539473', 'MJ180701791', '2020-07-04', '2020-07-18', true) ON CONFLICT (takeoutid) DO NOTHING;
INSERT INTO taken_out(takeoutid, bookid, userid, date_takeout, date_return, returned) VALUES ('BT1807201241', 'LMS3960429', 'SC200494623', '2020-07-04', '2020-07-18', false) ON CONFLICT (takeoutid) DO NOTHING;

