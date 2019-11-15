create table sighting (
    id          bigint          not null    auto_increment  comment 'The unique sighting identifier; for now only for shoes.',
    latitude    double          not null                    comment 'The north/south position in decimal degrees.',
    longitude   double          not null                    comment 'The east/west in decimal degrees.',
    seen_at     datetime        not null                    comment 'Date and time of the sighting.',
    seen_by     varchar(255)    not null                    comment 'The person spotting the shoe.',

    primary key (id)
)                                                           comment 'An object sighting.';
