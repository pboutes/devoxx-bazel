load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")
java_library(
    name = "tomcat_embed_core",
    exports = [
        "//3rdparty/jvm/org/apache/tomcat:tomcat_annotations_api",
        "//external:jar/org/apache/tomcat/embed/tomcat_embed_core"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



java_library(
    name = "tomcat_embed_el",
    exports = [
        "//external:jar/org/apache/tomcat/embed/tomcat_embed_el"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)



java_library(
    name = "tomcat_embed_websocket",
    exports = [
        "//external:jar/org/apache/tomcat/embed/tomcat_embed_websocket",
        ":tomcat_embed_core"
    ],
    visibility = [
        "//3rdparty/jvm:__subpackages__"
    ]
)


