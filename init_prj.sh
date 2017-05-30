if [ -z "$1" ]; then
    echo target dir is required!
    exit;
fi

cp bakup run_android  run_ios usepkg $1/../..

cp -rv test7/* $1/
