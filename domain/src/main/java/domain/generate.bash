#!/usr/bin/env bash

TMPL_FILE="Model.tmpl"
BUILD_TMPL="BUILD.tmpl"

group=50
current_folder=""
count=0

for i in {0..600}
do
    if (($i % $group == 0))
    then
        count=$(($count + 1))
        new_dir="model-${count}"
        current_folder=${new_dir}
        mkdir -p ${new_dir}
        touch ${current_folder}/BUILD.bazel
        sed -e "s/\${name}/${current_folder}/" ${BUILD_TMPL} > ${current_folder}/BUILD.bazel
    fi

    new_file="ModelGen${i}.java"
    touch ${current_folder}/${new_file}
    sed -e "s/\${className}/ModelGen${i}/" ${TMPL_FILE} > ${current_folder}/${new_file}
done