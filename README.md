
# Synopsis

Example code for a talk "Deliver Scala using Gradle" presented by Jeff Henrikson at the "Unconference" track of "Scale by the Bay", San Francisco, Nov 2018.

## How to run demos

## Demo: Self-documentation

    ./gradlew tasks
        ------------------------------------------------------------
        All tasks runnable from root project
        ------------------------------------------------------------
        . . .

        Other tasks
        -----------
        customtasks - Lists all tasks pertitent to the unconf repository
        wrapper

    ./gradlew customtasks

        All tasks pertitent to the unconf repository
        -----------------------------------------------------
        clean - Deletes binary code artifacts
        compile - Compiles binary code artifacts
        test - Runs tests

## Demo: Sibling projects
    (cd siblings && ../gradlew run)

## Demo: Sbt is not all or nothing
    (cd callsbt && ../gradlew callsbtUberjar)

## Demo: Virtualization of tasks
    (cd gizmos && ../gradlew test)

## Demo: Debug
    (cd callsbt && ../gradlew callsbtUberjar --stacktrace --info)
