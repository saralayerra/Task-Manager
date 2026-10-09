# Java Task Manager

A small Spring Boot REST API intended for learning a Java deployment pipeline on AWS EC2.

## Requirements

- Java 17
- Maven 3.9+
- Docker
- Jenkins with Pipeline, Git, and SonarQube Scanner for Jenkins plugins
- SonarQube server configured in Jenkins with the name `SonarQube`
- Jenkins Secret Text credential with ID `sonar-token`
- Trivy installed on the Jenkins agent / EC2 host
- Jenkins agent allowed to use Docker

## API endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/tasks` | List tasks |
| POST | `/api/tasks` | Add a task |

Example POST request body:

```json
{
  "title": "Learn Docker",
  "completed": false
}
```

After deployment, open `http://EC2_PUBLIC_IP:8080/api/tasks` to list tasks.
For POST requests, use Postman or curl.

## Important notes

- Tasks are stored in memory. They reset when the application restarts; no database is required.
- The Jenkinsfile assumes Jenkins, Docker, Trivy, and the app container run on the same EC2 host/agent.
- Configure the SonarQube server and token in Jenkins before running the pipeline.
- The Trivy stages intentionally fail the pipeline when HIGH or CRITICAL findings are detected. Review findings before deciding whether to change the policy.
- In the EC2 security group, allow inbound TCP port 8080 only from trusted IPs while testing. Do not expose the Jenkins or SonarQube administration ports publicly.
- This is a learning project, not a production-ready service.

## Build output

The Maven build creates `target/task-manager.jar`. The Dockerfile copies that JAR into a Java 17 runtime image.
