#!/bin/bash

./gradlew clean
./gradlew assembleRelease
ch_names=`ls app/build/outputs/apk/`
for ch_name in $ch_names; do
	echo $ch_name
	version_name=`aapt dump badging app/build/outputs/apk/$ch_name/release/app-$ch_name-release.apk | grep versionCode | awk '{split($4, ver, "\047"); print ver[2]}'`
	mkdir -p ~/Downloads/app/zhuhaibus/$version_name/$ch_name
	cp app/build/outputs/apk/$ch_name/release/app-$ch_name-release.apk ~/Downloads/app/zhuhaibus/$version_name/$ch_name/
	cp app/build/outputs/mapping/$ch_name/release/mapping.txt ~/Downloads/app/zhuhaibus/$version_name/$ch_name/
done

echo '陛下，我们跑完了'
