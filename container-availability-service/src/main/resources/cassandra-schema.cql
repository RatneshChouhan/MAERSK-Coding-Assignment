CREATE KEYSPACE IF NOT EXISTS container_booking
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 1
    } AND durable_writes = true;

CREATE TABLE IF NOT EXISTS container_booking.bookings (
    booking_ref text PRIMARY KEY,
    container_size int,
    container_type text,
    origin text,
    destination text,
    quantity int,
    String timestamp
);
