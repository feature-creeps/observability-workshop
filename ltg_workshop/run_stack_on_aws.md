1. Your AWS region will need an existing VPC in it. This can be checked via commandline: `aws ec2 describe-vpcs`. To create one via commandline if needed use: `aws ec2 create-vpc --cidr-block 10.0.0.0/16`

2. Create a running instance:
```
docker-machine create --driver amazonec2 --amazonec2-ami ami-026f49896b1af2759 --amazonec2-instance-type m5.2xlarge --amazonec2-open-port 8080 --amazonec2-region us-east-2 --amazonec2-root-size 200 o11y-workshop
```
> Note: If you use `aws-vault` to protect AWS creds run:
>
>`aws-vault exec ninedemons-admin_role -- docker-machine create --driver amazonec2 --amazonec2-ami ami-026f49896b1af2759 --amazonec2-instance-type m5.2xlarge --amazonec2-open-port 8080 --amazonec2-region us-east-2 --amazonec2-root-size 200 o11y-workshop`

2. Set your shell environment to use the new instance:
```
eval $(docker-machine env o11y-workshop)
```

3. Now you can do all the docker stuff you would running locally. So `docker-compose up` in `observability-workshop/stack/stack-local-default`

4. To get the public IP of your instance:
```
docker-machine ip o11y-workshop
```

5. To run the traffic generator to upload traffic :
```
go run main.go -d ~/work/flickrscrape/result -u http://3.17.156.205:8080/api/images | \
  vegeta attack -rate=10/m -lazy -format=json -duration=30s | \
  tee results.bin | \
  vegeta report
```

6. When done do `docker-machine rm o11y-workshop`