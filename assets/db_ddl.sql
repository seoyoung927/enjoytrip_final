CREATE TABLE member (
    uid int PRIMARY KEY auto_increment,
    userId VARCHAR(255) unique NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    isHost BOOLEAN DEFAULT FALSE
);

CREATE TABLE Board (
    boardId INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    content TEXT,
    userId VARCHAR(255),
    answer TEXT,
    createdAt datetime not null DEFAULT CURRENT_TIMESTAMP,
    foreign key(userId) references member (userId) ON DELETE SET NULL
);

CREATE TABLE HotPlace (
    hid INT PRIMARY KEY AUTO_INCREMENT,
    userId varchar(255),
    title varchar(255),
    intro TEXT,
    description Text,
    imagePath blob,
    createdTime DATETIME not null DEFAULT CURRENT_TIMESTAMP,
    foreign key(userId) references member (userId) ON DELETE SET NULL
);

create table hotplace_comment (
    cid int  PRIMARY KEY AUTO_INCREMENT,
    userId varchar(255),
    content text,
    hid int,
    createdTime datetime not null DEFAULT CURRENT_TIMESTAMP,
    foreign key(userId) references member (userId) ON DELETE SET NULL,
    foreign key(hid) references hotplace (hid) ON DELETE CASCADE
);

create table plan(
    pid INT PRIMARY KEY AUTO_INCREMENT,
    content_id int,
    userId varchar(255),
    foreign key(content_id) references attraction_info (content_id) ON DELETE CASCADE,
    foreign key(userId) references member (userId) ON DELETE CASCADE
);

create table product(
    pid int PRIMARY KEY AUTO_INCREMENT,
    ownerId varchar(255),
    title varchar(255),
    intro text,
    content text,
    warning text,
    price int,
    people int,
    foreign key(ownerId) references member (userId) ON DELETE CASCADE
);

create Table book (
    bid int PRIMARY KEY AUTO_INCREMENT,
    pid int,
    userId varchar(255),
    memo text,
    foreign key(pid) references product (pid) ON DELETE CASCADE,
    foreign key(userId) references member (userId) ON DELETE CASCADE
);

create table product_image(
    imageId int PRIMARY KEY AUTO_INCREMENT,
    imagePath blob,
    pid int,
    foreign key(pid) references product (pid) ON DELETE CASCADE
);

CREATE TABLE Auth(
    userId VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    token VARCHAR(1000) default null,
    foreign key(userId) references member (userId) ON DELETE CASCADE
);

create table plan_comment(
    cid  INT PRIMARY KEY AUTO_INCREMENT,
    pid int,
    content_id int,
    userId varchar(255),
    content text,
    isShow boolean, 
createdTime DATETIME not null DEFAULT CURRENT_TIMESTAMP,
    foreign key(pid) references plan (pid) ON DELETE SET NULL,
    foreign key(userId) references member (userId) ON DELETE SET NULL
);
