# IBM C.I.C. Backend Coding Challenge

## Documentation

### Movies shot in San Francisco

This is a REST-API that return movies shot in San Francisco.

```html
http://<host>:<port>/movies
```

supported methods:

- GET

supported query parameters:

- title: String
  - filters by title, can be part of the title too.
- beforeYear: integer
  - filters movies to movies before the specified year.

Returns a json array.

example query:

```curl
GET http://localhost:8080/movies?title=180
```
