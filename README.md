# order-service

A standalone Java 17 + Maven demo service with guarded order creation. It listens on port `8082` and uses only JDK HTTP server APIs at runtime.

## Build and run

```powershell
mvn test
mvn -q -DskipTests package
java -cp target/classes com.example.order.OrderServiceApp
```

## Endpoints

- `POST /orders` creates a sample order for demo customer `42` and returns HTTP 201.
- `GET /orders/{id}` returns a sample order.
- `GET /orders/user/{customerId}` returns a JSON order list.

Example: `curl -X POST http://localhost:8082/orders`

`application-example.yml` records the local demo port and the optional user/payment service URLs. `repairbot.yaml` is a safe, non-secret AI repair configuration for use by the separate Python orchestrator; fake Jira/GitHub modes, dry-run behavior, and plan approval are enabled by default.
