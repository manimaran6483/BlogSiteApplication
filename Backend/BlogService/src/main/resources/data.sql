 create table blog (
       id bigint auto_increment primary key,
        article TEXT not null,
        author_name varchar(50) not null,
        blog_name varchar(200) not null,
        category varchar(100) not null,
        created_at datetime,
        updated_at datetime
)

