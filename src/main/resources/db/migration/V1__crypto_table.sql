
CREATE TABLE currency (
   id UUID PRIMARY KEY,
   ticker varchar UNIQUE not null,
   name varchar not null,
   number_of_coins bigint not null,
   market_cap bigint not null
);

INSERT INTO currency (id, ticker, name, number_of_coins, market_cap) VALUES
  ('1544011a-05e9-4f78-86e8-a7afa8f820d9', 'BTC', 'Bitcoin', 16770000, 189580000000),
  ('61877fe3-d3c6-4308-9dbf-6d45f5728ede', 'ETH', 'Ethereum', 96710000, 69280000000),
  ('b9a095b1-96df-4d3a-9057-fe95a4ed0786', 'XRP', 'Ripple', 38590000000, 64750000000),
  ('b9cbb664-7a3c-4df3-a552-8edc8ac24fcb', 'BCH', 'BitcoinCash', 16670000, 69020000000);