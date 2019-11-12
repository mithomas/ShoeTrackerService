
create table sighting (
    id          bigint          comment 'The unique sighting identifier.',
    latitude    double          comment 'The north/south position in decimal degrees.',
    longitude   double          comment 'The east/west in decimal degrees.',
    seen_at     timestamp       comment 'Date and time of the sighting.',
    seen_by     varchar(255)    comment 'The person spotting the object.',
)                               comment 'An object sighting.';

create table shoe (
    id          bigint          comment 'The unique shoe identifier.',
)                               comment 'A spotted shoe, decoupled from the sighting event for possible extensions both for shoe information.';

create table sighting_object_map (
    sighting_id bigint          comment 'The unique sighting identifier.',
    object_id   bigint          comment 'The unique object identifier',
    object_type varchar(16)     comment 'The object type; valid values: [shoe]',
)                               comment 'Mapping table between sightings and objects.';