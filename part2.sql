----------------------------------------Find unbooked properties----------------------------------------------
SELECT p.id, p.title
FROM properties p
LEFT JOIN bookings b ON p.id = b.property_id
WHERE b.id IS NULL
ORDER BY p.id;

---------------------------------------Detect overlapping bookings---------------------------------

SELECT 
    a.id AS first_booking_id,
    a.start_date AS first_start,
    a.end_date AS first_end,
    b.id AS second_booking_id,
    b.start_date AS second_start,
    b.end_date AS second_end
FROM 
    bookings a
JOIN 
    bookings b ON a.property_id = b.property_id
WHERE 
    a.id < b.id 
    AND (
        (a.start_date, a.end_date) OVERLAPS (b.start_date, b.end_date)
        OR
        a.start_date = b.end_date
        OR
        a.end_date = b.start_date
    )
ORDER BY 
   a.start_date;
   
--------------------------------------------Top 5 users by distinct properties booked--------------------------------------


WITH user_property_counts AS (
    SELECT 
        u.id AS user_id,
        u.name AS user_name,
        COUNT(DISTINCT b.property_id) AS distinct_properties_booked
    FROM 
        users u
    JOIN 
        bookings b ON u.id = b.user_id
    GROUP BY 
        u.id, u.name
    ORDER BY 
        distinct_properties_booked DESC
    LIMIT 5
)
SELECT 
    upc.*,
    STRING_AGG(DISTINCT p.title, ', ' ORDER BY p.title) AS properties_booked
FROM 
    user_property_counts upc
JOIN 
    bookings b ON upc.user_id = b.user_id
JOIN 
    properties p ON b.property_id = p.id
GROUP BY 
    upc.user_id, upc.user_name, upc.distinct_properties_booked
ORDER BY 
    upc.distinct_properties_booked DESC;
    
	
----------------------------------------Recommend schema/indexing improvements for large datasets---------------------------------------------

-->Schema Improvements
1)Normalization Enhancements:-
ALTER TABLE properties ADD COLUMN type_id SMALLINT REFERENCES property_types(id);

2)Partitioning for Time-Series Data :-
CREATE TABLE bookings_2023_06 PARTITION OF bookings
  FOR VALUES FROM ('2023-06-01') TO ('2023-07-01');
  
3)Data Type Optimizations:-
ALTER TABLE users ALTER COLUMN id TYPE BIGINT;
ALTER TABLE properties ALTER COLUMN id TYPE BIGINT;

-->Strategic Indexing
1)Core Performance Indexes:-
CREATE INDEX idx_bookings_property_dates ON bookings 
  (property_id, start_date, end_date);
  
-->Additional Optimizations:-
1)Materialized Views for Analytics

2)Query Optimization Features
-- Enable advanced features
ALTER DATABASE your_database SET enable_partition_pruning = on;
SET effective_cache_size = '4GB'; -- Adjust based on available RAM


