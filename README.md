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

