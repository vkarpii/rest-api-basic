INSERT INTO tag(tag_name)
VALUES ('Free'),
       ('Music'),
       ('Podcasts');

INSERT INTO gift_certificate(certificate_name, certificate_description, price, create_date, last_update_date, duration)
VALUES ('Spotify Basic',
        'Spotify is a digital music, podcast, and video service that gives you access to millions of songs and other content from creators all over the world',
        0.00, '2022-12-10 11:05:02', '2022-12-10 11:05:02', 30),
       ('Spotify Premium',
        ' Ad-free and on-demand playback. Offline listening for up to 10,000 songs each on up to 5 different devices. Unlimited skips.',
        9.99, '2022-12-10 11:05:02', '2022-12-10 11:05:02', 30),
       ('Google Podcasts', 'With Google Podcasts, you can find and listen to the world''s podcasts for free.',
        0.00, '2022-12-10 11:05:02', '2022-12-10 11:05:02', 30);

INSERT INTO certificate_tag(certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 3);