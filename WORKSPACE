workspace(name = "devoxx_bazel_sample")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

BAZEL_SKYLIB_TAG = "0.6.0"

http_archive(
    name = "bazel_skylib",
    strip_prefix = "bazel-skylib-%s" % BAZEL_SKYLIB_TAG,
    url = "https://github.com/bazelbuild/bazel-skylib/archive/%s.tar.gz" %
          BAZEL_SKYLIB_TAG,
)

PROTOBUF_JAVA_VERSION = "3.6.1"

# Current workaround for https://github.com/bazelbuild/rules_scala/issues/726
http_archive(
    name = "com_google_protobuf",
    url = "http://central.maven.org/maven2/com/google/protobuf/protobuf-java/%s/protobuf-java-%s.jar" % (PROTOBUF_JAVA_VERSION, PROTOBUF_JAVA_VERSION),
    sha256 = "fb66d913ff0578553b2e28a3338cbbbe2657e6cfe0e98d939f23aea219daf508",
    build_file_content = """
package(default_visibility = ["//visibility:public"])

filegroup(
    name = "meta",
    srcs = glob([
        "META-INF/**/*",
    ])
)

filegroup(
    name = "classes",
    srcs = glob([
        "com/**/*",
        "google/**/*",
    ])
)

genrule(
    name = "protobuf_java",
    srcs = [":meta", ":classes"],
    outs = ["myjar.jar"],
    toolchains = ["@bazel_tools//tools/jdk:current_java_runtime"],
    cmd = "mkdir -p META-INF && mkdir -p com/google/protobuf && mv $(locations :meta) META-INF && mv $(locations :classes) com/google/protobuf && $(JAVABASE)/bin/jar cfM $@ META-INF com",
)
    """,
)

http_archive(
    name = "bazel_common",
    strip_prefix = "bazel-common-f1115e0f777f08c3cdb115526c4e663005bec69b",
    url = "https://github.com/google/bazel-common/archive/f1115e0f777f08c3cdb115526c4e663005bec69b.zip",
)



###########################################################
##################### SCALA RULE ##########################
###########################################################

rules_scala_version = "f3113fb6e9e35cb8f441d2305542026d98afc0a2"

http_archive(
             name = "io_bazel_rules_scala",
             url = "https://github.com/bazelbuild/rules_scala/archive/%s.zip" % rules_scala_version,
             type = "zip",
             strip_prefix= "rules_scala-%s" % rules_scala_version,
             )

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")

scala_repositories()

register_toolchains("//:my_toolchain")

###########################################################
##################### END SCALA RULE ######################
###########################################################

RULES_JVM_EXTERNAL_TAG = "1.2"
RULES_JVM_EXTERNAL_SHA = "e5c68b87f750309a79f59c2b69ead5c3221ffa54ff9496306937bfa1c9c8c86b"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

load("//3rdparty:workspace.bzl", "maven_dependencies")

maven_dependencies()

maven_install(
    name = "fetcher_app",
    artifacts = [
        "com.typesafe.akka:akka-http_2.11:10.1.5",
        "com.typesafe.akka:akka-http-spray-json_2.11:10.1.5",
        "com.typesafe.akka:akka-stream_2.11:2.5.12",
    ],
    repositories = [
        "https://repo.maven.apache.org/maven2"
    ]
)


###########################################################
################### DOCKER RULE ###########################
###########################################################

DOCKER_COMMIT = "49cc180b7566947ed414c232762b5b549fbbaf72"

# Download the rules_docker repository at release v0.7.0
http_archive(
    name = "io_bazel_rules_docker",
    strip_prefix = "rules_docker-%s" % (DOCKER_COMMIT),
    sha256 = "4438139f4a4ad3ffaf094e6aa03a6e80519b24da5ce79d5316b036041bf731c1",
    urls = ["https://github.com/bazelbuild/rules_docker/archive/%s.zip" % (DOCKER_COMMIT)],
)

# This is NOT needed when going through the language lang_image
# "repositories" function(s).
load(
    "@io_bazel_rules_docker//repositories:repositories.bzl",
    container_repositories = "repositories",
)
container_repositories()

load(
    "@io_bazel_rules_docker//container:container.bzl",
    "container_pull",
)

container_pull(
  name = "java_base",
  registry = "gcr.io",
  repository = "distroless/java",
  tag = "debug",
  # 'tag' is also supported, but digest is encouraged for reproducibility.
)

container_pull(
    name = "nginx_base",
    registry = "index.docker.io",
    repository = "library/nginx",
    digest = "sha256:f5aab36815a33016d4375ce6468a2f1453dbaf47a53f8c7df69a9f6865045ef4",
)

load(
    "@io_bazel_rules_docker//java:image.bzl",
    _java_image_repos = "repositories",
)

_java_image_repos()

###########################################################
################### END DOCKER RULE #######################
###########################################################