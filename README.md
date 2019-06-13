# Devoxx France: Bazel sample app

Sample app use for the Devoxx France 2019 conference "À la découverte de Bazel".

This "dummy" app is composed of multiple parts:

- **gateway**: a spring boot java app in the `demo` package
- **fetcher**: a scala app in the `fetcher` package
- **domain**: a java domain model in the `domain` package
- **cache-server**: a cache-server package use to build our remote cache server image

For managing external dependencies, I have both used (to show different tools):
- [bazel-deps](https://github.com/johnynek/bazel-deps)
- [rules_jvm_external](https://github.com/bazelbuild/rules_jvm_external)

## Getting started

1. Install bazel by following the instructions from http://bazel.build
2. Retrieve and clone this repository

## Build the project

**Build and test with the version 0.24.1**

### Build and run binaries

Build and run the **gateway** binary:
```bash
bazel build :gateway
bazel run :gateway # env set to dev by default
```

or in a one shot:
```bash
bazel run :gateway # build and run it
```

Build and run the **fetcher** binary:
```bash
bazel run :fetcher
```

### Test it

```bash
curl "http://localhost:3000/greet?name=Devoxx"
```

should return 

```json
{
  "data": "Hello Devoxx !",
  "from": "http://localhost:3000"
}
```

## Add the remote cache

To perform our remote caching, we'll use an nginx server with the WebDAV module. We will run a container, and mount the container cache directory to an host volume. The container will expose the port number `8888.`

Our nginx server will start with the following configuration:
```
events {
  worker_connections 1024;
}

http {
  sendfile on;
  server {

    location /cache/ {
      root /cache;
      dav_methods PUT;
      create_full_put_path on;
      client_max_body_size 1G;
      allow all;
    }

  }
}
```

### Configure bazel to use the remote cache server

We could pass option directly with bazel CLI, or use a `.bazelrc` configuration file (this is what we are going to do).

Look at the `.bazelrc` file at the root repository, we've had this one:
```
build --remote_http_cache=http://0.0.0.0:8888/cache
```

Here, we're specifying which remote cache we should hit.

### Build the cache-server (no need of docker)

```bash
bazel build :cache-server
```

### Run the cache server (need to have a docker daemon)

```bash
bazel run :cache-server
```

Now, to test it, you can do:

```bash
bazel clean # clean our local cache
```

then build some stuff:

```bash
bazel build :gateway
bazel build :fetcher
```

We should see a lot of `PUT` request on our remote cache backend.

When all is built, clean the local cache by doing a `bazel clean` (simulate the fact that some other people populate our remote cache service).

then redo:
```bash
bazel build :gateway
bazel build :fetcher
```

We should see something like:
```
INFO: Elapsed time: 2.365s, Critical Path: 1.85s
INFO: 44 processes: 44 remote cache hit. # <- all actions are fetched from the remote cache \o/
INFO: Build completed successfully, 48 total actions
```
