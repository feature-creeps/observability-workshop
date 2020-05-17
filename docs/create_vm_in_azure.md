# Creating a virtual machine using AZURE for the purposes of installing the observability workshop stack. Alot of these instructions were taken from 
Parveeen Khan's blog post https://www.parveenkhans.com/2020/02/testing-tour-stop-10-pairing-up-on.html

## BackGround 
The observability workshop stack consists of ~ 8 microservices, each with database. There's also tools such as grafana, kibana, prometheus that assist in providing visibility to logging, tracing and metrics. 

The VM has a minimum requirement of 16GB of Memory 

OS:  Ubuntu. 
Instance Type: Standard D4s v3 (4 vcpus, 16 GiB memory)

We're assuming you are new to AZURE and don't have resource groups setup 

## Prerequisites

1) Create a free account on Azure
Sign up and get an account on Azure where the virtual machine will be created - https://azure.microsoft.com/. (I'd recommend you do this a few hours before you plan to start creating the VM. It felt that AZURE took a while to setup my account properly)  

2) Know your AZURE subscription id 
- Login to the AZURE Portal 
- Home > Subscriptions its the long key that looks like 1b2aefd2-a41e-7g31-9a05-645c3ed5e38f

2) Bash shell on your local computer 
3) Install AZURE CLI using docker 
``` bash 
docker run -it mcr.microsoft.com/azure-cli
``` 
4) Create Resource Group using CLI 
``` bash 
az group create --name olly --location <yourregion>
``` 
5) Create VM 
``` bash
$ docker-machine create --driver azure --azure-subscription-id 1b2aefd2-a41e-434a-9a05-645c3ed5e38f \
--azure-location <region> \ 
--azure-image <image name region dependent > 
--azure-open-port 5601 --azure-open-port 3000 \
--azure-open-port 9090 --azure-open-port 9411 \
--azure-open-port 80 --azure-open-port 8080 \
--azure-open-port 8081 --azure-open-port 8082 \
--azure-open-port 8083 --azure-open-port 8084 \
--azure-open-port 8085 --azure-open-port 8086 \
--azure-size D4s_v3 --azure-size-gb 200 --azure-data-disk-sizes-gb 200
--azure-admin-username olly
 o11y-workshop
 ```
 
 --amazonec2-region eu-west-2 \
--amazonec2-ami ami-0916731d2f176f937 \
--amazonec2-open-port 5601 --amazonec2-open-port 3000 \
--amazonec2-open-port 9090 --amazonec2-open-port 9411 \
--amazonec2-open-port 80 --amazonec2-open-port 8080 \
--amazonec2-open-port 8081 --amazonec2-open-port 8082 \
--amazonec2-open-port 8083 --amazonec2-open-port 8084 \
--amazonec2-open-port 8085 --amazonec2-open-port 8086 \
--amazonec2-instance-type m5.2xlarge --amazonec2-root-size 200 \
o11y-workshop
Running pre-create checks...
Microsoft Azure: To sign in, use a web browser to open the page https://aka.ms/devicelogin.
Enter the code [...] to authenticate.
Give permission for Docker Machine for Azure 
  
  
az vm create \
    --resource-group myResourceGroup \
    --name o11y-workshop \
    --image win2016datacenter \
    --admin-username azureuser 
    
5) SSH KEy 
1) Create an SSH Key for security purposes [full instructions here](https://docs.microsoft.com/en-gb/azure/virtual-machines/linux/quick-create-portal)
-Type 
``` bash
`ssh-keygen -t rsa -b 2048`
``` 
to create the ssh key.

- Press Enter to save in the default location, listed in brackets.
- Type a passphrase for your SSH key or press Enter to continue without a passphrase.
- Type:
``` bash 
cat ~/.ssh/id_rsa.pub
``` 
- you should see something like:
```` bash 
ssh-rsa ZZZZB3NzaC1yc2EAAAADAQABAAABAQCmgvFLEhF1hjtnbyeVi8DdM0idYLgncDDmxdFu6GrkWMimvpG1afJwAsUVbN9BwYlzgy9XJeBk+YAi1Nyu/nzxfZuYIfsWPawZB04G/2nZyZgR/0hVQjgPSdfuJYyIbxNubUqnja5sDx+pKZU5wXoJl4mxUMtJRfYcgfB+kd6icpnzcptcBvGvQ8ynVS/mSeD8TbLiN4eldRH65Q22+TgedxyEx+kS4EOXNTylt+P1sCAK2Ppa3nUxJoLHXhY/gua95n+NBdTEqcWrugFR2XTe7NnWNAZ2hdxa47Xdv2xwZsnL8FPxasvc1ljSovqIN9PsqQwxwJ6lPEq6SGULH+oj
````
- Copy and store this key (in a text file) for later 

## Create the VM on Azure  
- If you haven't already, login to the azure portal 
- Project Details 
-- Virtual Machine > Create a Virtual Machine > Add 
-- Resource Group > Create New > Observability
- Instance Details 
-- Virtual Machine Name: Observability
-- Region: <Your Region> 
-- Image: Ubuntu Server 16.04 TLS <or similar> 
-- Azure Spot Instance: No
-- Size: Standard D4s v3 <this is not default you will need to change> 
-Administrator Accoun t 
-- username: olly 
-- SSH public key (the one you generated and looks like a heap of scrambled text) 
-Inbound port rules
--HTTP(80, HTTPS(443), SSH (22) 

- Review and once validation has passed hit CREATE 
- Go back to Virtual Machine link and you should see your VM (Observability) there


## Test Connectivity 
- Go Virtual Machine > Observability > Connect > SSH > Test Connection 
You want a feedback message  "Network connectivity allowed" 

## Connect to the VM 
Next we want to connect and login to our VM. We need to login to install the workshop stack. 
- Go Virtual Machine > Observability > Connect > SSH 
- Ignore steps 1) and 2) 
- Step 3. Type in where your public key lives on your local machine. Mine was ~/.ssh 
- Step 4. Copy the command and paste into a bash shell on your local computer 
- Watch as you magically login to your account 
- Type the following to verify you are in your VM instance:
``` bash 
whoami 
``` 
- You are now ready to install the workshop stack 


