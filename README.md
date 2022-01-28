# ServiceMenu



## Installation of the Project

Use the package manager [maven](https://maven.apache.org//) to install the dependencies,

```bash
mvn install
```

## Creation of the Jar

Use the package manager [maven](https://maven.apache.org//) to create the executable jar.

```bash
mvn package
```

The executable jar will be found in the target subdirectory and it will be called servicemenu-1.0-SNAPSHOT-jar-with-dependencies.jar

## Execution of the Jar

In the directory where the jar is executed, there must be:

- a directory named 'input'
- ServiceMenu.json in the 'input' directory

To run the jar :

```python
java -jar servicemenu-1.0-SNAPSHOT-jar-with-dependencies.jar
```

