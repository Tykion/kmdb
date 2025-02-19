# KMDB 🎥

*RESTful application to manage movies, actors and genres.*

## Installation:
1. Clone the repository:

```bash
git clone https://gitea.kood.tech/Tykion/kmdb.git
```

2. Navigate to project directory

```bash
cd kmdb
cd movies
```

3. Install dependencies:

```bash
mvn install
```

## Usage:

Use: 

```bash
mvn spring-boot:run
```

in the terminal and to stop it you just press CTRL + C in the terminal.

Recommend using **Postman** for CRUD operations.

<p>Default pathway: localhost:8080/api/{movies or genres or actors} <br>

*Make sure to not put slash on the last mapping/resource for example "localhost:8080/api/movies/" won't work, but "localhost:8080/api/movies will work"*
<br>
<p>ID's ARE SET AUTOMATICALLY AND ARE NOT CHANGEABLE<br>
<p>DATABASE IS IN THE "resources/database" FOLDER! (Sqlite)<br>

## Post/Create operations:

- Select **POST** method and input:

#### Adding actors example: (api/actors)

```
{
    "name": "example",
    "birthDate": "1975-12-31" (VALID BIRTHDATE: YYYY-MM-DD)
}
```

#### Adding movies example: (api/movies)

```
{
    "title": "example",
    "releaseYear": 2010,
    "duration": 148,
    "actorIds": [1,2],  // IDs (Delete if you don't need to add actors)
    "genreIds": [1,2]      // IDs (Delete row if you don't need to add genres)
}
```

#### Adding genres example: (api/genres)

```
{
    "name": "example"
}
```

*ID's should be immutable as fas as I tried, it gave me an error.*

## CRUD Operations: 

- **PAGINATION IS PRESENT!**

Starting page is 0 and default size is 20.

Example:
/api/movies?page=0&size=10


- **GET** specific movie, genre or actor /api/{movies/actors/genres}/{id}
- **GET** Movies by genre: /api/movies?genre={genreId}
- **DELETE** genre and associated relationships: /api/genres/{genreId}?force=true
- **GET** actors from a specific movie: /api/movies/{movieId}/actors
- **GET** Actor by name: /api/actors/search?name=...     (Can be name or/and surname, case insensitive)
- **GET** Movies an actor has appeared in: /api/movies?actor={Actor.id}
- **GET** Movies by year: /api/movies?year={releaseYear}
- **GET** Movie by title /api/movies/search?title={title}
- **PATCH** a movie's actors list: api/movies/{movie_id}/actors , example:

```
{
    "actorIds": [1, 2, 3, 4]  // Example actor IDs to be associated with the movie
}
```
