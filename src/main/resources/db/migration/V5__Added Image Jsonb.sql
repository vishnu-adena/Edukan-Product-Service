ALTER TABLE product
    DROP COLUMN image_url;

ALTER TABLE product
    ALTER COLUMN description TYPE VARCHAR(100000) USING (description::VARCHAR(100000));
