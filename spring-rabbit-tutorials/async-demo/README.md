1. create a message from the `foo` service:

```bash
$ http post :8080/foo/1
```

2. get the message from the `bar` service

```bash
$ http :8081/bar/1
```