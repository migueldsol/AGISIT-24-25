# Caseirinha Real - Deployment Instructions

This document provides a step-by-step guide to deploy the **Caseirinha Real** web application (a cake store application). The deployment process involves using **Vagrant**, **Google Cloud Platform (GCP)**, **Terraform**, and **Ansible** to set up the infrastructure, configure Kubernetes, and start the application.

---

## 1. Prerequisites

- [Vagrant](https://developer.hashicorp.com/vagrant/downloads)
- [VirtualBox](https://www.virtualbox.org/wiki/Downloads) (or another Vagrant-supported virtualization provider)
- [Google Cloud CLI](https://cloud.google.com/sdk/docs/install) installed on your **local machine** (for `gcloud` commands)
- A valid GCP project (with project ID)
- Terraform installed locally (or on the management VM)
- SSH keys (will be generated during the deployment)

---

## 2. Setup and Vagrant

1. **Navigate to the Setup directory** in your local environment:

   ```bash
   cd Setup
   ```

2. **Start the Vagrant virtual machine:**

   ```bash
   vagrant up
   ```

3. **SSH into the Vagrant machine:**
   ```bash
   vagrant ssh
   ```

You are now inside the management VM where you will execute further commands

---

## 3. GCP Configuration

1. **Navigate to the GCP Cloud directory** inside the VM:

```bash
 cd labs/Setup/gcpcloud
```

2. **Authenticate with your Google Cloud account:**

```bash
 gcloud auth login
```

Follow the prompt in your browser to complete the authentication

---

## 4. Update Terraform Variables

1. **Open the file** `terraform.gcp-variables.ttf`

2. **Set your GCP project ID:**

   ```bash
   'variable "GCP_PROJECT_ID" {
   default = "agisit-2425-website-XXXXX-7657685"
   }'
   ```

3. You should check your `GCP_ZONE` and if it doesn't work you should change them and also the `GCP_REGION`

---

## 5. Initialize Terraform and Generate SSH Key

1. **Initialize Terraform:**

```bash
terraform init
```

2. **Generate an SSH key** (press ENTER through the prompts):

```bash
ssh-keygen -t rsa -b 2048
```

After completion, you will see the `The key's randomart image is:` (example):

```bash
+--[ED25519 256]--+
|..o              |
|.+               |
| .E              |
|.. .     +o o    |
| .+.    S+=+     |
| .... o.*+o*     |
|     oo*.o+oo.   |
|     o o=+.oo+   |
|     .o.oo==B++  |
+----[SHA256]-----+
```

## 6. Terraform Plan and Apply

1. **Preview the changes** Terraform will make:

```bash
tarraform plan
```

2. **Apply the Terraform configuration:**

```bash
terraform apply
```

- Terraform will ask for confirmation. Type `yes` to proceed

- This process will create the necessary GCP infrastucture (one master node and multiple worker nodes).

Upon successful completion, Terraform will output something similiar to:

```bash
master = "34.118.4.170"
master_ssh = "https://www.googleapis.com/compute/v1/projects/your-gcp-project-id/zones/europe-central2-a/instances/master"
worker_IPs = tolist([
  "worker1 = 34.118.25.221",
  "worker2 = 34.118.13.250",
  "worker3 = 34.116.245.78",
  "worker4 = 34.116.143.160",
  "worker5 = 34.118.110.146",
])
```

---

## 7. Update Hosts File

1. **Gather the IPs** from the Terraform output.

2. **Edit the hosts file** inisde the management VM (the one you accessed with `vagrant ssh`):

```bash
sudo nano /etc/hosts
```

3. Add the appropriate entries for the master and worker nodes, for example:

```bash
34.118.4.170   master
34.118.25.221  worker1
34.118.13.250  worker2
34.116.245.78  worker3
34.116.143.160 worker4
34.118.110.146 worker5
```

4. **Save and exit** the file.

## 8. Ansible Playbooks

In the **management VM**, run the following Ansible playbooks in **order**:

1. **Configure Nodes:**

```bash
ansible-playbook ansible-gcp-configure-nodes.yml
```

2. **Install Kubernetes on All Nodes:**

```bash
ansible-playbook ansible-k8s-install.yml

```

3. **Create the K8s Cluster:**

```bash
ansible-playbook ansible-create-cluster.yml

```

4. **Join Worker Nodes to the Cluster:**

```bash
ansible-playbook ansible-workers-join.yml

```

5. **Start Deployment:**

```bash
ansible-playbook ansible-start-deployment.yml

```

Once these playbooks finish, your Kubernetes cluster will be up and ready for the application.

---

This is a web aplication of a cake store caled Caseirinha Real. The users can create accounts, login, buy items and track their orders.

You need to alter the network rules inside

Steps do deploy the aplication

Go the folder Setup and execute "vagrant up"

Next execute "vagrant ssh" to connect to the virtual machine

Next go the labs/Setup/gcpcloud directory

Proceed to login to the google cloud by using the command gcloud auth login.

Substitute in the file terraform.gcp-variables.tf with your project id like the following code

'variable "GCP_PROJECT_ID" {
default = "agisit-2425-website-XXXXX-7657685"
}'

You should check your GCP_Zone and if it doesn't work you should change them and also the GCP_REGION

Next use the terraform init command to initialize Terraform and proceed to generate an ssh key with the next command 'ssh-keygen -t rsa -b 2048' and press enter until the ssh key appear in the command line like this:

The key's randomart image is:
+---[RSA 2048]----+
| +.E |
| = . |
| . = . |
| + . o|
| S + +.|
| .+ o..+B|
| . =++++X|
| o .+++oo\*=|
| . o..+=\*\*B=|
+----[SHA256]-----+

apply the 'terraform plan' command and the 'terraform apply' (it will ask you for a 'yes' input)

Now the terraform is all setup and so are your workers and master.

You will have an output of this kind:

'master = "34.118.4.170"
master_ssh = "https://www.googleapis.com/compute/v1/projects/agisit-2425-website-103191/zones/europe-central2-a/instances/master"
worker_IPs = tolist([
"worker1 = 34.118.25.221",
"worker2 = 34.118.13.250",
"worker3 = 34.116.245.78",
"worker4 = 34.116.143.160",
"worker5 = 34.118.110.146",
])'

Grap the ip's and complete the gcphosts file and edit the file inside the mgmt system (the vm you ran vagrant ssh) with path /etc/hosts

We should continue to apply the ansible-playbooks to create the pods and the rest of the needed resources.

Apply the commands in the following order.

ansible-playbook ansible-gcp-configure-nodes.yml
ansible-playbook ansible-k8s-install.yml
ansible-playbook ansible-create-cluster.yml
ansible-playbook ansible-workers-join.yml
ansible-playbook ansible-start-deployment.yml

After this you're almost set up. We just gotta define the external ip of our webserver.

Go the gcp cloud and login through the ssh on the master node by copying the command that should look something like this, inside the Compute Engine tab and sub tab of VM instances.

'gcloud compute ssh --zone "europe-central2-a" "master" --project "agisit-2425-website-103191"'

For this you need to find the worker associated with the ingress-nginx-controller service

For this you will find where the associated pod of the ingress-nginx-controller is by running the command

'sudo kubectl get pods -n ingress-nginx' that will give you the pods related to the ingress-nginx namespace

Copy the name of the pod with the ingress-nginx-controler...

and apply the command

'sudo kubeclt describe pod [name of the pod] -n ingress-nginx'

this will give you the name of the worker where the service is, if you scroll up and look for the node value. Go the google cloud, still in the same tab, and search for the name of that worker. Copy the external ip and lets set the external ip of the service.

apply this command and substitute the EXTERNAL_IP_HERE 'sudo kubectl patch svc ingress-nginx-controller -p '{"spec":{"externalIPs":["EXTERNAL_IP_HERE"]}}' -n ingress-nginx'

apply the command 'sudo kubectl get services -n ingress-nginx' and look for the ingress-nginx-controller external and port that should look like this 80:PORT_NUMBER.

Also substitute the previous IP in the development.ylm file where it says EXTERNAL_IP_HERE and the port number where it says PORT_NUMBER.

Your app should be ready to go. Connect to the http://EXTERNAL_IP_HERE:PORT_NUMBER
