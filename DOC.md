# Menza backend documentation

### Own hosting

Please contact me and don't create your own BE instance
if you plan on using the BE for CTU canteens.
Project is Docker ready, just update the `application.conf`.
See docs inside the template file in the `backend/` folder.

### Auth - Api key

To access BE, you have to include the `X-Api-Key` header in your requests.
If you want to use my (official) BE hosted, create a GitHub issue.
I want to be in contact with you,
so I can inform you about important BE breaking changes in advance.
I will give you a key and BE url with no hesitations,
don't steal keys from my other apps please.

### ID generation

The later endpoints use multiple types of ids.
Here we list how the ids are generated.
The following lines are only standards the Android Menza app is using
and I recommend you follow them. For other canteens feel free
to make your own sensible system and document it here.

#### Menza ID

Menza ID is always prefixed by its provider.
Either `CTU_` or `BUFFET_` values are the valid options for now.

The rest of the id goes as follows.
For CTU, numeric ID from Agata is used.
For Strahov canteen the ID is `CTU_1`.
For Buffets either `FEL` or `FS` is appended.

#### Dish ID

ID is always a string.
For CTU, dish ID from Agata is used.
For buffet, SHA1 hash of the dish name is used.

### Endpoints

For server url check the Api key section.

#### Rate endpoint

`POST /api/v1/rate/{menzaID}`

```json
{
  "dishID": "12345",
  "nameCs": "jmeno_jidla",
  "nameEn": "dish_name",
  // int [1 - 5]
  "taste": 5,
  "portion": 1,
  "worthiness": 3
}
```

Response is the same as the status endpoint.


#### Status endpoint

The endpoint returns current rating status for the menza.
All the fields are self-explanatory except for the combined field.
Audience shows the number of people that participated in the given category.
Count is the number of ratings considered in the category (used for average).
The combined field is as the name suggest combination or sum of the other fields.

`GET /api/v1/status/{menzaID}`
`200 OK`

```json
[
    {
        "id": "12345",
        "nameCs": "jmeno_jidla",
        "nameEn": "dish_name",
        "combined": {
            "sum": 80,
            "count": 20,
            "audience": 7,
            "average": 4.0
        },
        "taste": {
            "sum": 35,
            "count": 7,
            "audience": 7,
            "average": 5.0
        },
        "portion": {
            "sum": 24,
            "count": 6,
            "audience": 6,
            "average": 4.0
        },
        "worthiness": {
            "sum": 21,
            "count": 7,
            "audience": 7,
            "average": 3.0
        }
    }
]
```

#### Statistics endpoint

The endpoints provide information about the BE usage.

`GET /api/v1/statistics`
`200 OK`

```json
{
    "rating_requests": 1,
    "state_requests": 2,
    "statistics_requests": 1
}
```
