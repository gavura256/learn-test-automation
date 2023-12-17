# test-automation

## Getting started

To start api tests run command:

```gradle clean test -Dgroup=smoke -DprojectName=your_project_name -DuserToken=your_user_token```

To build docker image run:

```docker build -t kgavura/chromium-gradle:jdk17 --no-cache .```

To push it to Docker Hub

```docker push kgavura/chromium-gradle:jdk17-alpine```
