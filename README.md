# Broker

- [Architecture](#architecture)
- [How to build?](#how-to-build)
- [How to run?](#how-to-run)
- [Endpoints](#endpoints)
- [Development requirements](#development-requirements)
- [Simulations](#simulations)

## Architecture

### System components
The system is partitioned in 3 main components: 
 - [Wallet](#wallet)
 - [OrderBook (only for vibranium)](#orderbook)
 - [Trade](#trade)

#### Wallet

The responsibility of this component is manager wallets. In this, is possible to do deposits and withdraws. Currently, the wallet is enabled to store BRL(Brazilian Real) and VIB(Vibranium), but the structure is ready to store any kind of asset.
Besides that, there is a feature to make trades. This feature will be used in the next component, the [OrderBook](#orderbook).

##### About the database.

For this component, the database choice was a relational database. The data has some relationships and the ACID properties available in this kind of database are requirements to assure a non-corruption in possible simultaneous access.

#### OrderBook

*Disclaimer: For this POC only vibranium order book was created.*

The order book component is responsibly is find the best buy, for who is buying, and the best sale for who is selling.
There are several algorithms to be used in a order book. For this project the algorithm implemented was the **Price Time Priority**
When the algorithm does a match, the wallet will be called to know if trades between parts are available. In case of availability, the trade will be stored in our next component, the [Trade](#trade).

##### About the database.

For this component, the database choice was a relational database. The ACID properties available in this kind of database are requirements to assure a non-corruption in possible simultaneous access.


#### Trade

The trade component is responsible by store finished trades. 

##### About the database.

For this component, the choice was a NoSQL (by document) database. Here, the data doesn't have relationships and after writing never more will be updated or deleted. With this kind of database is possible to scale easily.

### Communications among components

The [OrderBook](#orderbook) receives requests in a REST API. To not block this API, a request, when arriving in the system, is sent to a queue(rabbitmq) that will be consumed by the Matcher Algorithm.

When matcher happens, the trade information is sent to [Trade component](#trade) , through a queue

## How to build?

```shell
./mvnw clean package
```

```shell
docker build -t helioalb/broker:latest .
```

## How to run?

```shell
docker-compose up -d
```

ou, se quiser acompanhar os logs:

```shell
docker-compose up
```

## Endpoints

|System         |action          |endpoint          |
|---------------|----------------|------------------|
|Wallet         |create wallet   |POST /wallets     |
|Wallet         |deposit         |POST /wallets/{wallet_code}/deposit|
|Wallet         |withdraw        |POST /wallets/{wallet_code}/withdraw|
|Wallet         |balance         |GET /wallets/{wallet_code}|
|OrderBook      |insert a bid    |POST /orderbook/vibranium/bids|
|OrderBook      |insert an ask   |POST /orderbook/vibranium/asks|
|OrderBook      |list bids       |GET /orderbook/vibranium/bids|
|OrderBook      |list asks       |GET /orderbook/vibranium/asks|
|Trade          |list trades     |GET /trades|

The endpoints also can be accessed in: http://localhost:8080/swagger-ui/index.html

## Development requirements

- JDK 17
- Postgres > 13
- MongoDb > 5
- RabbitMq > 3.9

## Simulations

#### Wallets with balance


##### Create first wallet:

###### Request
```shell
curl --request POST \
  --url http://localhost:8080/wallets
```

###### Response

```json
{"id":"fd308578-b94f-4231-8ae0-91cf8fc60227"}
```



##### Deposit BRL in fd308578-b94f-4231-8ae0-91cf8fc60227

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/wallets/fd308578-b94f-4231-8ae0-91cf8fc60227/deposit \
  --header 'Content-Type: application/json' \
  --data '{
  "asset": "BRL",
  "amount": 1000
}'
```

###### Response

```json
202
```

##### Deposit VIB in fd308578-b94f-4231-8ae0-91cf8fc60227

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/wallets/fd308578-b94f-4231-8ae0-91cf8fc60227/deposit \
  --header 'Content-Type: application/json' \
  --data '{
  "asset": "VIB",
  "amount": 1000
}'
```

###### Response

```json
202
```

##### Create second wallet:

###### Request
```shell
curl --request POST \
  --url http://localhost:8080/wallets
```

###### Response

```json
{"id":"5953bfc2-5c11-4847-b8fc-1f78513c701d"}
```



##### Deposit BRL in 5953bfc2-5c11-4847-b8fc-1f78513c701d

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/wallets/5953bfc2-5c11-4847-b8fc-1f78513c701d/deposit \
  --header 'Content-Type: application/json' \
  --data '{
  "asset": "BRL",
  "amount": 1000
}'
```

###### Response

```json
202
```

##### Deposit VIB in 5953bfc2-5c11-4847-b8fc-1f78513c701d

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/wallets/5953bfc2-5c11-4847-b8fc-1f78513c701d/deposit \
  --header 'Content-Type: application/json' \
  --data '{
  "asset": "VIB",
  "amount": 1000
}'
```

###### Response

```json
202
```

##### Wallet fd308578-b94f-4231-8ae0-91cf8fc60227 wants buy 10 vibraniums by 0.9

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/orderbook/vibranium/bids \
  --header 'Content-Type: application/json' \
  --data '{
  "walletCode": "fd308578-b94f-4231-8ae0-91cf8fc60227",
  "quantity": 10,
  "price": 0.9
}'
```

###### Response

```json
202
```

##### List bids

###### Request

```shell
curl --request GET \
  --url http://localhost:8080/orderbook/vibranium/bids
```

###### Response

```json
    {
      "id": 1,
      "walletCode": "fd308578-b94f-4231-8ae0-91cf8fc60227",
      "quantity": 10.0000,
      "price": 0.9000
    }
```

##### Wallet 5953bfc2-5c11-4847-b8fc-1f78513c701d to sell 5 vibraniums by 0.8

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/orderbook/vibranium/asks \
  --header 'Content-Type: application/json' \
  --data '{
  "walletCode": "5953bfc2-5c11-4847-b8fc-1f78513c701d",
  "quantity": 5,
  "price": 0.8
}'
```

###### Response

```json
202
```

##### List bids

###### Request

```shell
curl --request GET \
  --url http://localhost:8080/orderbook/vibranium/bids
```

###### Response

```json
    {
      "id": 1,
      "walletCode": "fd308578-b94f-4231-8ae0-91cf8fc60227",
      "quantity": 5.0000,
      "price": 0.9000
    }
```

##### Trade

###### Request

```shell
curl --request GET \
  --url http://localhost:8080/trades
```

###### Response

```json
[

  {
    "id": "62655e9e055d5265c5f4290b",
    "assetTraded": "VIB",
    "assetUsedToPay": "BRL",
    "amount": 4.5000,
    "quantity": 5,
    "sellerWalletCode": "5953bfc2-5c11-4847-b8fc-1f78513c701d",
    "buyerWalletCode": "fd308578-b94f-4231-8ae0-91cf8fc60227",
    "createdAt": "2022-04-24T14:28:46.103"
  }
]
```

##### Wallet 5953bfc2-5c11-4847-b8fc-1f78513c701d to sell 10 vibraniums by 0.8

###### Request

```shell
curl --request POST \
  --url http://localhost:8080/orderbook/vibranium/asks \
  --header 'Content-Type: application/json' \
  --data '{
  "walletCode": "5953bfc2-5c11-4847-b8fc-1f78513c701d",
  "quantity": 10,
  "price": 0.8
}'
```

###### Response

```json
202
```

##### List bids

###### Request

```shell
curl --request GET \
  --url http://localhost:8080/orderbook/vibranium/bids
```

###### Response

```json
    empty
```

##### Trade

###### Request

```shell
curl --request GET \
  --url http://localhost:8080/trades
```

###### Response

```json
[
  {
    "id": "62655e9e055d5265c5f4290b",
    "assetTraded": "VIB",
    "assetUsedToPay": "BRL",
    "amount": 4.5000,
    "quantity": 5,
    "sellerWalletCode": "5953bfc2-5c11-4847-b8fc-1f78513c701d",
    "buyerWalletCode": "fd308578-b94f-4231-8ae0-91cf8fc60227",
    "createdAt": "2022-04-24T14:28:46.103"
  },
  {
    "id": "62655fb8055d5265c5f4290c",
    "assetTraded": "VIB",
    "assetUsedToPay": "BRL",
    "amount": 4.50000000,
    "quantity": 5.0000,
    "sellerWalletCode": "5953bfc2-5c11-4847-b8fc-1f78513c701d",
    "buyerWalletCode": "fd308578-b94f-4231-8ae0-91cf8fc60227",
    "createdAt": "2022-04-24T14:33:28.878"
  }
]
```

##### List asks

###### Request

```shell
curl --request GET \
  --url http://localhost:8080/orderbook/vibranium/asks
```

###### Response

```json
    {
      "id": 1,
      "walletCode": "5953bfc2-5c11-4847-b8fc-1f78513c701d",
      "quantity": 5.0000,
      "price": 0.8000
    }
```
