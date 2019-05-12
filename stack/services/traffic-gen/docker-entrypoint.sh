#!/usr/bin/env bash
set -e

cd /tmp

wget -q https://github.com/tsenart/vegeta/releases/download/cli%2Fv12.4.0/vegeta-12.4.0-linux-amd64.tar.gz

tar xzvf vegeta-12.4.0-linux-amd64.tar.gz
chmod a+x vegeta
mv vegeta /usr/local/bin

echo "Fetching upload traffic generator"

wget -q https://github.com/feature-creeps/upload-traffic-gen/releases/download/v0.4.0/upload-traffic-gen_0.4.0_Linux_x86_64.tar.gz
tar xzvf upload-traffic-gen_0.4.0_Linux_x86_64.tar.gz
chmod a+x upload-traffic-gen
mv upload-traffic-gen /usr/local/bin

echo "Waiting for imgeholder service to come up"

until $(curl --output /dev/null --silent --head --fail http://imageholder:8080/actuator/health); do
      printf '.'
      sleep 5
done

echo "imageholder running - uploading images"

/usr/local/bin/upload-traffic-gen -f false -u "http://imageholder:8080/api/images" -d /images | \
  /usr/local/bin/vegeta attack -rate=1/s -lazy -format=json -duration=1m | \
  vegeta report -type json


echo "Uploaded images"


echo "Fetching transformation traffic generator"

wget -q https://github.com/feature-creeps/transform-traffic-gen/releases/download/v0.2.0/transform-traffic-gen_0.2.0_Linux_x86_64.tar.gz
tar xzvf transform-traffic-gen_0.2.0_Linux_x86_64.tar.gz
chmod a+x transform-traffic-gen
mv transform-traffic-gen /usr/local/bin

echo "Waiting for imageorchestrator service to come up"

until $(curl --output /dev/null --silent --head --fail http://imageholder:8080/actuator/health); do
      printf '.'
      sleep 5
done

echo "imageorchestrator running - sending random transformation requests"

/usr/local/bin/transform-traffic-gen  -n 300 -f  "http://imageholder:8080/api/images/nameContaining/uploaded" -t  "http://imageorchestrator:8080/api/images/transform" | \
  /usr/local/bin/vegeta attack -rate=12/m -lazy -format=json -duration=10m > /dev/null
