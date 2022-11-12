# Menza backend documentation

### Own hosting

Please contact me and don't create your own BE instance,
so the rating is not decentralized. But if you want, you can do so.
Project is Docker ready, just update the `application.conf`.
See docs inside the template file in the `backend/` folder.

### Auth - Api key

To access BE, you have to include the `X-Api-Key` header in your requests.

If you want to use the official BE hosted by me, write me a message
on Telegram ([@to_urcite_ty_kokos](https://t.me/to_urcite_ty_kokos)).
I require to be in contact with you,
so I can inform you about important BE changes.
I will give you a key and BE url with no problems,
don't steal keys from other apps please.

### ID generation

I saw no point in storing dish names on BE, so all the endpoints below accept
hashed dish ids.

You compute the dish id according this formula:

```kotlin
id = provider + "_" + menza_name + "_" + dish_name
id = SHA1(id.bytes("UTF-8"))
id = BASE64.encode(id)
return id.take(8) // the first 8 chars
```

Where all the args are trimmed. The `provider` field is always `CTU` (for now).

Server checks if exactly 8 chars are sent.

### Endpoints

For server url check the Api key section.

#### Rate endpoint

`POST /api/v1/rate`

```json
{
  "id": "12345678",
  // int [1 - 5]
  "rating": 5
}
```

Response is the same as the status endpoint.

#### Sold-out endpoint

`POST /api/v1/sold-out`

```json
{
  "id": "12345678"
}
```

Response is the same as the status endpoint.

#### Status endpoint

`GET /api/v1/status`
`200 OK`

```json
[
  {
    "id": "12345678",
    "ratingSum": 5,
    "rateCount": 1,
    "rating": 5.0,
    "soldOutCount": 0
  }
]
```

#### Statistics endpoint

`GET /api/v1/statistics`
`200 OK`

```json
{
  "ratings": 1,
  "average": 1.0,
  "sold_out": 0,
  "state_requests": 1,
  "statistics_requests": 1
}
```
