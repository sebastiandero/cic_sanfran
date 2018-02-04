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

# !!
Automated Testing was not done as this program is quite small(270 lines of code, none of which can be sensibly unit tested).
If this is a requirement(says optional on the webpage) of course I can add it, just let me know.