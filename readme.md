### Pre-requisites
To build the project you will need JDK 17 in your PATH.

### Build
```shell
./gradlew assemble
```

### Run
```shell
./gradlew run --console=plain
```

### Operations

* Per the test requirements, console input is solicited from user for searching the Star Wars service.

* All user input must be followed by the `<enter>` key in order to perform the search.  Results will be subsequently displayed in the console.

* To exit the program simply type `quit` and press `<enter>` key. Or you can exit with `<ctrl>+c`.

### Running in Docker

This application also ships as a docker container. To build and run entirely in docker:

* Build: `docker-compose build`
* Run: `docker-compose up`
